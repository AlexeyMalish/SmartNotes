package com.appprivategalery.myapplication.domain.di

import com.appprivategalery.myapplication.data.db.AlarmDao
import com.appprivategalery.myapplication.data.db.EventDao
import com.appprivategalery.myapplication.data.db.NoteDao
import com.appprivategalery.myapplication.data.db.VoiceNoteDao
import com.appprivategalery.myapplication.data.repository.dataSource.AlarmsLocalDataSource
import com.appprivategalery.myapplication.data.repository.dataSource.EventsLocalDataSource
import com.appprivategalery.myapplication.data.repository.dataSource.NotesLocalDataSource
import com.appprivategalery.myapplication.data.repository.dataSource.VoiceNotesLocalDataSource
import com.appprivategalery.myapplication.data.repository.dataSourceImpl.AlarmsLocalDataSourceImpl
import com.appprivategalery.myapplication.data.repository.dataSourceImpl.EventsLocalDataSourceImpl
import com.appprivategalery.myapplication.data.repository.dataSourceImpl.NotesLocalDataSourceImpl
import com.appprivategalery.myapplication.data.repository.dataSourceImpl.VoiceNotesLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideNotesLocalDataSource(noteDao: NoteDao) : NotesLocalDataSource{
        return NotesLocalDataSourceImpl(noteDao)
    }

    @Singleton
    @Provides
    fun provideVoiceNotesLocalDataSource(voiceNoteDao:VoiceNoteDao) : VoiceNotesLocalDataSource{
        return VoiceNotesLocalDataSourceImpl(voiceNoteDao)
    }

    @Singleton
    @Provides
    fun provideEventsLocalDataSource(eventDao: EventDao) : EventsLocalDataSource{
        return EventsLocalDataSourceImpl(eventDao)
    }

    @Singleton
    @Provides
    fun provideAlarmsLocalDataSource(alarmDao: AlarmDao) : AlarmsLocalDataSource = AlarmsLocalDataSourceImpl(alarmDao)
}