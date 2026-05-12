package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.repository.MapRepository
import com.heveamobile.mapbystep.ui.home.SortingOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMapsWithProgressUseCase(
    private val repository: MapRepository,
) {
    operator fun invoke(
        sortOrder: SortingOrder = SortingOrder.Rarity,
        hideUndiscovered: Boolean = false,
    ): Flow<List<Map>> {
        return repository
            .getAllMapsWithProgressFlow() // Get all maps
            .map { maps ->
                maps.map { map ->
                    map.copy(
                        // Update the destinations to be filtered and sorted accordingly
                        destinations = map.destinations
                            .filter { if (hideUndiscovered) it.isDiscovered else true }
                            .sortedWith(
                                when (sortOrder) {
                                    SortingOrder.Rarity -> compareBy(
                                        { it.rarity.intValue },
                                        { it.name },
                                    )

                                    SortingOrder.Alphabetical -> compareBy { it.name }
                                },
                            ),
                    )
                }
            }
    }
}