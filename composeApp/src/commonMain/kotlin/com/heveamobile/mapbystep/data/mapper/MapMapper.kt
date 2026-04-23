package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.MapEntity
import com.heveamobile.mapbystep.domain.model.Map

fun MapEntity.toDomain(): Map {
    return Map(
        id = this.id,
        infoType = this.infoType,
        version = this.version,
        name = this.name,
        baseDistance = this.baseDistance,
        currentLevel = this.currentLevel,
        commonDestinationCount = this.commonDestinationCount,
        commonDestinationsVisited = this.commonDestinationsVisited,
        uncommonDestinationCount = this.uncommonDestinationCount,
        uncommonDestinationsVisited = this.uncommonDestinationsVisited,
        rareDestinationCount = this.rareDestinationCount,
        rareDestinationsVisited = this.rareDestinationsVisited,
        epicDestinationCount = this.epicDestinationCount,
        epicDestinationsVisited = this.epicDestinationsVisited,
        legendaryDestinationCount = this.legendaryDestinationCount,
        legendaryDestinationsVisited = this.legendaryDestinationsVisited,
        currentMapPoints = this.currentMapPoints,
        commonValue = this.commonValue,
        uncommonValue = this.uncommonValue,
        rareValue = this.rareValue,
        epicValue = this.epicValue,
        legendaryValue = this.legendaryValue,
        price = this.price,
        isOwned = this.isOwned,
        isActive = this.isActive,
    )
}

fun Map.toEntity(): MapEntity {
    return MapEntity(
        id = this.id,
        version = this.version,
        infoType = this.infoType,
        name = this.name,
        baseDistance = this.baseDistance,
        currentLevel = this.currentLevel,
        commonDestinationCount = this.commonDestinationCount,
        commonDestinationsVisited = this.commonDestinationsVisited,
        uncommonDestinationCount = this.uncommonDestinationCount,
        uncommonDestinationsVisited = this.uncommonDestinationsVisited,
        rareDestinationCount = this.rareDestinationCount,
        rareDestinationsVisited = this.rareDestinationsVisited,
        epicDestinationCount = this.epicDestinationCount,
        epicDestinationsVisited = this.epicDestinationsVisited,
        legendaryDestinationCount = this.legendaryDestinationCount,
        legendaryDestinationsVisited = this.legendaryDestinationsVisited,
        currentMapPoints = this.currentMapPoints,
        commonValue = this.commonValue,
        uncommonValue = this.uncommonValue,
        rareValue = this.rareValue,
        epicValue = this.epicValue,
        legendaryValue = this.legendaryValue,
        price = this.price,
        isOwned = this.isOwned,
        isActive = this.isActive,
    )
}