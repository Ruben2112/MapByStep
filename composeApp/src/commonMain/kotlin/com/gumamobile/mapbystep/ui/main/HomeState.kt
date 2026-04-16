package com.gumamobile.mapbystep.ui.main

data class HomeState(
    val isDrawerOpen: Boolean = false,
)

sealed interface HomeAction {
    data object OpenNavigationDrawer : HomeAction
    data object CloseNavigationDrawer : HomeAction
}
