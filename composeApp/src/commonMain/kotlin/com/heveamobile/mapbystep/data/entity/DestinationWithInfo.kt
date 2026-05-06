package com.heveamobile.mapbystep.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DestinationWithInfo(
    @Embedded
    val destination: DestinationEntity,

    @Relation(
        parentColumn = "infoId",
        entityColumn = "id",
    )
    val countryInfo: CountryInfoEntity?,
)
