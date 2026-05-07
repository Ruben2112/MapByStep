package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Rarity
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import com.heveamobile.mapbystep.domain.repository.MapRepository
import com.heveamobile.mapbystep.domain.repository.UserRepository

class SpendStepsUseCase(
    private val userRepository: UserRepository,
    private val mapRepository: MapRepository,
    private val destinationRepository: DestinationRepository,
) {
    suspend operator fun invoke(): List<Destination> {
        val user = userRepository.getUser()
            ?: return emptyList()
        val activeMap = mapRepository.getActiveMapWithDestinationInfo()
            ?: return emptyList()

        val costPerVisit = activeMap.calculatedDistance
        val totalPossibleVisits = user.availableSteps
            .floorDiv(costPerVisit)
            .toInt()
//        val totalPossibleVisits = 5
        if (totalPossibleVisits <= 0) return emptyList()

        val destinations = activeMap.destinations
        val visitedDestinations = mutableListOf<Destination>()
        var levelUpOccurred = false

        run loop@{
            repeat(totalPossibleVisits) {
                val random = (1..10000).random()
                val targetRarity = when (random) {
                    in 1..8109 -> Rarity.Common
                    in 8110..9609 -> Rarity.Uncommon
                    in 9610..9909 -> Rarity.Rare
                    in 9910..9975 -> Rarity.Epic
                    else -> Rarity.Legendary
                }

                val destination = destinations
                    .filter { it.rarity == targetRarity }
                    .random()

                // We make a copy so we can mark only the first occurrence of the destination as new
                val destinationCopy = destination.copy(
                    isNew = !destination.isDiscovered,
                    isDiscovered = true,
                    totalVisits = destination.totalVisits + 1,
                )

                destination.isDiscovered = true
                destination.totalVisits++

                visitedDestinations.add(destinationCopy)

                if (destinations.all { it.isDiscovered }) {
                    levelUpOccurred = true

                    // 1. Update Map Level
                    mapRepository.updateMap(
                        activeMap.copy(
                            currentLevel = activeMap.currentLevel + 1,
                            currentMapPoints = 0,
                        ),
                    )

                    // 2. Clear discovery flags in DB
                    destinationRepository.resetDiscovered(mapId = activeMap.id)
                    return@loop
                }
            }
        }

        // Mark only first destination in the list as new
        val newlyDiscoveredIds = visitedDestinations
            .filter { it.isNew }
            .map { it.id }
            .toSet()

        // Update destinations
        if (visitedDestinations.isNotEmpty()) {
            val finalToSave = visitedDestinations.map { destination ->
                destination.copy(
                    // If level up happened, they are no longer "discovered" for the new level
                    isDiscovered = !levelUpOccurred,
                )
            }
            destinationRepository.upsertDestinations(finalToSave)
        }

        userRepository.updateUser(
            user.copy(availableSteps = user.availableSteps - (visitedDestinations.size * costPerVisit)),
        )

        // Mark only the first occurrence of the destination as new
        newlyDiscoveredIds.forEach { id ->
            val matchingDestinations = visitedDestinations.filter { it.id == id }
            if (matchingDestinations.size > 1) {
                matchingDestinations.forEachIndexed { index, destination ->
                    destination.isNew = index == 0
                }
            }
        }

        return visitedDestinations.toList()
    }
}