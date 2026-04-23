package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.DestinationEntity
import com.heveamobile.mapbystep.domain.model.Destination

fun DestinationEntity.toDomain(): Destination {
    return Destination(
        id = this.id,
        mapId = this.mapId,
        name = this.name,
        rarity = this.rarity,
        visits = this.totalVisits,
    )
}

fun Destination.toEntity(): DestinationEntity {
    return DestinationEntity(
        id = this.id,
        mapId = this.mapId,
        name = this.name,
        rarity = this.rarity,
        totalVisits = this.visits,
    )
}