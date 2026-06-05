package com.heveamobile.mapbystep.ui.home

import com.heveamobile.mapbystep.navigation.Route
import mapbystep.shared.generated.resources.Res
import mapbystep.shared.generated.resources.ic_destination_info
import mapbystep.shared.generated.resources.ic_destinations
import mapbystep.shared.generated.resources.ic_directions
import mapbystep.shared.generated.resources.ic_maps
import mapbystep.shared.generated.resources.ic_profile
import mapbystep.shared.generated.resources.ic_settings
import mapbystep.shared.generated.resources.navigation_route_destination_info
import mapbystep.shared.generated.resources.navigation_route_destinations
import mapbystep.shared.generated.resources.navigation_route_directions
import mapbystep.shared.generated.resources.navigation_route_maps
import mapbystep.shared.generated.resources.navigation_route_profile
import mapbystep.shared.generated.resources.navigation_route_settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class NavigationDrawerRoute(
    val routeName: StringResource,
    val icon: DrawableResource,
) {
    Profile(
        Res.string.navigation_route_profile,
        Res.drawable.ic_profile,
    ),
    Maps(
        Res.string.navigation_route_maps,
        Res.drawable.ic_maps,
    ),
    Destinations(
        Res.string.navigation_route_destinations,
        Res.drawable.ic_destinations,
    ),
    DestinationInfo(
        Res.string.navigation_route_destination_info,
        Res.drawable.ic_destination_info,
    ),
    Directions(
        Res.string.navigation_route_directions,
        Res.drawable.ic_directions,
    ),
    Settings(
        Res.string.navigation_route_settings,
        Res.drawable.ic_settings,
    )
}

fun NavigationDrawerRoute.toRoute(): Route {
    return when (this) {
        NavigationDrawerRoute.Profile -> Route.Profile
        NavigationDrawerRoute.Maps -> Route.Maps
        NavigationDrawerRoute.Destinations -> Route.Destinations
        NavigationDrawerRoute.DestinationInfo -> Route.DestinationInfo(destinationId = null)
        NavigationDrawerRoute.Directions -> Route.Directions
        NavigationDrawerRoute.Settings -> Route.Settings
    }
}