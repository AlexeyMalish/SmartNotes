package com.appprivategalery.myapplication.domain.usecase.event

import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.domain.repository.EventsRepository

class DeleteEventUseCase(private val eventsRepository: EventsRepository) {
    suspend fun execute(event:Event) = eventsRepository.deleteEvent(event)
}