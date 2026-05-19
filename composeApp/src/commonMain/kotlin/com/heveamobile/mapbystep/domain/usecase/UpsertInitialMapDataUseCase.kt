package com.heveamobile.mapbystep.domain.usecase

import com.heveamobile.mapbystep.domain.repository.CountryInfoRepository
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import com.heveamobile.mapbystep.domain.repository.MapRepository
import mapbystep.composeapp.generated.resources.Res

class UpsertInitialMapDataUseCase(
    val mapRepository: MapRepository,
    val destinationRepository: DestinationRepository,
    val countryInfoRepository: CountryInfoRepository,
) {
    val currentMapVersion = 3
    val initialMapId = "4d08314f-2224-4eff-a3bd-8d141d981fad"

    suspend operator fun invoke() {
        val map = mapRepository.getMapById(id = initialMapId)

        val mapBytes = Res.readBytes("files/mCountriesOfTheWorld.csv")
        val mapData = mapBytes.decodeToString()

        val destinationBytes = Res.readBytes("files/dCountriesOfTheWorld.csv")
        val destinationData = destinationBytes.decodeToString()

        val infoBytes = Res.readBytes("files/iCountriesOfTheWorld.csv")
        val infoData = infoBytes.decodeToString()

        map?.let { existingMap ->
            if (existingMap.version < currentMapVersion) {
                val currentDestinations = destinationRepository.getDestinationsByMapId(initialMapId)
                val visitMapping = currentDestinations.associate { it.id to it.totalVisits }

                mapRepository.importInitialMapCsvData(
                    data = mapData,
                )

                countryInfoRepository.importInitialDestinationCsvData(data = infoData)

                destinationRepository.importInitialDestinationCsvData(data = destinationData)

                visitMapping.forEach { (id, visits) ->
                    if (visits > 0) {
                        destinationRepository.updateVisitCountForDestinationById(
                            id = id,
                            visits = visits,
                        )
                    }
                }
            }
        }
            ?: run {
                mapRepository.importInitialMapCsvData(
                    data = mapData,
                    isActive = true,
                )

                countryInfoRepository.importInitialDestinationCsvData(data = infoData)

                destinationRepository.importInitialDestinationCsvData(data = destinationData)
            }
    }
}