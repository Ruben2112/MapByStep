package com.heveamobile.mapbystep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.heveamobile.mapbystep.data.entity.UserEntity
import com.heveamobile.mapbystep.data.entity.UserWithStepDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("SELECT * FROM UserEntity LIMIT 1")
    fun getUserFlow(): Flow<UserEntity?>

    @Transaction
    @Query("SELECT * FROM UserEntity LIMIT 1")
    suspend fun getUserWithStepData(): UserWithStepDataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: UserEntity)
}