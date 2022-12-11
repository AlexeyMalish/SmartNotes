package com.appprivategalery.myapplication.data.repository

import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.repository.dataSource.AlarmsLocalDataSource
import com.appprivategalery.myapplication.domain.repository.AlarmsRepository
import kotlinx.coroutines.flow.Flow

class AlarmsRepositoryImpl(private val alarmsLocalDataSource: AlarmsLocalDataSource) : AlarmsRepository {
    override suspend fun saveAlarm(alarm: Alarm): Long = alarmsLocalDataSource.saveAlarmToDB(alarm)

    override fun savedAlarms(): Flow<List<Alarm>> = alarmsLocalDataSource.savedAlarms()

    override suspend fun updateAlarm(alarm: Alarm) = alarmsLocalDataSource.updateAlarm(alarm)

    override suspend fun deleteAlarm(alarm: Alarm)= alarmsLocalDataSource.deleteAlarm(alarm)

}