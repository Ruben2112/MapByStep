package com.gumamobile.mapbystep.data.mapper

import com.gumamobile.mapbystep.data.entity.StepDataEntity
import com.gumamobile.mapbystep.domain.model.StepData

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