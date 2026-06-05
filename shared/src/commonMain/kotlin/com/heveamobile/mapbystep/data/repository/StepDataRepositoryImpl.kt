package com.heveamobile.mapbystep.data.repository

import com.heveamobile.mapbystep.data.dao.StepDataDao
import com.heveamobile.mapbystep.data.mapper.toEntity
import com.heveamobile.mapbystep.data.source.remote.HealthDataSource
import com.heveamobile.mapbystep.domain.model.StepData
import com.heveamobile.mapbystep.domain.repository.StepDataRepository
import kotlin.time.Instant

class StepDataRepositoryImpl(
    private val stepDao: StepDataDao,
    private val healthDataSource: HealthDataSource,
) : StepDataRepository {

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

    override suspend fun deleteOutdatedData(before: Instant) {
        stepDao.deleteOutdatedSteps(before = before)
    }
}