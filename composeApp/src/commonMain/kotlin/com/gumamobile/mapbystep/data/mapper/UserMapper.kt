package com.gumamobile.mapbystep.data.mapper

import com.gumamobile.mapbystep.data.entity.UserEntity
import com.gumamobile.mapbystep.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        startTime = this.startTime,
        lastSyncTime = this.lastSyncTime,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        startTime = this.startTime,
        lastSyncTime = this.lastSyncTime,
    )
}