package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.domain.model.Map
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun importInitialMapCsvData(
        data: String,
        isActive: Boolean = false,
    )

    suspend fun updateMap(map: Map)
    fun getMapById(id: String): Map?
    fun getAllWithProgressFlow(): Flow<List<Map>>
    suspend fun getActiveMapWithDestinationInfo(): Map?
    fun getActiveMapFlow(): Flow<Map?>
}