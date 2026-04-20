package com.heveamobile.mapbystep.ui.profile

import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus

data class ProfileState(
    val totalSteps: Long = 0L,
    val previousTwentyFourHours: Long = 0L,
    val twentyFourHourRecord: Long = 0L,
    val previousSevenDays: Long = 0L,
    val sevenDayRecord: Long = 0L,
    val previousThirtyDays: Long = 0L,
    val thirtyDayRecord: Long = 0L,


    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val healthPermissionState: HealthPermissionStatus = HealthPermissionStatus.Loading,
)

sealed interface ProfileAction {
    data object UpdatePermissionState : ProfileAction
}