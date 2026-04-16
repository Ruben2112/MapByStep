package com.gumamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.gumamobile.mapbystep.data.entity.StepDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDataDao {
    @Query("SELECT * FROM StepDataEntity")
    fun getSteps(): Flow<List<StepDataEntity>>

    @Upsert
    suspend fun upsertSteps(steps: List<StepDataEntity>)
}