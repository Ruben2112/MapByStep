package com.gumamobile.mapbystep.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumamobile.mapbystep.domain.HealthPermissionManager
import com.gumamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.gumamobile.mapbystep.ui.common.HealthPermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val healthPermissionManager: HealthPermissionManager,
    private val syncStepsUseCase: SyncStepsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            if (healthPermissionManager.checkPermissionState() == HealthPermissionState.Granted) {
                syncStepsUseCase()
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OpenNavigationDrawer -> {
                _state.value = _state.value.copy(isDrawerOpen = true)
            }

            HomeAction.CloseNavigationDrawer -> {
                _state.value = _state.value.copy(isDrawerOpen = false)
            }
        }
    }
}