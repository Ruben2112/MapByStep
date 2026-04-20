package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.domain.model.StepData
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface StepDataRepository {
    fun getStepsFlow(): Flow<List<StepData>>

    suspend fun saveStepData(
        userId: Long,
        stepData: List<StepData>,
    )

    suspend fun fetchRemoteSteps(
        startTime: Instant,
        endTime: Instant,
    ): List<StepData>
}