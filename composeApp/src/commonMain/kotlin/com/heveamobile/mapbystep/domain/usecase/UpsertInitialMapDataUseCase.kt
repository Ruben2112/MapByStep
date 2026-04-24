package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import com.heveamobile.mapbystep.domain.repository.MapRepository
import mapbystep.composeapp.generated.resources.Res

class UpsertInitialMapDataUseCase(
    val mapRepository: MapRepository,
    val destinationRepository: DestinationRepository,
) {
    val currentMapVersion = 2
    val initialMapId = "4d08314f-2224-4eff-a3bd-8d141d981fad"

    suspend operator fun invoke() {
        val map = mapRepository.getMapById(id = initialMapId)

        val mapBytes = Res.readBytes("files/mCountriesOfTheWorld.csv")
        val mapData = mapBytes.decodeToString()

        val destinationBytes = Res.readBytes("files/dCountriesOfTheWorld.csv")
        val destinationData = destinationBytes.decodeToString()

        map?.let { existingMap ->
            if (existingMap.version < currentMapVersion) {
                val currentDestinations = destinationRepository.getDestinationsByMapId(initialMapId)
                val visitMapping = currentDestinations.associate { it.id to it.visits }

                mapRepository.importInitialMapCsvData(
                    data = mapData,
                    isActive = existingMap.isActive,
                )

                destinationRepository.importInitialDestinationCsvData(
                    data = destinationData,
                )

                visitMapping.forEach { (id, visits) ->
                    if (visits > 0) {
                        destinationRepository.updateVisitCountForDestinationById(
                            id = id,
                            visits = visits,
                        )
                    }
                }

                /** TODO Migrate existing Destination data. Fetch current Destinations of Map,
                 * create a mapping of [destinationId:visits], delete Destinations of Map, import
                 * new Destinations an update visits of the Destinations by ID in the created
                 * mapping. All other data is immutable anyway so no need to migrate. This makes
                 * it easy to add or remove columns.
                 */
            }
        }
            ?: run {
                mapRepository.importInitialMapCsvData(
                    data = mapData,
                    isActive = true,
                )

                destinationRepository.importInitialDestinationCsvData(data = destinationData)
            }
    }
}