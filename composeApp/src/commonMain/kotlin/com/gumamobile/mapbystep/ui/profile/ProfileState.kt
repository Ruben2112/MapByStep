package com.gumamobile.mapbystep.ui.profile

data class ProfileState(
    val totalSteps: Int? = null,
    val twentyFourHourRecord: Int? = null,
    val oneWeekRecord: Int? = null,
    val oneMonthRecord: Int? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

sealed interface ProfileAction {
    data object LoadSteps : ProfileAction
}