package com.heveamobile.mapbystep.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.heveamobile.mapbystep.data.dao.DestinationDao
import com.heveamobile.mapbystep.data.dao.MapDao
import com.heveamobile.mapbystep.data.dao.StepDataDao
import com.heveamobile.mapbystep.data.dao.UserDao
import com.heveamobile.mapbystep.data.entity.DestinationEntity
import com.heveamobile.mapbystep.data.entity.MapEntity
import com.heveamobile.mapbystep.data.entity.StepDataEntity
import com.heveamobile.mapbystep.data.entity.UserEntity

@Database(
    entities = [
        StepDataEntity::class,
        UserEntity::class,
        MapEntity::class,
        DestinationEntity::class,
    ],
    version = 5,
)
@TypeConverters(
    DateTimeConverters::class,
    InfoTypeConverters::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStepDataDao(): StepDataDao
    abstract fun getUserDao(): UserDao
    abstract fun getMapDao(): MapDao
    abstract fun getDestinationDao(): DestinationDao
}