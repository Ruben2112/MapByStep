package com.heveamobile.mapbystep.navigation

import androidx.navigation3.runtime.NavKey
import com.heveamobile.mapbystep.ui.home.NavigationDrawerRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Profile : Route {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Profile
        }
    }

    @Serializable
    data object Maps : Route {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Maps
        }
    }

    @Serializable
    data object Destinations : Route {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Destinations
        }
    }

    @Serializable
    data class DestinationInfo(
        val destinationId: String?,
    ) : Route {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.DestinationInfo
        }
    }

    @Serializable
    data object Directions : Route {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Directions
        }
    }

    @Serializable
    data object Settings : Route {
        override fun toNavigationDrawerRoute(): NavigationDrawerRoute {
            return NavigationDrawerRoute.Settings
        }
    }

    fun toNavigationDrawerRoute(): NavigationDrawerRoute
}