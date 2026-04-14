package com.gumamobile.mapbystep.ui.main

import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_destination_details
import mapbystep.composeapp.generated.resources.ic_destinations
import mapbystep.composeapp.generated.resources.ic_directions
import mapbystep.composeapp.generated.resources.ic_maps
import mapbystep.composeapp.generated.resources.ic_profile
import mapbystep.composeapp.generated.resources.ic_settings
import mapbystep.composeapp.generated.resources.ic_steps
import mapbystep.composeapp.generated.resources.route_destination_details
import mapbystep.composeapp.generated.resources.route_destinations
import mapbystep.composeapp.generated.resources.route_directions
import mapbystep.composeapp.generated.resources.route_map_progress
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
    MapProgress(
        Res.string.route_map_progress,
        Res.drawable.ic_steps,
    ),
    Directions(
        Res.string.route_directions,
        Res.drawable.ic_directions,
    ),
    Destinations(
        Res.string.route_destinations,
        Res.drawable.ic_destinations,
    ),
    DestinationDetails(
        Res.string.route_destination_details,
        Res.drawable.ic_destination_details,
    ),
    Settings(
        Res.string.route_settings,
        Res.drawable.ic_settings,
    ),
}