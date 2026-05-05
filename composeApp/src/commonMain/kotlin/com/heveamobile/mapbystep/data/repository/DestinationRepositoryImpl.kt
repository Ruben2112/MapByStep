package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.data.dao.DestinationDao
import com.heveamobile.mapbystep.data.entity.DestinationEntity
import com.heveamobile.mapbystep.data.mapper.toDomain
import com.heveamobile.mapbystep.data.mapper.toEntity
import com.heveamobile.mapbystep.domain.infrastructure.FileStorage
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import mapbystep.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.MissingResourceException

class DestinationRepositoryImpl(
    private val destinationDao: DestinationDao,
    private val fileStorage: FileStorage,
) : DestinationRepository {
    override suspend fun upsertDestination(destination: Destination) {
        destinationDao.upsertDestination(destination.toEntity())
    }

    override fun getDestinationsByMapId(mapId: String): List<Destination> {
        return destinationDao
            .getDestinationsByMapId(mapId)
            .map { it.toDomain() }
    }

    override fun getDestinationById(id: String): Destination? {
        return destinationDao
            .getDestinationById(id = id)
            ?.toDomain()
    }

    override suspend fun importInitialDestinationCsvData(data: String) {
        val lines = data.lines()
        val destinations = lines
            .drop(1)
            .mapNotNull { line ->
                if (line.isBlank()) return@mapNotNull null
                val columns = line.split(";")
                DestinationEntity(
                    id = columns[0],
                    mapId = columns[1],
                    infoId = columns[2],
                    name = columns[3],
                    rarity = columns[4].toInt(),
                )
            }

        destinationDao.upsertDestinations(destinations)
        writeBundledImagesToDisk(destinations)
    }

    override fun resetDiscovered(mapId: String) {
        destinationDao.resetDiscovered(mapId = mapId)
    }

    override suspend fun upsertDestinations(destinations: List<Destination>) {
        destinationDao.upsertDestinations(destinations.map { it.toEntity() })
    }

    override suspend fun updateVisitCountForDestinationById(
        id: String,
        visits: Int,
    ) {
        destinationDao.updateVisitCountForDestinationById(
            id = id,
            visits = visits,
        )
    }

    suspend fun writeBundledImagesToDisk(destinations: List<DestinationEntity>) {
        destinations.forEach { destination ->
            try {
                val path = "${destination.mapId}/${destination.id}.svg"
                if (!fileStorage.exists(path)) {
                    val bytes = Res.readBytes("files/cotw_${destination.id}.svg")
                    fileStorage.saveFile(
                        path,
                        bytes,
                    )
                }
            } catch (e: MissingResourceException) {
                println("Could not find svg for destination ${destination.name}")
            }
        }
    }
}