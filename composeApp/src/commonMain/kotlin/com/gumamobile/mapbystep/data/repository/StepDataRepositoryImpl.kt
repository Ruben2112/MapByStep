package com.gumamobile.mapbystep.data.repository

import com.gumamobile.mapbystep.data.dao.StepDataDao
import com.gumamobile.mapbystep.data.dao.UserDao
import com.gumamobile.mapbystep.data.entity.UserEntity
import com.gumamobile.mapbystep.data.mapper.toDomain
import com.gumamobile.mapbystep.data.mapper.toEntity
import com.gumamobile.mapbystep.data.source.HealthDataSource
import com.gumamobile.mapbystep.domain.model.StepData
import com.gumamobile.mapbystep.domain.repository.StepDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.Instant

class StepDataRepositoryImpl(
    private val userDao: UserDao,
    private val stepDao: StepDataDao,
    private val healthDataSource: HealthDataSource,
) : StepDataRepository {

    override fun getStepsFlow(): Flow<List<StepData>> {
        return stepDao
            .getSteps()
            .map { it.map { it.toDomain() } }
    }

    override suspend fun saveStepData(
        userId: Long,
        stepData: List<StepData>,
    ) {
        stepDao.upsertSteps(stepData.map { it.toEntity(userId) })
    }

    override suspend fun fetchRemoteSteps(
        startTime: Instant,
        endTime: Instant,
    ): List<StepData> {
        return healthDataSource.fetchSteps(
            startTime = startTime,
            endTime = endTime,
        )
    }

    private suspend fun getOrCreateUser(): UserEntity {
        var user = userDao.getUser()
        if (user == null) {
            user = UserEntity(
                startTime = Clock.System.now(),
            )
            userDao.upsertUser(user)
            user = userDao.getUser()
        }
        return user!!
    }
}