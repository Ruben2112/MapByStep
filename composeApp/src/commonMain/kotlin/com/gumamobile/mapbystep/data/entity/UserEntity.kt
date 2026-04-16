package com.gumamobile.mapbystep.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val startTime: Instant,
    val lastSyncTime: Instant? = null,
)
