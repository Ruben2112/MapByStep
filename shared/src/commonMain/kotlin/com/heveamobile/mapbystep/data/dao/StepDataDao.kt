package com.heveamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.heveamobile.mapbystep.data.entity.StepDataEntity
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

@Dao
interface StepDataDao {
    @Query("SELECT * FROM StepDataEntity")
    fun getStepsFlow(): Flow<List<StepDataEntity>>

    @Upsert
    suspend fun upsertSteps(steps: List<StepDataEntity>)

    @Query("DELETE FROM StepDataEntity WHERE endTime < :before")
    suspend fun deleteOutdatedSteps(before: Instant)
}