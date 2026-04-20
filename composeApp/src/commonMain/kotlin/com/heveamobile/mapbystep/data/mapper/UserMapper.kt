package com.heveamobile.mapbystep.data.mapper

import com.heveamobile.mapbystep.data.entity.UserEntity
import com.heveamobile.mapbystep.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        startTime = this.startTime,
        lastSyncTime = this.lastSyncTime,
        availableSteps = this.availableSteps,
        totalSteps = this.totalSteps,
        twentyFourHourRecord = this.twentyFourHourRecord,
        sevenDayRecord = this.sevenDayRecord,
        thirtyDayRecord = this.thirtyDayRecord,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        startTime = this.startTime,
        lastSyncTime = this.lastSyncTime,
        availableSteps = this.availableSteps,
        totalSteps = this.totalSteps,
        twentyFourHourRecord = this.twentyFourHourRecord,
        sevenDayRecord = this.sevenDayRecord,
        thirtyDayRecord = this.thirtyDayRecord,

    )
}