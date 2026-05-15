package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.MapWithDestinations
import com.heveamobile.mapbystep.domain.model.Map

fun MapWithDestinations.toDomain(): Map {
    return Map(
        id = this.map.id,
        version = this.map.version,
        infoType = this.map.infoType,
        name = this.map.name,
        baseDistance = this.map.baseDistance,
        currentLevel = this.map.currentLevel,
        totalDestinationCount = this.totalDestinationCount(),
        totalDestinationsVisited = this.totalDestinationsVisited(),
        commonDestinationCount = this.commonDestinationCount(),
        commonDestinationsVisited = this.commonDestinationsVisited(),
        commonDirections = this.map.commonDirections,
        uncommonDestinationCount = this.uncommonDestinationCount(),
        uncommonDestinationsVisited = this.uncommonDestinationsVisited(),
        uncommonDirections = this.map.uncommonDirections,
        rareDestinationCount = this.rareDestinationCount(),
        rareDestinationsVisited = this.rareDestinationsVisited(),
        rareDirections = this.map.rareDirections,
        epicDestinationCount = this.epicDestinationCount(),
        epicDestinationsVisited = this.epicDestinationsVisited(),
        epicDirections = this.map.epicDirections,
        legendaryDestinationCount = this.legendaryDestinationCount(),
        legendaryDestinationsVisited = this.legendaryDestinationsVisited(),
        legendaryDirections = this.map.legendaryDirections,
        currentMapPoints = this.map.currentMapPoints,
        commonValue = this.map.commonValue,
        uncommonValue = this.map.uncommonValue,
        rareValue = this.map.rareValue,
        epicValue = this.map.epicValue,
        legendaryValue = this.map.legendaryValue,
        isOwned = this.map.isOwned,
        isActive = this.map.isActive,
        destinations = this.destinations.map { it.destination.toDomain(it.countryInfo) },
    )
}