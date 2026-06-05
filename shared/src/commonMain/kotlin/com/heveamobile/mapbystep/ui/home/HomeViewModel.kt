package com.heveamobile.mapbystep.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.HealthPermissionManager
import com.heveamobile.mapbystep.domain.repository.UserPreferencesRepository
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.SpendStepsUseCase
import com.heveamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.heveamobile.mapbystep.domain.usecase.UpsertInitialMapDataUseCase
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val healthPermissionManager: HealthPermissionManager,
    private val getUserUseCase: GetUserUseCase,
    private val syncStepsUseCase: SyncStepsUseCase,
    private val upsertInitialMapDataUseCase: UpsertInitialMapDataUseCase,
    private val getMapsWithProgressUseCase: GetMapsWithProgressUseCase,
    private val spendStepsUseCase: SpendStepsUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var revealAllJob: Job? = null

    init {
        _state.update { it.copy(isLoadingSteps = true) }

        viewModelScope.launch {
            getUserUseCase().collectLatest { user ->
                _state.update {
                    it.copy(
                        availableSteps = user?.availableSteps
                            ?: 0L,
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            combine(
                getMapsWithProgressUseCase(),
                userPreferencesRepository.distanceMultiplier,
            ) { maps, multiplier -> maps to multiplier }.collectLatest { (maps, multiplier) ->
                val calculatedDistance = maps.firstOrNull()?.calculatedDistance
                    ?: 0L
                val requiredSteps = (calculatedDistance * multiplier).toLong()

                _state.update { state ->
                    state.copy(
                        requiredSteps = requiredSteps,
                    )
                }
            }
        }

        viewModelScope.launch {
            combine(
                userPreferencesRepository.hideUndiscovered,
                userPreferencesRepository.gridSortingOrder,
            ) { hide, order -> hide to order }.collectLatest { (hide, order) ->
                _state.update { state ->
                    state.copy(
                        sharedDestinationsState = state.sharedDestinationsState.copy(
                            hideUndiscovered = hide,
                            sortingOrder = order,
                        ),
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val syncStepsJob = launch {
                if (healthPermissionManager.checkPermissionState() == HealthPermissionStatus.Granted) {
                    syncStepsUseCase()
                }
            }

            val upsertInitialMapDataJob = launch {
                upsertInitialMapDataUseCase()
            }

            joinAll(
                syncStepsJob,
                upsertInitialMapDataJob,
            )

            val hideUndiscovered = userPreferencesRepository.hideUndiscovered.first()
            val sortingOrder = userPreferencesRepository.gridSortingOrder.first()

            getUserUseCase().first()
            getMapsWithProgressUseCase().first()

            _state.update {
                it.copy(
                    isLoadingSteps = false,
                    sharedDestinationsState = it.sharedDestinationsState.copy(
                        hideUndiscovered = hideUndiscovered,
                        sortingOrder = sortingOrder,
                    ),
                )
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OpenNavigationDrawer -> {
                _state.update { it.copy(isDrawerOpen = true) }
            }

            HomeAction.CloseNavigationDrawer -> {
                _state.update { it.copy(isDrawerOpen = false) }
            }

            HomeAction.SyncSteps -> {
                viewModelScope.launch {
                    syncStepsUseCase()
                }
            }

            HomeAction.SpendSteps -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = spendStepsUseCase().sortedBy { it.rarity }
                    val mapPointsGained = result.sumOf {
                        it.mapPointsGained
                            ?: 0
                    }
                    _state.update { state ->
                        state.copy(
                            visitedDestinationsState = state.visitedDestinationsState.copy(
                                destinations = result,
                                mapPointsGained = mapPointsGained,
                            ),
                        )
                    }
                }
            }

            is HomeAction.RevealDestination -> {
                viewModelScope.launch {
                    _state.update { state ->
                        state.copy(
                            visitedDestinationsState = state.visitedDestinationsState.copy(
                                destinations = state.visitedDestinationsState.destinations.map { item ->
                                    if (item === action.destination) {
                                        item.copy(isRevealed = true)
                                    } else {
                                        item
                                    }
                                },
                            ),
                        )
                    }

                    val allDestinationsRevealed =
                        _state.value.visitedDestinationsState.destinations.all { it.isRevealed }
                    if (allDestinationsRevealed) {
                        delay(1000)
                        _state.update { state ->
                            state.copy(
                                visitedDestinationsState = state.visitedDestinationsState.copy(
                                    showResultSummary = true,
                                ),
                            )
                        }
                    }
                }
            }

            is HomeAction.RevealAllDestinations -> {
                if (!state.value.visitedDestinationsState.isRevealingAll) {
                    revealAllJob = viewModelScope.launch {
                        _state.update { state ->
                            state.copy(
                                visitedDestinationsState = state.visitedDestinationsState.copy(isRevealingAll = true),
                            )
                        }

                        // 1. Get the full list of destinations to reveal
                        val allDestinations = state.value.visitedDestinationsState.destinations

                        // 2. Filter to only those not already revealed
                        val toReveal = allDestinations.filter { !it.isRevealed }

                        toReveal.forEachIndexed { index, destination ->
                            _state.update { state ->
                                state.copy(
                                    visitedDestinationsState = state.visitedDestinationsState.copy(
                                        destinations = state.visitedDestinationsState.destinations.map { item ->
                                            if (item === destination) {
                                                item.copy(isRevealed = true)
                                            } else {
                                                item
                                            }
                                        },
                                    ),
                                )
                            }

                            // Add a delay to revealing next destination.
                            if (index < toReveal.size - 1) {
                                // Logic: Use the rarity of the NEXT card to determine the suspense delay
                                delay((toReveal[index + 1].rarity.intValue * 300).toLong())
                            }
                        }

                        _state.update { state ->
                            state.copy(
                                visitedDestinationsState = state.visitedDestinationsState.copy(isRevealingAll = false),
                            )
                        }
                        delay(1000)
                        _state.update { state ->
                            state.copy(
                                visitedDestinationsState = state.visitedDestinationsState.copy(
                                    showResultSummary = true,
                                ),
                            )
                        }
                    }
                }
            }

            HomeAction.CloseVisitedDestinations -> {
                _state.update { state ->
                    state.copy(visitedDestinationsState = VisitedDestinationsState())
                }
            }

            is HomeAction.ToggleDropdownMenu -> {
                _state.update { state ->
                    state.copy(
                        sharedDestinationsState = state.sharedDestinationsState.copy(
                            showDropdownMenu = !state.sharedDestinationsState.showDropdownMenu,
                        ),
                    )
                }
            }

            is HomeAction.ToggleHideUndiscovered -> {
                viewModelScope.launch {
                    userPreferencesRepository.updateHideUndiscovered(
                        !userPreferencesRepository.hideUndiscovered.first(),
                    )
                }
            }

            is HomeAction.UpdateSortOrder -> {
                viewModelScope.launch {
                    userPreferencesRepository.updateGridSortingOrder(action.sortOrder)
                }
            }

            is HomeAction.ToggleDestinationInfo -> {
                val destinationShown = state.value.visitedDestinationsState.destinationShown
                viewModelScope.launch {
                    _state.update { state ->
                        state.copy(
                            visitedDestinationsState = state.visitedDestinationsState.copy(
                                destinationShown = if (destinationShown == null) action.destination else null,
                            ),
                        )
                    }
                }
            }

            is HomeAction.SkipRevealingAllDestinations -> {
                revealAllJob?.cancel()
                revealAllJob = null

                _state.update { state ->
                    state.copy(
                        visitedDestinationsState = state.visitedDestinationsState.copy(
                            destinations = state.visitedDestinationsState.destinations.map {
                                it.copy(isRevealed = true)
                            },
                            isRevealingAll = false,
                            showResultSummary = true,
                        ),
                    )
                }
            }
        }
    }
}