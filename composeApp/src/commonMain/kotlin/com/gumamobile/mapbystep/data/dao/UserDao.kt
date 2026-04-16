package com.gumamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gumamobile.mapbystep.data.entity.UserEntity
import com.gumamobile.mapbystep.data.entity.UserWithStepDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("SELECT * FROM UserEntity")
    fun getSteps(): Flow<List<UserWithStepDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: UserEntity)
}