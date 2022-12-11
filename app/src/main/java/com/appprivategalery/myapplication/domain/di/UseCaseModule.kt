package com.appprivategalery.myapplication.domain.di

import com.appprivategalery.myapplication.domain.repository.AlarmsRepository
import com.appprivategalery.myapplication.domain.repository.EventsRepository
import com.appprivategalery.myapplication.domain.repository.NotesRepository
import com.appprivategalery.myapplication.domain.repository.VoiceNotesRepository
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideSaveNoteUseCase(
        notesRepository: NotesRepository
    ): SaveNoteUseCase {
        return SaveNoteUseCase(notesRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNotesUseCase(
        notesRepository: NotesRepository
    ): SavedNotesUseCase {
        return SavedNotesUseCase(notesRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateNoteUseCase(
        notesRepository: NotesRepository
    ): UpdateNoteUseCase {
        return UpdateNoteUseCase(notesRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteNoteUseCase(
        notesRepository: NotesRepository
    ): DeleteNoteUseCase {
        return DeleteNoteUseCase(notesRepository)
    }

    @Singleton
    @Provides
    fun provideSaveVoiceNoteUseCase(
        voiceNotesRepository: VoiceNotesRepository
    ): SaveVoiceNoteUseCase {
        return SaveVoiceNoteUseCase(voiceNotesRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedVoiceNotesUseCase(
        voiceNotesRepository: VoiceNotesRepository
    ): SavedVoiceNotesUseCase {
        return SavedVoiceNotesUseCase(voiceNotesRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteVoiceNoteUseCase(
        voiceNotesRepository: VoiceNotesRepository
    ): DeleteVoiceNoteUseCase {
        return DeleteVoiceNoteUseCase(voiceNotesRepository)
    }

    @Singleton
    @Provides
    fun provideSaveEventUseCase(
        eventsRepository: EventsRepository
    ): SaveEventUseCase {
        return SaveEventUseCase(eventsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedEventsUseCase(
        eventsRepository: EventsRepository
    ): SavedEventsUseCase {
        return SavedEventsUseCase(eventsRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateEventUseCase(
        eventsRepository: EventsRepository
    ): UpdateEventUseCase {
        return UpdateEventUseCase(eventsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteEventUseCase(
        eventsRepository: EventsRepository
    ): DeleteEventUseCase {
        return DeleteEventUseCase(eventsRepository)
    }

    @Singleton
    @Provides
    fun provideGetEventsOnCertainDay(
        eventsRepository: EventsRepository
    ): EventsOnDayUseCase {
        return EventsOnDayUseCase(eventsRepository)
    }

    @Singleton
    @Provides
    fun provideGetEventsAtCertainWeek(
        eventsRepository: EventsRepository
    ): EventsAtWeekUseCase = EventsAtWeekUseCase(eventsRepository)

    @Singleton
    @Provides
    fun provideSaveAlarmUseCase(
        alarmsRepository: AlarmsRepository
    ): SaveAlarmUseCase = SaveAlarmUseCase(alarmsRepository)

    @Singleton
    @Provides
    fun provideSavedAlarmsUseCase(
        alarmsRepository: AlarmsRepository
    ): SavedAlarmsUseCase = SavedAlarmsUseCase(alarmsRepository)

    @Singleton
    @Provides
    fun provideUpdateAlarmsUseCase(
        alarmsRepository: AlarmsRepository
    ): UpdateAlarmUseCase = UpdateAlarmUseCase(alarmsRepository)

    @Singleton
    @Provides
    fun provideDeleteAlarmsUseCase(
        alarmsRepository: AlarmsRepository
    ): DeleteAlarmUseCase = DeleteAlarmUseCase(alarmsRepository)
}