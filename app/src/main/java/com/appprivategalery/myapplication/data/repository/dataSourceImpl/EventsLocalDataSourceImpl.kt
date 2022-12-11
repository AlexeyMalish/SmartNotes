package com.appprivategalery.myapplication.data.repository.dataSourceImpl

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.appprivategalery.myapplication.data.db.EventDao
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.repository.dataSource.EventsLocalDataSource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EventsLocalDataSourceImpl(private val eventDao: EventDao) : EventsLocalDataSource {
    override suspend fun saveEventToDB(event: Event) : Long = eventDao.insertEvent(event)

    override fun savedEvents(): Flow<List<Event>> {
        return eventDao.getAllEvents()
    }

    override suspend fun updateEvent(event: Event) {
        return eventDao.updateEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }

    override fun getEventsOnCertainDay(localDate: LocalDate): Flow<List<Event>> {
        return eventDao.getEventsOnCertainDay(localDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getEventsAtCertainWeek(localDate:LocalDate): Flow<List<Event>> {
        return  eventDao.getEventsAtCurrentWeek(localDate,localDate.plusDays(7))
    }

}