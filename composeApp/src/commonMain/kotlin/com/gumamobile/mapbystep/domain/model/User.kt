package com.gumamobile.mapbystep.domain.model

import kotlin.time.Instant

data class User(
    val id: Long,
    val startTime: Instant,
    val lastSyncTime: Instant? = null,
)
