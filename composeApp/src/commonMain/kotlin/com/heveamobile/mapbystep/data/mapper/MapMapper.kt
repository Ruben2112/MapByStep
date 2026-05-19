package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.MapEntity
import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.model.Rarity

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
        uncommonValue = this.uncommonValue,
        rareValue = this.rareValue,
        epicValue = this.epicValue,
        legendaryValue = this.legendaryValue,
        isOwned = this.isOwned,
        directions = mutableListOf<Rarity>()
            .apply {
                repeat(commonDirections) { add(Rarity.Common) }
                repeat(uncommonDirections) { add(Rarity.Uncommon) }
                repeat(rareDirections) { add(Rarity.Rare) }
                repeat(epicDirections) { add(Rarity.Epic) }
                repeat(legendaryDirections) { add(Rarity.Legendary) }
            }
            .sortedByDescending { it.intValue },
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
        commonDirections = this.directions.count { it == Rarity.Common },
        uncommonValue = this.uncommonValue,
        uncommonDirections = this.directions.count { it == Rarity.Uncommon },
        rareValue = this.rareValue,
        rareDirections = this.directions.count { it == Rarity.Rare },
        epicValue = this.epicValue,
        epicDirections = this.directions.count { it == Rarity.Epic },
        legendaryValue = this.legendaryValue,
        legendaryDirections = this.directions.count { it == Rarity.Legendary },
        isOwned = this.isOwned,
    )
}