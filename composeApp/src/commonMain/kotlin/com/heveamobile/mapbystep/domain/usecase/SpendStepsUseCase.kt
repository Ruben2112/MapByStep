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

        // Calculate how many visits we can reward
        val costPerVisit = activeMap.calculatedDistance
        val totalPossibleVisits = user.availableSteps
            .floorDiv(costPerVisit)
            .toInt()
//        val totalPossibleVisits = 1 // For testing purposes
        if (totalPossibleVisits <= 0) return emptyList()

        val destinations = activeMap.destinations
        val directions = activeMap.directions.toMutableList()
        val visitedDestinations = mutableListOf<Destination>()
        var totalMapPointsGained = 0
        var levelUpOccurred = false

        run loop@{
            repeat(totalPossibleVisits) {

                // Select a rarity based on direction or random if no direction is provided
                val direction = directions.removeFirstOrNull()
                val targetRarity: Rarity = direction
                    ?: when ((1..10000).random()) {
                        in 1..8109 -> Rarity.Common
                        in 8110..9609 -> Rarity.Uncommon
                        in 9610..9909 -> Rarity.Rare
                        in 9910..9975 -> Rarity.Epic
                        else -> Rarity.Legendary
                    }

                // Randomly select a destination with the specified rarity. If a direction is provided,
                // the destination has to be undiscovered
                val filteredDestinations = if (direction != null) {
                    destinations.filter { destination ->
                        !destination.isDiscovered && destination.rarity == targetRarity
                    }
                } else {
                    destinations.filter { destination -> destination.rarity == targetRarity }
                }
                val destination = filteredDestinations.random()

                // Reward map points if the destination was already discovered
                if (destination.isDiscovered) {
                    val mapPointsGained = when (destination.rarity) {
                        Rarity.Common -> activeMap.commonValue
                        Rarity.Uncommon -> activeMap.uncommonValue
                        Rarity.Rare -> activeMap.rareValue
                        Rarity.Epic -> activeMap.epicValue
                        Rarity.Legendary -> activeMap.legendaryValue
                    }
                    destination.mapPointsGained = mapPointsGained
                    totalMapPointsGained += mapPointsGained
                }

                // We make a copy so we can mark only the first occurrence of the destination as new
                val destinationCopy = destination.copy(
                    isNew = !destination.isDiscovered,
                    isDiscovered = true,
                    totalVisits = destination.totalVisits + 1,
                )
                visitedDestinations.add(destinationCopy)

                destination.isDiscovered = true
                destination.totalVisits++

                // Update Map Level when all its destinations are discovered
                if (destinations.all { it.isDiscovered }) {
                    levelUpOccurred = true

                    // Break the loop to prevent spending steps on more visits
                    return@loop
                }
            }
        }

        if (levelUpOccurred) {
            // Clear discovery flags in DB if level up occurred
            destinationRepository.resetDiscovered(mapId = activeMap.id)
        } else {
            // Update destinations
            destinationRepository.upsertDestinations(visitedDestinations)
        }

        // Subtract spent steps from user
        userRepository.updateUser(
            user.copy(availableSteps = user.availableSteps - (visitedDestinations.size * costPerVisit)),
        )

        mapRepository.updateMap(
            activeMap.copy(
                currentLevel = if (levelUpOccurred) activeMap.currentLevel + 1 else activeMap.currentLevel,
                currentMapPoints = if (levelUpOccurred) 0 else activeMap.currentMapPoints + totalMapPointsGained,
                directions = if (levelUpOccurred) emptyList() else activeMap.directions,
            ),
        )

        return visitedDestinations.toList()
    }
}