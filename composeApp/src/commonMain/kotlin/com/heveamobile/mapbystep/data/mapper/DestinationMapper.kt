package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.DestinationEntity
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Rarity

fun DestinationEntity.toDomain(): Destination {
    return Destination(
        id = this.id,
        mapId = this.mapId,
        name = this.name,
        rarity = Rarity.fromInt(this.rarity),
        isDiscovered = this.isDiscovered,
        visits = this.totalVisits,
    )
}

fun Destination.toEntity(): DestinationEntity {
    return DestinationEntity(
        id = this.id,
        mapId = this.mapId,
        name = this.name,
        rarity = this.rarity.intValue,
        isDiscovered = this.isDiscovered,
        totalVisits = this.visits,
    )
}