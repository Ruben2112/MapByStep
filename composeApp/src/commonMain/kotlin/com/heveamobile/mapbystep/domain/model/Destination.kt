package com.heveamobile.mapbystep.domain.model

data class Destination(
    val id: String,
    val mapId: String,
    val name: String,
    val rarity: Int,
    val visits: Int = 0,
)