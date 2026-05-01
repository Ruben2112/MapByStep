package com.heveamobile.mapbystep.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Profile : Route, NavKey

    @Serializable
    data object Maps : Route, NavKey

    @Serializable
    data object Destinations : Route, NavKey

    @Serializable
    data class DestinationDetails(
        val destinationId: String?,
    ) : Route, NavKey

    @Serializable
    data object Directions : Route, NavKey

    @Serializable
    data object Settings : Route, NavKey
}