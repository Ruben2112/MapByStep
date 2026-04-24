package com.heveamobile.mapbystep.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.HealthPermissionManager
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.SpendStepsUseCase
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
    private val getMapsWithProgressUseCase: GetMapsWithProgressUseCase,
    private val spendStepsUseCase: SpendStepsUseCase,
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

        viewModelScope.launch(Dispatchers.IO) {
            getMapsWithProgressUseCase().collectLatest { maps ->
                _state.update { state ->
                    state.copy(
                        requiredSteps = maps.firstOrNull { it.isActive }?.calculatedDistance
                            ?: 0L,
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

            getUserUseCase().first()
            getMapsWithProgressUseCase().first()

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

            HomeAction.SpendSteps -> {
                viewModelScope.launch(Dispatchers.IO) {
                    spendStepsUseCase()
                }
            }
        }
    }
}