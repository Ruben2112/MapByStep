package com.heveamobile.mapbystep.ui.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.repository.UserPreferencesRepository
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import com.heveamobile.mapbystep.navigation.NavigationHandler
import com.heveamobile.mapbystep.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DestinationsViewModel(
    private val navigationHandler: NavigationHandler,
    private val getMapsWithProgressUseCase: GetMapsWithProgressUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DestinationsState())
    val state: StateFlow<DestinationsState> = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            combine(
                userPreferencesRepository.gridSortingOrder,
                userPreferencesRepository.hideUndiscovered,
            ) { order, hide -> order to hide }
                .flatMapLatest { (order, hide) ->
                    getMapsWithProgressUseCase(
                        order,
                        hide,
                    )
                }
                .collectLatest { maps ->
                    _state.update { state ->
                        val currentSelectedId = state.selectedMap?.id
                        val selectedMap = maps.find { it.id == currentSelectedId }
                            ?: maps.firstOrNull()

                        state.copy(
                            selectedMap = selectedMap,
                            maps = maps,
                            destinations = selectedMap?.destinations
                                ?: emptyList(),
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun onAction(action: DestinationsAction) {
        when (action) {
            is DestinationsAction.ToggleMapSelector -> TODO()
            is DestinationsAction.SelectMap -> {
                _state.update { it.copy(selectedMap = action.map) }
            }

            is DestinationsAction.ToggleProgressDisplay -> {
                _state.update { it.copy(isProgressExpanded = !it.isProgressExpanded) }
            }

            is DestinationsAction.OpenDestinationInfo -> {
                navigationHandler.navigateTo(Route.DestinationInfo(action.destinationId))
            }
        }
    }
}