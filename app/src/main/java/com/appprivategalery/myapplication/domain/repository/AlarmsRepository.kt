package com.appprivategalery.myapplication.domain.repository

import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.data.model.Event
import kotlinx.coroutines.flow.Flow

interface AlarmsRepository {
    suspend fun saveAlarm(alarm: Alarm): Long
    fun savedAlarms(): Flow<List<Alarm>>
    suspend fun updateAlarm(alarm: Alarm)
    suspend fun deleteAlarm(alarm: Alarm)
}