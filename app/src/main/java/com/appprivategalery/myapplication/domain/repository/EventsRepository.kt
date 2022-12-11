package com.appprivategalery.myapplication.domain.repository

import com.appprivategalery.myapplication.data.model.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventsRepository {
    suspend fun saveEvent(event:Event) : Long
    fun savedEvents() : Flow<List<Event>>
    suspend fun updateEvent(event: Event)
    suspend fun deleteEvent(event: Event)
    fun getEventsOnCertainDay(localDate : LocalDate) : Flow<List<Event>>
    fun getEventsAtCertainWeek(localDate: LocalDate) : Flow<List<Event>>
}