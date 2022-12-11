package com.appprivategalery.myapplication.domain.di

import android.app.Application
import com.appprivategalery.myapplication.domain.usecase.alarm.DeleteAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.SavedAlarmsUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.SaveAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.alarm.UpdateAlarmUseCase
import com.appprivategalery.myapplication.domain.usecase.event.*
import com.appprivategalery.myapplication.domain.usecase.note.DeleteNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.note.SavedNotesUseCase
import com.appprivategalery.myapplication.domain.usecase.note.SaveNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.note.UpdateNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.voiceNote.DeleteVoiceNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.voiceNote.SavedVoiceNotesUseCase
import com.appprivategalery.myapplication.domain.usecase.voiceNote.SaveVoiceNoteUseCase
import com.appprivategalery.myapplication.presentation.viewmodel.alarm.AlarmsViewModelFactory
import com.appprivategalery.myapplication.presentation.viewmodel.event.EventsViewModelFactory
import com.appprivategalery.myapplication.presentation.viewmodel.note.NotesViewModelFactory
import com.appprivategalery.myapplication.presentation.viewmodel.voiceNote.VoiceNotesModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideNotesViewModelFactory(
        application: Application,
        savedNoteUseCase: SaveNoteUseCase,
        savedNotesUseCase: SavedNotesUseCase,
        updateNoteUseCase: UpdateNoteUseCase,
        deleteNoteUseCase: DeleteNoteUseCase
    ) : NotesViewModelFactory {
        return NotesViewModelFactory(application,savedNoteUseCase,savedNotesUseCase,updateNoteUseCase,deleteNoteUseCase)
    }

    @Singleton
    @Provides
    fun provideVoiceNotesViewModelFactory(
        application: Application,
        saveVoiceNoteUseCase: SaveVoiceNoteUseCase,
        savedVoiceNotesUseCase: SavedVoiceNotesUseCase,
        deleteVoiceNoteUseCase: DeleteVoiceNoteUseCase,
    ) : VoiceNotesModelFactory{
        return VoiceNotesModelFactory(application,saveVoiceNoteUseCase,savedVoiceNotesUseCase,deleteVoiceNoteUseCase)
    }

    @Singleton
    @Provides
    fun provideEventsViewModelFactory(
        application: Application,
        saveEventUseCase: SaveEventUseCase,
        savedEventsUseCase: SavedEventsUseCase,
        updateEventUseCase: UpdateEventUseCase,
        deleteEventUseCase: DeleteEventUseCase,
        eventsOnDayUseCase: EventsOnDayUseCase,
        eventsAtWeekUseCase: EventsAtWeekUseCase
    ) : EventsViewModelFactory{
        return EventsViewModelFactory(application,saveEventUseCase, savedEventsUseCase, updateEventUseCase, deleteEventUseCase,eventsOnDayUseCase, eventsAtWeekUseCase)
    }

    @Singleton
    @Provides
    fun provideAlarmsViewModelFactory(
        application: Application,
        saveAlarmUseCase: SaveAlarmUseCase,
        SavedAlarmsUseCase: SavedAlarmsUseCase,
        updateAlarmUseCase: UpdateAlarmUseCase,
        deleteAlarmUseCase: DeleteAlarmUseCase
    ) : AlarmsViewModelFactory = AlarmsViewModelFactory(application,saveAlarmUseCase,SavedAlarmsUseCase,updateAlarmUseCase,deleteAlarmUseCase)
}