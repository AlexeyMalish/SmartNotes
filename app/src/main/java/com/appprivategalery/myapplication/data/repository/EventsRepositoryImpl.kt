package com.appprivategalery.myapplication.data.repository

import android.util.Log
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.repository.dataSource.EventsLocalDataSource
import com.appprivategalery.myapplication.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EventsRepositoryImpl(
    private val eventsLocalDataSource: EventsLocalDataSource
) : EventsRepository {
    override suspend fun saveEvent(event: Event): Long = eventsLocalDataSource.saveEventToDB(event)

    override fun savedEvents(): Flow<List<Event>> {
        return eventsLocalDataSource.savedEvents()
    }

    override suspend fun updateEvent(event: Event) {
        eventsLocalDataSource.updateEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        eventsLocalDataSource.deleteEvent(event)
    }

    override fun getEventsOnCertainDay(localDate: LocalDate): Flow<List<Event>> {
        return eventsLocalDataSource.getEventsOnCertainDay(localDate)
    }

    override fun getEventsAtCertainWeek(localDate: LocalDate): Flow<List<Event>> {
        return eventsLocalDataSource.getEventsAtCertainWeek(localDate)
    }
}