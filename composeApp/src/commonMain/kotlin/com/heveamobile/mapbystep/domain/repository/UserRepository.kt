package com.heveamobile.mapbystep.domain.repository

import com.heveamobile.mapbystep.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): User?
    fun getUserFlow(): Flow<User?>
    suspend fun getUserWithStepData(): User?
    suspend fun createUser()
    suspend fun updateUser(user: User)
}