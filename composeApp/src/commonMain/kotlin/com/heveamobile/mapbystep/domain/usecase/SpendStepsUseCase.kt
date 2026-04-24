package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.data.entity.Rarity
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import com.heveamobile.mapbystep.domain.repository.MapRepository
import com.heveamobile.mapbystep.domain.repository.UserRepository

class SpendStepsUseCase(
    private val userRepository: UserRepository,
    private val mapRepository: MapRepository,
    private val destinationRepository: DestinationRepository,
) {
    suspend operator fun invoke() {
        val user = userRepository.getUser()
            ?: return
        val activeMap = mapRepository.getActiveMap()
            ?: return

        val costPerVisit = activeMap.calculatedDistance
        val totalPossibleVisits = user.availableSteps
            .floorDiv(costPerVisit)
            .toInt()
        if (totalPossibleVisits <= 0) return

        val destinations = activeMap.destinations
        val visitedDestinations = mutableSetOf<Destination>()
        var levelUpOccurred = false

        run loop@{
            repeat(totalPossibleVisits) {
                val random = (1..10000).random()
                val rarityToTarget = when (random) {
                    in 1..8109 -> Rarity.Common
                    in 8110..9609 -> Rarity.Uncommon
                    in 9610..9909 -> Rarity.Rare
                    in 9910..9975 -> Rarity.Epic
                    else -> Rarity.Legendary
                }

                val destination = destinations
                    .filter { it.rarity == rarityToTarget.intValue }
                    .random()

                destination.isDiscovered = true
                destination.visits++
                visitedDestinations.add(destination)

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

        // Update destinations
        if (visitedDestinations.isNotEmpty()) {
            val finalToSave = visitedDestinations.map { destination ->
                destination.copy(
                    // If level up happened, they are no longer "discovered" for the new level
                    isDiscovered = if (levelUpOccurred) false else destination.isDiscovered,
                )
            }
            destinationRepository.upsertDestinations(finalToSave)
        }

        userRepository.updateUser(
            user.copy(availableSteps = user.availableSteps - (visitedDestinations.size * costPerVisit)),
        )
    }
}