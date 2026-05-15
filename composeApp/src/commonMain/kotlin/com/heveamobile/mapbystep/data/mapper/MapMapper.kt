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
        currentMapPoints = this.currentMapPoints,
        commonValue = this.commonValue,
        commonDirections = this.commonDirections,
        uncommonValue = this.uncommonValue,
        uncommonDirections = this.uncommonDirections,
        rareValue = this.rareValue,
        rareDirections = this.rareDirections,
        epicValue = this.epicValue,
        epicDirections = this.epicDirections,
        legendaryValue = this.legendaryValue,
        legendaryDirections = this.legendaryDirections,
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
        currentMapPoints = this.currentMapPoints,
        commonValue = this.commonValue,
        commonDirections = this.commonDirections,
        uncommonValue = this.uncommonValue,
        uncommonDirections = this.uncommonDirections,
        rareValue = this.rareValue,
        rareDirections = this.rareDirections,
        epicValue = this.epicValue,
        epicDirections = this.epicDirections,
        legendaryValue = this.legendaryValue,
        legendaryDirections = this.legendaryDirections,
        isOwned = this.isOwned,
        isActive = this.isActive,
    )
}