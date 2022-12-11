package com.appprivategalery.myapplication.domain.usecase.event

import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.domain.repository.EventsRepository
import kotlinx.coroutines.flow.Flow

class SavedEventsUseCase(private val eventsRepository: EventsRepository) {
    fun execute() : Flow<List<Event>> {
        return eventsRepository.savedEvents()
    }
}