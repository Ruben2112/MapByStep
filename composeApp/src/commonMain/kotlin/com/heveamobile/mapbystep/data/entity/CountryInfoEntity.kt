package com.heveamobile.mapbystep.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryInfoEntity(
    @PrimaryKey
    val id: String,
    val cca2: String,
    val continents: String,
    val currencies: String,
    val flag: String,
    val languages: String,
    val population: Int,
    val capitals: String,
    val boundingBox: String,
)