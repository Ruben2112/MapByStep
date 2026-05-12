package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.DestinationWithInfo
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Rarity

fun DestinationWithInfo.toDomain(): Destination {
    return Destination(
        id = this.destination.id,
        mapId = this.destination.mapId,
        infoId = this.destination.infoId,
        name = this.destination.name,
        rarity = Rarity.fromInt(this.destination.rarity),
        isDiscovered = this.destination.isDiscovered,
        totalVisits = this.destination.totalVisits,
        info = this.countryInfo?.toDomain(),
    )
}