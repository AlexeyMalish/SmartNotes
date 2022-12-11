package com.appprivategalery.myapplication.data.repository.dataSourceImpl

import com.appprivategalery.myapplication.data.db.AlarmDao
import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.repository.dataSource.AlarmsLocalDataSource
import kotlinx.coroutines.flow.Flow

class AlarmsLocalDataSourceImpl(private val alarmDao: AlarmDao) : AlarmsLocalDataSource {
    override suspend fun saveAlarmToDB(alarm: Alarm) : Long = alarmDao.insert(alarm)

    override fun savedAlarms(): Flow<List<Alarm>> = alarmDao.getAll()

    override suspend fun updateAlarm(alarm: Alarm) = alarmDao.update(alarm)

    override suspend fun deleteAlarm(alarm: Alarm) = alarmDao.delete(alarm)
}