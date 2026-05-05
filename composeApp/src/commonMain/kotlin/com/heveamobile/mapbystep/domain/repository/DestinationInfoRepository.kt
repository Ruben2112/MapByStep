package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.data.entity.InfoType
import com.heveamobile.mapbystep.domain.model.Info

interface DestinationInfoRepository {
    suspend fun importInitialDestinationCsvData(data: String)
    suspend fun getInfoById(id: String): Info?
}

interface CountryInfoRepository : DestinationInfoRepository

class DestinationInfoRepositoryResolver(
    private val countryInfoRepository: DestinationInfoRepository,
) {
    fun resolve(type: InfoType): DestinationInfoRepository {
        return when (type) {
            InfoType.Country -> countryInfoRepository
        }
    }
}