package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface DestinationRepository {
    suspend fun upsertDestination(destination: Destination)
    fun getDestinationsByMapIdFlow(mapId: String): Flow<List<Destination>>
    fun getDestinationsByMapId(mapId: String): List<Destination>
    suspend fun importInitialDestinationCsvData(data: String)
    suspend fun updateVisitCountForDestinationById(
        id: String,
        visits: Int,
    )
}