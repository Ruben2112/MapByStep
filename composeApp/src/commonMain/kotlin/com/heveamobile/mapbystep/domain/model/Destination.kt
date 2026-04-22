package com.heveamobile.mapbystep.domain.model

data class Destination(
    val id: String,
    val mapId: String,
    val name: String,
    val rarity: String,
    val visits: Int = 0,
)