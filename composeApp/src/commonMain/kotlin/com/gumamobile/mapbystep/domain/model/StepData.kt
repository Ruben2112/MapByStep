package com.gumamobile.mapbystep.domain.model

import kotlin.time.Instant

data class StepData(
    val startTime: Instant,
    val endTime: Instant,
    val count: Long,
)