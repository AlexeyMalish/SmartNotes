package com.appprivategalery.myapplication.domain.usecase.event

import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EventsAtWeekUseCase(private val eventsRepository: EventsRepository) {
    fun execute(localDate: LocalDate) : Flow<List<Event>> {
        return eventsRepository.getEventsAtCertainWeek(localDate)
    }
}