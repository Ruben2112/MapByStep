package com.heveamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.heveamobile.mapbystep.data.entity.MapEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MapDao {

    @Query("SELECT * FROM MapEntity")
    fun getMapsFlow(): Flow<List<MapEntity>>

    @Upsert
    suspend fun upsertMap(map: MapEntity)

    @Query("SELECT * FROM MapEntity WHERE id = :id LIMIT 1")
    fun getMapById(id: String): MapEntity?

    @Query("SELECT * FROM MapEntity WHERE isActive = TRUE LIMIT 1")
    fun getActiveMapFlow(): Flow<MapEntity?>
}