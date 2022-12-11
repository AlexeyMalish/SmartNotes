package com.appprivategalery.myapplication.domain.di

import com.appprivategalery.myapplication.presentation.adapter.AlarmsAdapter
import com.appprivategalery.myapplication.presentation.adapter.EventsAdapter
import com.appprivategalery.myapplication.presentation.adapter.NotesAdapter
import com.appprivategalery.myapplication.presentation.adapter.VoiceNotesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideNotesAdapter():NotesAdapter{
        return NotesAdapter()
    }

    @Singleton
    @Provides
    fun provideVoiceNotesAdapter():VoiceNotesAdapter{
        return VoiceNotesAdapter()
    }


    @Provides
    fun provideEventsAdapter():EventsAdapter{
        return EventsAdapter()
    }

    @Singleton
    @Provides
    fun provideAlarmsAdapter():AlarmsAdapter{
        return AlarmsAdapter()
    }
}