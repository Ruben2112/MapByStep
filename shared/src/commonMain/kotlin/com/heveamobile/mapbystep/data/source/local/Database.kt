package com.heveamobile.mapbystep.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.heveamobile.mapbystep.data.dao.CountryInfoDao
import com.heveamobile.mapbystep.data.dao.DestinationDao
import com.heveamobile.mapbystep.data.dao.MapDao
import com.heveamobile.mapbystep.data.dao.StepDataDao
import com.heveamobile.mapbystep.data.dao.UserDao
import com.heveamobile.mapbystep.data.entity.CountryInfoEntity
import com.heveamobile.mapbystep.data.entity.DestinationEntity
import com.heveamobile.mapbystep.data.entity.MapEntity
import com.heveamobile.mapbystep.data.entity.StepDataEntity
import com.heveamobile.mapbystep.data.entity.UserEntity

const val DATABASE_FILE_NAME = "map_by_step.db"

@Database(
    entities = [
        StepDataEntity::class,
        UserEntity::class,
        MapEntity::class,
        DestinationEntity::class,
        CountryInfoEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    DateTimeConverters::class,
    InfoTypeConverters::class,
    RarityConverters::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStepDataDao(): StepDataDao
    abstract fun getUserDao(): UserDao
    abstract fun getMapDao(): MapDao
    abstract fun getDestinationDao(): DestinationDao
    abstract fun getInfoDao(): CountryInfoDao
}