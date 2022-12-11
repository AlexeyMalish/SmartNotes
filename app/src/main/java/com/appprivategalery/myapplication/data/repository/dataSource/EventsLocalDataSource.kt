package com.appprivategalery.myapplication.data.repository.dataSource

import com.appprivategalery.myapplication.data.model.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventsLocalDataSource {
    suspend fun saveEventToDB(event:Event) : Long
    fun savedEvents() : Flow<List<Event>>
    suspend fun updateEvent(event: Event)
    suspend fun deleteEvent(event: Event)
    fun getEventsOnCertainDay(localDate : LocalDate) : Flow<List<Event>>
    fun getEventsAtCertainWeek(localDate:LocalDate) : Flow<List<Event>>
}