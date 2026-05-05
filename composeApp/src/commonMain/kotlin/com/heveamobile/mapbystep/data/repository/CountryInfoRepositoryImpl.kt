package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.data.dao.CountryInfoDao
import com.heveamobile.mapbystep.data.entity.CountryInfoEntity
import com.heveamobile.mapbystep.data.mapper.toDomain
import com.heveamobile.mapbystep.domain.model.Info
import com.heveamobile.mapbystep.domain.repository.CountryInfoRepository

class CountryInfoRepositoryImpl(private val infoDao: CountryInfoDao) : CountryInfoRepository {
    override suspend fun importInitialDestinationCsvData(data: String) {
        val lines = data.lines()
        val infos = lines
            .drop(1)
            .mapNotNull { line ->
                if (line.isBlank()) return@mapNotNull null
                val columns = line.split(";")
                CountryInfoEntity(
                    id = columns[0],
                    cca2 = columns[1],
                    continents = columns[2],
                    currencies = columns[3],
                    flag = columns[4],
                    languages = columns[5],
                    population = columns[6].toInt(),
                    capitals = columns[7],
                )
            }

        infoDao.upsertInfos(infos)
    }

    override suspend fun getInfoById(id: String): Info? {
        return infoDao
            .getInfoById(id)
            ?.toDomain()
    }
}