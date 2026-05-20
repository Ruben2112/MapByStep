package com.heveamobile.mapbystep.navigation

import androidx.navigation3.runtime.NavKey
import com.heveamobile.mapbystep.ui.home.NavigationDrawerRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Profile : Route, NavKey {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Profile
        }
    }

    @Serializable
    data object Maps : Route, NavKey {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Maps
        }
    }

    @Serializable
    data object Destinations : Route, NavKey {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Destinations
        }
    }

    @Serializable
    data class DestinationInfo(
        val destinationId: String?,
    ) : Route, NavKey {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.DestinationInfo
        }
    }

    @Serializable
    data object Directions : Route, NavKey {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Directions
        }
    }

    @Serializable
    data object Settings : Route, NavKey {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Settings
        }
    }

    fun toNavigationDrawerRoute(): NavigationDrawerRoute
}