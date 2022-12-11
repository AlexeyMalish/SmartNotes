package com.appprivategalery.myapplication.presentation.viewmodel.event

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.domain.usecase.event.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventsViewModel(
    private val app: Application,
    private val saveEventUseCase: SaveEventUseCase,
    private val savedEventsUseCase: SavedEventsUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val eventsOnDayUseCase: EventsOnDayUseCase,
    private val eventsAtWeekUseCase: EventsAtWeekUseCase
) : AndroidViewModel(app) {

    suspend fun saveEvent(event: Event) : Long = saveEventUseCase.execute(event)

    fun savedEvents() = liveData {
        savedEventsUseCase.execute().collect {
            emit(it)
        }
    }

    fun updateEvent(event: Event) = viewModelScope.launch {
        updateEventUseCase.execute(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch {
        deleteEventUseCase.execute(event)
    }

    fun getEventsOnCertainDay(localDate: LocalDate) = liveData {
        eventsOnDayUseCase.execute(localDate).collect {
            emit(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getEventsAtCertainWeek(localDate: LocalDate) = liveData {
        eventsAtWeekUseCase.execute(localDate).collect {
            localDate.plusDays(7)
            emit(it)
        }
    }
}