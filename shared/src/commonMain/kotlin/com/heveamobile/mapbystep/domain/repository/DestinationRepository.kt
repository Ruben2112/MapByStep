package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.domain.model.Destination

interface DestinationRepository {
    fun getDestinationsByMapId(mapId: String): List<Destination>
    fun getDestinationById(id: String): Destination?
    suspend fun importInitialDestinationCsvData(data: String)
    fun resetDiscovered(mapId: String)
    suspend fun upsertDestinations(destinations: List<Destination>)
    suspend fun updateVisitCountForDestinationById(
        id: String,
        visits: Int,
    )
}