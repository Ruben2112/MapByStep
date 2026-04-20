package com.heveamobile.mapbystep.ui.profile

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

class ProfileViewModel(
    val healthPermissionManager: HealthPermissionManager,
    val syncStepsUseCase: SyncStepsUseCase,
    val getUserUseCase: GetUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val permissionState = healthPermissionManager.checkPermissionState()
            _state.update { it.copy(healthPermissionState = permissionState) }

            getUserUseCase().collectLatest { user ->
                _state.update {
                    it.copy(
                        totalSteps = user?.totalSteps
                            ?: 0,
                        twentyFourHourRecord = user?.twentyFourHourRecord
                            ?: 0,
                        sevenDayRecord = user?.sevenDayRecord
                            ?: 0,
                        thirtyDayRecord = user?.thirtyDayRecord
                            ?: 0,
                    )
                }
            }
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.UpdatePermissionState -> {
                viewModelScope.launch {
                    val permissionState = healthPermissionManager.checkPermissionState()
                    _state.update { it.copy(healthPermissionState = permissionState) }

                    // Also sync steps if user has only just granted permissions
                    if (permissionState == HealthPermissionStatus.Granted) {
                        syncStepsUseCase()
                    }
                }
            }
        }
    }

    private fun checkPermissions() {
    }
}