package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.data.dao.StepDataDao
import com.heveamobile.mapbystep.data.dao.UserDao
import com.heveamobile.mapbystep.data.mapper.toDomain
import com.heveamobile.mapbystep.data.mapper.toEntity
import com.heveamobile.mapbystep.data.source.HealthDataSource
import com.heveamobile.mapbystep.domain.model.StepData
import com.heveamobile.mapbystep.domain.repository.StepDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        //TODO: Delete step data older than 2 months
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
}