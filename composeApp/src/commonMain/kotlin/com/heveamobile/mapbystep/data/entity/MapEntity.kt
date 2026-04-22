package com.heveamobile.mapbystep.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class InfoType {
    Country,
}

@Entity
data class MapEntity(
    @PrimaryKey
    val id: String,
    val version: Int,
    val infoType: InfoType,
    val name: String,
    val baseDistance: Long,
    val currentLevel: Int = 1,
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