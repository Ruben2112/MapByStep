package com.heveamobile.mapbystep.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.heveamobile.mapbystep.domain.model.Rarity

data class MapWithDestinations(
    @Embedded
    val map: MapEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "mapId",
    )
    val destinations: List<DestinationEntity>,
) {
    fun totalDestinationCount() = destinations.size
    fun totalDestinationsVisited() = destinations.count { it.isDiscovered }
    fun commonDestinationCount() = destinations.count { it.rarity == Rarity.Common.intValue }
    fun commonDestinationsVisited() = destinations.count { it.rarity == Rarity.Common.intValue && it.isDiscovered }
    fun uncommonDestinationCount() = destinations.count { it.rarity == Rarity.Uncommon.intValue }
    fun uncommonDestinationsVisited() = destinations.count { it.rarity == Rarity.Uncommon.intValue && it.isDiscovered }
    fun rareDestinationCount() = destinations.count { it.rarity == Rarity.Rare.intValue }
    fun rareDestinationsVisited() = destinations.count { it.rarity == Rarity.Rare.intValue && it.isDiscovered }
    fun epicDestinationCount() = destinations.count { it.rarity == Rarity.Epic.intValue }
    fun epicDestinationsVisited() = destinations.count { it.rarity == Rarity.Epic.intValue && it.isDiscovered }
    fun legendaryDestinationCount() = destinations.count { it.rarity == Rarity.Legendary.intValue }
    fun legendaryDestinationsVisited() = destinations.count { it.rarity == Rarity.Legendary.intValue && it.isDiscovered }
}