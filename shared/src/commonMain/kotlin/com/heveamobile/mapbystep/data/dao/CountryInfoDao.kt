package com.heveamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.heveamobile.mapbystep.data.entity.CountryInfoEntity

@Dao
interface CountryInfoDao {
    @Upsert
    suspend fun upsertInfos(destinations: List<CountryInfoEntity>)

    @Query("SELECT * FROM CountryInfoEntity WHERE id = :id")
    suspend fun getInfoById(id: String): CountryInfoEntity?

}