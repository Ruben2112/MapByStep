package com.heveamobile.mapbystep.data.source

import com.heveamobile.mapbystep.domain.model.StepData
import kotlin.time.Instant

interface HealthDataSource {
    suspend fun fetchSteps(
        startTime: Instant,
        endTime: Instant,
    ): List<StepData>
}