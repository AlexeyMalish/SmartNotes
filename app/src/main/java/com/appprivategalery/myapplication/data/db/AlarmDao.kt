package com.appprivategalery.myapplication.data.db

import androidx.room.*
import com.appprivategalery.myapplication.data.model.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(alarm : Alarm) : Long

    @Query("SELECT * FROM alarms ORDER BY id ASC")
    fun getAll(): Flow<List<Alarm>>

    @Update
    suspend fun update(alarm: Alarm)

    @Delete
    suspend fun delete(alarm: Alarm)
}