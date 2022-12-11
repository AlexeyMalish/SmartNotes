package com.appprivategalery.myapplication.domain.di

import com.appprivategalery.myapplication.data.repository.AlarmsRepositoryImpl
import com.appprivategalery.myapplication.data.repository.EventsRepositoryImpl
import com.appprivategalery.myapplication.data.repository.NotesRepositoryImpl
import com.appprivategalery.myapplication.data.repository.VoiceNotesRepositoryImpl
import com.appprivategalery.myapplication.data.repository.dataSource.AlarmsLocalDataSource
import com.appprivategalery.myapplication.data.repository.dataSource.EventsLocalDataSource
import com.appprivategalery.myapplication.data.repository.dataSource.NotesLocalDataSource
import com.appprivategalery.myapplication.data.repository.dataSource.VoiceNotesLocalDataSource
import com.appprivategalery.myapplication.domain.repository.AlarmsRepository
import com.appprivategalery.myapplication.domain.repository.EventsRepository
import com.appprivategalery.myapplication.domain.repository.NotesRepository
import com.appprivategalery.myapplication.domain.repository.VoiceNotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNotesRepository(
        notesLocalDataSource:NotesLocalDataSource
    ): NotesRepository{
        return NotesRepositoryImpl(notesLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideVoiceNotesRepository(
        voiceNotesLocalDataSource: VoiceNotesLocalDataSource
    ) : VoiceNotesRepository{
        return VoiceNotesRepositoryImpl(voiceNotesLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideEventsRepository(
        eventsLocalDataSource: EventsLocalDataSource
    ) : EventsRepository{
        return EventsRepositoryImpl(eventsLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideAlarmsRepository(
        alarmsLocalDataSource: AlarmsLocalDataSource
    ) : AlarmsRepository{
        return AlarmsRepositoryImpl(alarmsLocalDataSource)
    }
}