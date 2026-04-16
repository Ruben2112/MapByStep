package com.gumamobile.mapbystep.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumamobile.mapbystep.domain.HealthPermissionManager
import com.gumamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.gumamobile.mapbystep.ui.common.HealthPermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    val healthPermissionManager: HealthPermissionManager,
    val syncStepsUseCase: SyncStepsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        checkPermissions()
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.CheckPermissions -> checkPermissions()
        }
    }

    private fun checkPermissions() {
        viewModelScope.launch {
            val newState = healthPermissionManager.checkPermissionState()
            _state.update { it.copy(healthPermissionState = newState) }
            if (_state.value.healthPermissionState == HealthPermissionState.Granted) {
                syncStepsUseCase()
            }
        }
    }
}