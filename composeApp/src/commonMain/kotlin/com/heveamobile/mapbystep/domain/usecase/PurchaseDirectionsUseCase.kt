package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.model.Rarity
import com.heveamobile.mapbystep.domain.repository.MapRepository

class PurchaseDirectionsUseCase(
    private val mapRepository: MapRepository,
) {
    suspend operator fun invoke(
        map: Map,
        cart: kotlin.collections.Map<Rarity, Int>,
        cost: Int,
    ) {
        if (map.currentMapPoints < cost) return

        var updatedCommonDirectionCount = map.commonDirections
        var updatedUncommonDirectionCount = map.uncommonDirections
        var updatedRareDirectionCount = map.rareDirections
        var updatedEpicDirectionCount = map.epicDirections
        var updatedLegendaryDirectionCount = map.legendaryDirections

        cart.forEach {
            when (it.key) {
                Rarity.Common -> updatedCommonDirectionCount += it.value
                Rarity.Uncommon -> updatedUncommonDirectionCount += it.value
                Rarity.Rare -> updatedRareDirectionCount += it.value
                Rarity.Epic -> updatedEpicDirectionCount += it.value
                Rarity.Legendary -> updatedLegendaryDirectionCount += it.value
            }
        }

        mapRepository.updateMap(
            map.copy(
                commonDirections = updatedCommonDirectionCount,
                uncommonDirections = updatedUncommonDirectionCount,
                rareDirections = updatedRareDirectionCount,
                epicDirections = updatedEpicDirectionCount,
                legendaryDirections = updatedLegendaryDirectionCount,
                currentMapPoints = map.currentMapPoints - cost,
            ),
        )
    }
}