package com.heveamobile.mapbystep.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class DestinationEntity(
    @PrimaryKey
    val id: String,
    val mapId: String,
    val infoId: String,
    val name: String,
    val rarity: Int,
    val isDiscovered: Boolean = false,
    val totalVisits: Int = 0,
)
