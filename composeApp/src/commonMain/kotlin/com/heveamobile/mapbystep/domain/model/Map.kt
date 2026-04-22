package com.heveamobile.mapbystep.domain.model

import com.heveamobile.mapbystep.data.entity.InfoType

data class Map(
    val id: String,
    val version: Int,
    val infoType: InfoType,
    val name: String,
    val baseDistance: Long,
    val currentLevel: Int = 1,
    val calculatedDistance: Long = baseDistance * currentLevel,
    val currentMapPoints: Long = 0,
    val commonValue: Int,
    val uncommonValue: Int,
    val rareValue: Int,
    val epicValue: Int,
    val legendaryValue: Int,
    val price: Double? = null,
    val isOwned: Boolean = false,
    val isActive: Boolean = false,
)