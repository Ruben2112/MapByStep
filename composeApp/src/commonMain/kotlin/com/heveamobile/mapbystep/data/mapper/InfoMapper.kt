package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.CountryInfoEntity
import com.heveamobile.mapbystep.domain.model.Info

fun CountryInfoEntity.toDomain(): Info.CountryInfo {
    return Info.CountryInfo(
        id = this.id,
        cca2 = this.cca2,
        continents = this.continents,
        currencies = this.currencies,
        flag = this.flag,
        languages = this.languages,
        population = this.population,
        capitals = this.capitals,
        boundingBox = this.boundingBox
            .split(",")
            .map { it.toDouble() },
    )
}

fun Info.CountryInfo.toEntity(): CountryInfoEntity {
    return CountryInfoEntity(
        id = this.id,
        cca2 = this.cca2,
        continents = this.continents,
        currencies = this.currencies,
        flag = this.flag,
        languages = this.languages,
        population = this.population,
        capitals = this.capitals,
        boundingBox = this.boundingBox.joinToString(","),
    )
}