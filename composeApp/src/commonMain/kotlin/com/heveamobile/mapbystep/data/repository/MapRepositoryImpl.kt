package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.data.dao.MapDao
import com.heveamobile.mapbystep.data.entity.InfoType
import com.heveamobile.mapbystep.data.mapper.toDomain
import com.heveamobile.mapbystep.data.mapper.toEntity
import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRepositoryImpl(
    private val mapDao: MapDao,
) : MapRepository {
    override suspend fun importInitialMapCsvData(
        data: String,
        isActive: Boolean,
    ) {
        val lines = data.lines()
        val map = lines
            .drop(1)
            .mapNotNull { line ->
                if (line.isBlank()) return@mapNotNull null
                val columns = line.split(",")
                Map(
                    id = columns[0],
                    infoType = InfoType.Country,
                    version = columns[1].toInt(),
                    name = columns[2],
                    baseDistance = columns[3].toLong(),
                    commonValue = columns[4].toInt(),
                    uncommonValue = columns[5].toInt(),
                    rareValue = columns[6].toInt(),
                    epicValue = columns[7].toInt(),
                    legendaryValue = columns[8].toInt(),
                    isOwned = true,
                    isActive = isActive,
                )
            }
            .first()
        mapDao.upsertMap(map.toEntity())
    }

    override suspend fun updateMap(map: Map) {
        mapDao.upsertMap(map.toEntity())
    }

    override fun getMapById(id: String): Map? {
        return mapDao
            .getMapWithProgressById(id = id)
            ?.toDomain()
    }

    override fun getAllWithProgressFlow(): Flow<List<Map>> {
        mapDao.getMapsWithProgress()
        return mapDao
            .getMapsWithProgressFlow()
            .map { it.map { mapProgress -> mapProgress.toDomain() } }
    }

    override suspend fun getActiveMapWithDestinationInfo(): Map? {
        return mapDao
            .getActiveMap()
            ?.toDomain()
    }

    override fun getActiveMapFlow(): Flow<Map?> {
        return mapDao
            .getActiveMapWithProgressFlow()
            .map { it?.toDomain() }
    }
}