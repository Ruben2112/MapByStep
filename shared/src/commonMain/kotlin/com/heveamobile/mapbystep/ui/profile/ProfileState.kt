package com.heveamobile.mapbystep.ui.profile

import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus
import kotlin.time.Clock
import kotlin.time.Instant

data class ProfileState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val totalSteps: Long = 0L,
    val startTime: Instant = Clock.System.now(),
    val previousTwentyFourHours: Long = 0L,
    val twentyFourHourRecord: Long = 0L,
    val previousSevenDays: Long = 0L,
    val sevenDayRecord: Long = 0L,
    val previousThirtyDays: Long = 0L,
    val thirtyDayRecord: Long = 0L,
    val dailyStepData: Map<Instant, Long> = emptyMap(),

    val healthPermissionState: HealthPermissionStatus = HealthPermissionStatus.Loading,
)

sealed interface ProfileAction {
    data object UpdatePermissionState : ProfileAction
}