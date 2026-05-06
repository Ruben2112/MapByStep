package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.CountryInfoEntity
import com.heveamobile.mapbystep.data.entity.DestinationEntity
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Rarity

fun DestinationEntity.toDomain(countryInfo: CountryInfoEntity? = null): Destination {
    return Destination(
        id = this.id,
        mapId = this.mapId,
        infoId = this.infoId,
        name = this.name,
        rarity = Rarity.fromInt(this.rarity),
        isDiscovered = this.isDiscovered,
        totalVisits = this.totalVisits,
        info = countryInfo?.toDomain(),
    )
}

fun Destination.toEntity(): DestinationEntity {
    return DestinationEntity(
        id = this.id,
        mapId = this.mapId,
        infoId = this.infoId,
        name = this.name,
        rarity = this.rarity.intValue,
        isDiscovered = this.isDiscovered,
        totalVisits = this.totalVisits,
    )
}