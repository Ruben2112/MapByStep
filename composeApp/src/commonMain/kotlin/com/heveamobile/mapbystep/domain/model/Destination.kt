package com.heveamobile.mapbystep.domain.model

data class Destination(
    val id: String,
    val mapId: String,
    val name: String,
    val rarity: Int,
    var isDiscovered: Boolean = false,
    var visits: Int = 0,
)