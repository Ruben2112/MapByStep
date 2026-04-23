package com.heveamobile.mapbystep.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

enum class Rarity(val intValue: Int) {
    Common(1),
    Uncommon(2),
    Rare(3),
    Epic(4),
    Legendary(5),
}

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
    val name: String,
    val rarity: Int,
    val isDiscovered: Boolean = false,
    val totalVisits: Int = 0,
)
