package com.heveamobile.mapbystep.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.HealthPermissionManager
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val healthPermissionManager: HealthPermissionManager,
    private val getUserUseCase: GetUserUseCase,
    private val syncStepsUseCase: SyncStepsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            if (healthPermissionManager.checkPermissionState() == HealthPermissionStatus.Granted) {
                syncStepsUseCase()
            }
        }

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