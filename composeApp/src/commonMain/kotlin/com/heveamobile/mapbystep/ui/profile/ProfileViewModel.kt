package com.heveamobile.mapbystep.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.HealthPermissionManager
import com.heveamobile.mapbystep.domain.usecase.GetDailyStepsChartDataUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.SyncStepsUseCase
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

class ProfileViewModel(
    val healthPermissionManager: HealthPermissionManager,
    val syncStepsUseCase: SyncStepsUseCase,
    val getUserUseCase: GetUserUseCase,
    val getDailyStepsChartDataUseCase: GetDailyStepsChartDataUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val permissionState = healthPermissionManager.checkPermissionState()
            _state.update { it.copy(healthPermissionState = permissionState) }

            println("HENK: ProfileViewModel init")
            getUserUseCase().collectLatest { user ->
                getDailyStepsChartDataUseCase().collectLatest { dailyStepData ->
                    _state.update {
                        it.copy(
                            totalSteps = user?.totalSteps
                                ?: 0,
                            startTime = user?.startTime
                                ?: Clock.System.now(),
                            previousTwentyFourHours = user?.previousTwentyFourHours
                                ?: 0,
                            twentyFourHourRecord = user?.twentyFourHourRecord
                                ?: 0,
                            previousSevenDays = user?.previousSevenDays
                                ?: 0,
                            sevenDayRecord = user?.sevenDayRecord
                                ?: 0,
                            previousThirtyDays = user?.previousThirtyDays
                                ?: 0,
                            thirtyDayRecord = user?.thirtyDayRecord
                                ?: 0,
                            dailyStepData = dailyStepData,
                        )
                    }
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
}