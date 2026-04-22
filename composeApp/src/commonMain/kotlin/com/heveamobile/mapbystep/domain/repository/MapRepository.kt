package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.domain.model.Map
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun importInitialMapCsvData(
        data: String,
        isActive: Boolean = false,
    )

    suspend fun importMap(map: Map)
    fun getMapById(id: String): Map?
    fun getActiveMapFlow(): Flow<Map?>
}