package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.model.Rarity

class GetCountOfDirectionsInStockUseCase {
    operator fun invoke(
        map: Map,
        rarity: Rarity,
    ): Int {
        val total = when (rarity) {
            Rarity.Common -> map.commonDestinationCount
            Rarity.Uncommon -> map.uncommonDestinationCount
            Rarity.Rare -> map.rareDestinationCount
            Rarity.Epic -> map.epicDestinationCount
            Rarity.Legendary -> map.legendaryDestinationCount
        }
        val visited = when (rarity) {
            Rarity.Common -> map.commonDestinationsVisited
            Rarity.Uncommon -> map.uncommonDestinationsVisited
            Rarity.Rare -> map.rareDestinationsVisited
            Rarity.Epic -> map.epicDestinationsVisited
            Rarity.Legendary -> map.legendaryDestinationsVisited
        }
        val owned = map.directions.count { it == rarity }

        // total = amount of destinations in map
        // visited = amount of destinations that were already visited
        // owned = amount of directions the user already owns
        return (total - visited - owned).coerceAtLeast(0)
    }
}