package com.heveamobile.mapbystep.ui.home

import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_destination_info
import mapbystep.composeapp.generated.resources.ic_destinations
import mapbystep.composeapp.generated.resources.ic_directions
import mapbystep.composeapp.generated.resources.ic_maps
import mapbystep.composeapp.generated.resources.ic_profile
import mapbystep.composeapp.generated.resources.ic_settings
import mapbystep.composeapp.generated.resources.route_destination_info
import mapbystep.composeapp.generated.resources.route_destinations
import mapbystep.composeapp.generated.resources.route_directions
import mapbystep.composeapp.generated.resources.route_maps
import mapbystep.composeapp.generated.resources.route_profile
import mapbystep.composeapp.generated.resources.route_settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class NavigationDrawerRoute(
    val routeName: StringResource,
    val icon: DrawableResource,
) {
    Profile(
        Res.string.route_profile,
        Res.drawable.ic_profile,
    ),
    Maps(
        Res.string.route_maps,
        Res.drawable.ic_maps,
    ),
    Destinations(
        Res.string.route_destinations,
        Res.drawable.ic_destinations,
    ),
    DestinationInfo(
        Res.string.route_destination_info,
        Res.drawable.ic_destination_info,
    ),
    Directions(
        Res.string.route_directions,
        Res.drawable.ic_directions,
    ),
    Settings(
        Res.string.route_settings,
        Res.drawable.ic_settings,
    ),
}