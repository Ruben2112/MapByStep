package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.repository.MapRepository
import mapbystep.composeapp.generated.resources.Res

class UpsertInitialMapDataUseCase(val mapRepository: MapRepository) {
    val currentMapVersion = 1
    val initialMapId = "4d08314f-2224-4eff-a3bd-8d141d981fad"
    suspend operator fun invoke() {
        val map = mapRepository.getMapById(id = initialMapId)
        val bytes = Res.readBytes("files/mCountriesOfTheWorld.csv")
        val data = bytes.decodeToString()

        map?.let { existingMap ->
            if (existingMap.version < currentMapVersion) {
                mapRepository.importInitialMapCsvData(
                    data = data,
                    isActive = existingMap.isActive,
                )

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
                    data = data,
                    isActive = true,
                )
            }
    }
}