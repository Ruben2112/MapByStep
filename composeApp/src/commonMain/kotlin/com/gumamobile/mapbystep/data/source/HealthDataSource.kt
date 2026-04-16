package com.gumamobile.mapbystep.data.source

import com.gumamobile.mapbystep.domain.model.StepData
import kotlin.time.Instant

interface HealthDataSource {
    suspend fun fetchSteps(
        startTime: Instant,
        endTime: Instant,
    ): List<StepData>
}