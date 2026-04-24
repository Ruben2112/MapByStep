package com.heveamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.heveamobile.mapbystep.data.entity.MapEntity
import com.heveamobile.mapbystep.data.entity.MapWithDestinations
import kotlinx.coroutines.flow.Flow

@Dao
interface MapDao {

    @Transaction
    @Query("SELECT * FROM MapEntity")
    fun getMapsWithProgressFlow(): Flow<List<MapWithDestinations>>

    @Transaction
    @Query("SELECT * FROM MapEntity")
    fun getMapsWithProgress(): List<MapWithDestinations>

    @Upsert
    suspend fun upsertMap(map: MapEntity)


    @Transaction
    @Query("SELECT * FROM MapEntity WHERE id = :id LIMIT 1")
    fun getMapWithProgressById(id: String): MapWithDestinations?

    @Transaction
    @Query("SELECT * FROM MapEntity WHERE isActive = TRUE LIMIT 1")
    fun getActiveMapWithProgressFlow(): Flow<MapWithDestinations?>

    @Query("SELECT * FROM MapEntity WHERE isActive = TRUE LIMIT 1")
    fun getActiveMap(): MapWithDestinations?
}