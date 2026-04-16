package com.gumamobile.mapbystep.ui.profile

import com.gumamobile.mapbystep.ui.common.HealthPermissionState

data class ProfileState(
    val totalSteps: Int? = null,
    val twentyFourHourRecord: Int? = null,
    val oneWeekRecord: Int? = null,
    val oneMonthRecord: Int? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val healthPermissionState: HealthPermissionState = HealthPermissionState.Loading,
)

sealed interface ProfileAction {
    data object CheckPermissions : ProfileAction
}