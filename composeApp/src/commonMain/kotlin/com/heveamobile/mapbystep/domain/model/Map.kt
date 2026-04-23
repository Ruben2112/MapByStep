package com.heveamobile.mapbystep.domain.model

import com.heveamobile.mapbystep.data.entity.InfoType
import com.heveamobile.mapbystep.data.entity.Rarity

data class Map(
    val id: String,
    val version: Int,
    val infoType: InfoType,
    val name: String,
    val baseDistance: Long,
    val currentLevel: Int = 1,
    val totalDestinationCount: Int = 0,
    val totalDestinationsVisited: Int = 0,
    val commonDestinationCount: Int = 0,
    val commonDestinationsVisited: Int = 0,
    val uncommonDestinationCount: Int = 0,
    val uncommonDestinationsVisited: Int = 0,
    val rareDestinationCount: Int = 0,
    val rareDestinationsVisited: Int = 0,
    val epicDestinationCount: Int = 0,
    val epicDestinationsVisited: Int = 0,
    val legendaryDestinationCount: Int = 0,
    val legendaryDestinationsVisited: Int = 0,
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
) {
    fun formatProgress(rarity: Rarity?): String {
        val visited: Int
        val total: Int

        when (rarity) {
            Rarity.Common -> {
                visited = commonDestinationsVisited
                total = commonDestinationCount
            }

            Rarity.Uncommon -> {
                visited = uncommonDestinationsVisited
                total = uncommonDestinationCount
            }

            Rarity.Rare -> {
                visited = rareDestinationsVisited
                total = rareDestinationCount
            }

            Rarity.Epic -> {
                visited = epicDestinationsVisited
                total = epicDestinationCount
            }

            Rarity.Legendary -> {
                visited = legendaryDestinationsVisited
                total = legendaryDestinationCount
            }

            null -> {
                visited = totalDestinationsVisited
                total = totalDestinationCount
            }
        }

        val progressPercentage = if (total == 0) 0 else visited.floorDiv(total) * 100
        return "$visited / $total ($progressPercentage%)"
    }
}