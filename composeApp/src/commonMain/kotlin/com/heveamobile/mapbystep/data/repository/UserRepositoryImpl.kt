package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.data.dao.UserDao
import com.heveamobile.mapbystep.data.entity.UserEntity
import com.heveamobile.mapbystep.data.mapper.toDomain
import com.heveamobile.mapbystep.data.mapper.toEntity
import com.heveamobile.mapbystep.domain.model.User
import com.heveamobile.mapbystep.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class UserRepositoryImpl(
    private val userDao: UserDao,
) : UserRepository {
    override fun getUserFlow(): Flow<User?> {
        return userDao
            .getUserFlow()
            .map { it?.toDomain() }
    }

    override suspend fun getUserWithStepData(): User? {
        return userDao
            .getUserWithStepData()
            ?.toDomain()
    }

    override suspend fun createUser() {
        userDao.upsertUser(UserEntity(startTime = Clock.System.now()))
    }

    override suspend fun updateUser(
        user: User,
    ) {
        userDao.upsertUser(
            user.toEntity(),
        )
    }
}