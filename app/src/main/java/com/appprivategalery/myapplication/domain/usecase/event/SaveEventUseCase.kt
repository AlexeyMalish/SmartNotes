package com.appprivategalery.myapplication.domain.usecase.event

import android.util.Log
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.domain.repository.EventsRepository

class SaveEventUseCase(private val eventsRepository: EventsRepository) {
    suspend fun execute(event: Event) : Long = eventsRepository.saveEvent(event)
}