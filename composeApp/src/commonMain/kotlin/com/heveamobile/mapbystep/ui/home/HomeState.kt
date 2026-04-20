package com.heveamobile.mapbystep.ui.home

data class HomeState(
    val isDrawerOpen: Boolean = false,
    val availableSteps: Long = 0L,
    val requiredSteps: Long = 1000L,
)

sealed interface HomeAction {
    data object OpenNavigationDrawer : HomeAction
    data object CloseNavigationDrawer : HomeAction
    data object SyncSteps : HomeAction
}
