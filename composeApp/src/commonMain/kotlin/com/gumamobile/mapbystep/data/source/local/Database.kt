package com.gumamobile.mapbystep.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gumamobile.mapbystep.data.dao.StepDataDao
import com.gumamobile.mapbystep.data.dao.UserDao
import com.gumamobile.mapbystep.data.entity.StepDataEntity
import com.gumamobile.mapbystep.data.entity.UserEntity

@Database(
    entities = [
        StepDataEntity::class,
        UserEntity::class,
    ],
    version = 1,
)
@TypeConverters(DateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStepDataDao(): StepDataDao
    abstract fun getUserDao(): UserDao
}