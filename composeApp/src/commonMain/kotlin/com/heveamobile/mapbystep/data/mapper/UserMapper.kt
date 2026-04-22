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
        previousTwentyFourHours = this.previousTwentyFourHours,
        twentyFourHourRecord = this.twentyFourHourRecord,
        previousSevenDays = this.previousSevenDays,
        sevenDayRecord = this.sevenDayRecord,
        previousThirtyDays = this.previousThirtyDays,
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
        previousTwentyFourHours = this.previousTwentyFourHours,
        twentyFourHourRecord = this.twentyFourHourRecord,
        previousSevenDays = this.previousSevenDays,
        sevenDayRecord = this.sevenDayRecord,
        previousThirtyDays = this.previousThirtyDays,
        thirtyDayRecord = this.thirtyDayRecord,

    )
}