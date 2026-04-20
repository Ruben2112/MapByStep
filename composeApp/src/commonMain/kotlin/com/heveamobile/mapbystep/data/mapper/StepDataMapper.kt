package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.StepDataEntity
import com.heveamobile.mapbystep.domain.model.StepData

fun StepDataEntity.toDomain(): StepData {
    return StepData(
        count = this.count,
        startTime = this.startTime,
        endTime = this.endTime,
    )
}

fun StepData.toEntity(userId: Long): StepDataEntity {
    return StepDataEntity(
        count = this.count,
        startTime = this.startTime,
        endTime = this.endTime,
        userId = userId,
    )
}