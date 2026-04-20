package com.heveamobile.mapbystep.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.heveamobile.mapbystep.data.dao.StepDataDao
import com.heveamobile.mapbystep.data.dao.UserDao
import com.heveamobile.mapbystep.data.entity.StepDataEntity
import com.heveamobile.mapbystep.data.entity.UserEntity

@Database(
    entities = [
        StepDataEntity::class,
        UserEntity::class,
    ],
    version = 3,
)
@TypeConverters(DateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStepDataDao(): StepDataDao
    abstract fun getUserDao(): UserDao
}