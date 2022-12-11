package com.appprivategalery.myapplication.data.repository.dataSource

import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.data.model.Event
import kotlinx.coroutines.flow.Flow

interface AlarmsLocalDataSource {
    suspend fun saveAlarmToDB(alarm: Alarm) : Long
    fun savedAlarms() : Flow<List<Alarm>>
    suspend fun updateAlarm(alarm: Alarm)
    suspend fun deleteAlarm(alarm: Alarm)
}