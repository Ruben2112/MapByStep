package com.heveamobile.mapbystep.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.HealthPermissionManager
import com.heveamobile.mapbystep.domain.usecase.GetActiveMapUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.heveamobile.mapbystep.domain.usecase.UpsertInitialMapDataUseCase
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class HomeViewModel(
    private val healthPermissionManager: HealthPermissionManager,
    private val getUserUseCase: GetUserUseCase,
    private val syncStepsUseCase: SyncStepsUseCase,
    private val upsertInitialMapDataUseCase: UpsertInitialMapDataUseCase,
    private val getActiveMapUseCase: GetActiveMapUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

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

        viewModelScope.launch {
            getActiveMapUseCase().collectLatest { map ->
                _state.update {
                    it.copy(
                        requiredSteps = map?.calculatedDistance
                            ?: 0L,
                    )
                }
            }
        }

        viewModelScope.launch {
            val syncStepsJob = launch {
                if (healthPermissionManager.checkPermissionState() == HealthPermissionStatus.Granted) {
                    syncStepsUseCase()
                }
            }

            val upsertInitialMapDataJob = launch(Dispatchers.IO) {
                upsertInitialMapDataUseCase()
            }

            joinAll(
                syncStepsJob,
                upsertInitialMapDataJob,
            )

            getUserUseCase().first()
            getActiveMapUseCase().first()

            _state.update { it.copy(isLoadingSteps = false) }
        }

        _state.update { it.copy(isLoadingSteps = false) }
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
        }
    }
}