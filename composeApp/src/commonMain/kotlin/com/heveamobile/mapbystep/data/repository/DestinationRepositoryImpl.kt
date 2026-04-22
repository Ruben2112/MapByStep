package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.data.dao.DestinationDao
import com.heveamobile.mapbystep.data.mapper.toDomain
import com.heveamobile.mapbystep.data.mapper.toEntity
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.repository.DestinationRepository
import kotlinx.coroutines.flow.Flow

class DestinationRepositoryImpl(private val destinationDao: DestinationDao) : DestinationRepository {
    override suspend fun upsertDestination(destination: Destination) {
        destinationDao.upsertDestination(destination.toEntity())
    }

    override fun getDestinationsByMapIdFlow(mapId: String): Flow<List<Destination>> {
        TODO("Not yet implemented")
    }

    override fun getDestinationsByMapId(mapId: String): List<Destination> {
        return destinationDao
            .getDestinationsByMapId(mapId)
            .map { it.toDomain() }
    }

    override suspend fun importInitialDestinationCsvData(data: String) {
        val lines = data.lines()
        val destinations = lines
            .drop(1)
            .mapNotNull { line ->
                if (line.isBlank()) return@mapNotNull null
                val columns = line.split(",")
                Destination(
                    id = columns[0],
                    mapId = columns[1],
                    name = columns[3],
                    rarity = columns[4],
                )
            }

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
}