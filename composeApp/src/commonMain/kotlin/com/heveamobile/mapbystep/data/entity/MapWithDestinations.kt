package com.heveamobile.mapbystep.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.heveamobile.mapbystep.domain.model.Rarity

data class MapWithDestinations(
    @Embedded
    val map: MapEntity,

    @Relation(
        entity = DestinationEntity::class,
        parentColumn = "id",
        entityColumn = "mapId",
    )
    val destinations: List<DestinationWithInfo>,
) {
    fun totalDestinationCount() = destinations.size
    fun totalDestinationsVisited() = destinations.count { it.destination.isDiscovered }
    fun commonDestinationCount() = destinations.count { it.destination.rarity == Rarity.Common.intValue }
    fun commonDestinationsVisited() = destinations.count { it.destination.rarity == Rarity.Common.intValue && it.destination.isDiscovered }
    fun uncommonDestinationCount() = destinations.count { it.destination.rarity == Rarity.Uncommon.intValue }
    fun uncommonDestinationsVisited() = destinations.count { it.destination.rarity == Rarity.Uncommon.intValue && it.destination.isDiscovered }
    fun rareDestinationCount() = destinations.count { it.destination.rarity == Rarity.Rare.intValue }
    fun rareDestinationsVisited() = destinations.count { it.destination.rarity == Rarity.Rare.intValue && it.destination.isDiscovered }
    fun epicDestinationCount() = destinations.count { it.destination.rarity == Rarity.Epic.intValue }
    fun epicDestinationsVisited() = destinations.count { it.destination.rarity == Rarity.Epic.intValue && it.destination.isDiscovered }
    fun legendaryDestinationCount() = destinations.count { it.destination.rarity == Rarity.Legendary.intValue }
    fun legendaryDestinationsVisited() = destinations.count { it.destination.rarity == Rarity.Legendary.intValue && it.destination.isDiscovered }
}