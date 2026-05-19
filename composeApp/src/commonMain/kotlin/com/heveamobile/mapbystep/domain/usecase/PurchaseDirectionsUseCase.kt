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

        val newDirections = cart.flatMap { (rarity, count) ->
            List(count) { rarity }
        }

        mapRepository.updateMap(
            map.copy(
                currentMapPoints = map.currentMapPoints - cost,
                directions = map.directions + newDirections,
            ),
        )
    }
}