package com.heveamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.heveamobile.mapbystep.data.entity.DestinationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {

    @Upsert
    suspend fun upsertDestination(destination: DestinationEntity)

    @Query("SELECT * FROM DestinationEntity WHERE mapId = :mapId")
    fun getDestinationsByMapIdFlow(mapId: String): Flow<List<DestinationEntity>>

    @Query("SELECT * FROM DestinationEntity WHERE mapId = :mapId")
    fun getDestinationsByMapId(mapId: String): List<DestinationEntity>

    @Upsert
    suspend fun upsertDestinations(destinations: List<DestinationEntity>)

    @Transaction
    @Query("UPDATE DestinationEntity SET isDiscovered = 0 WHERE mapId = :mapId")
    fun resetDiscovered(mapId: String)

    @Query("UPDATE DestinationEntity SET totalVisits = :visits WHERE id = :id")
    suspend fun updateVisitCountForDestinationById(
        id: String,
        visits: Int,
    )
}