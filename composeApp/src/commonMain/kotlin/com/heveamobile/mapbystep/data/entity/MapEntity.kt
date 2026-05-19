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
    val commonDirections: Int = 0,
    val uncommonValue: Int,
    val uncommonDirections: Int = 0,
    val rareValue: Int,
    val rareDirections: Int = 0,
    val epicValue: Int,
    val epicDirections: Int = 0,
    val legendaryValue: Int,
    val legendaryDirections: Int = 0,
    val isOwned: Boolean = false,
)