package com.appprivategalery.myapplication.presentation.viewmodel.event

import android.app.Application
import androidx.lifecycle.*
import com.appprivategalery.myapplication.domain.usecase.event.*

class EventsViewModelFactory(
    private val app: Application,
    private val saveEventUseCase: SaveEventUseCase,
    private val savedEventsUseCase: SavedEventsUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val eventsOnDayUseCase: EventsOnDayUseCase,
    private val eventsAtWeekUseCase: EventsAtWeekUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventsViewModel(
            app,
            saveEventUseCase,
            savedEventsUseCase,
            updateEventUseCase,
            deleteEventUseCase,
            eventsOnDayUseCase,
            eventsAtWeekUseCase
        ) as T
    }

}
