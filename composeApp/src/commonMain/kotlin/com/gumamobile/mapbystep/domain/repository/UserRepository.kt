package com.gumamobile.mapbystep.domain.repository

import com.gumamobile.mapbystep.domain.model.User
import kotlin.time.Instant

interface UserRepository {
    suspend fun getUser(): User?
    suspend fun createUser(startTime: Instant): User
    suspend fun updateUserSyncTime(
        userId: Long,
        syncTime: Instant,
    )
}