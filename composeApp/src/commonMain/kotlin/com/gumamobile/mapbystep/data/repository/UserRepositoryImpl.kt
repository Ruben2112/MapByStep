package com.gumamobile.mapbystep.data.repository

import com.gumamobile.mapbystep.data.dao.UserDao
import com.gumamobile.mapbystep.data.entity.UserEntity
import com.gumamobile.mapbystep.data.mapper.toDomain
import com.gumamobile.mapbystep.domain.model.User
import com.gumamobile.mapbystep.domain.repository.UserRepository
import kotlin.time.Instant

class UserRepositoryImpl(
    private val userDao: UserDao,
) : UserRepository {
    override suspend fun getUser(): User? {
        return userDao
            .getUser()
            ?.toDomain()
    }

    override suspend fun createUser(startTime: Instant): User {
        userDao.upsertUser(UserEntity(startTime = startTime))
        return userDao
            .getUser()
            ?.toDomain()
            ?: throw IllegalStateException("User not found")
    }

    override suspend fun updateUserSyncTime(
        userId: Long,
        syncTime: Instant,
    ) {
        val user = userDao.getUser()
            ?: throw IllegalStateException("User not found")
        userDao.upsertUser(user.copy(lastSyncTime = syncTime))
    }
}