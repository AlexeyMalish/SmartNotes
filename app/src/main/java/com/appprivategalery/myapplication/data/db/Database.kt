package com.appprivategalery.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appprivategalery.myapplication.data.model.Alarm
import com.appprivategalery.myapplication.data.model.Event
import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.data.model.VoiceNote

@Database(
    entities = [
        Note::class,
        VoiceNote::class,
        Event::class,
        Alarm::class
    ], version = 7, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    abstract fun getVoiceNoteDao() : VoiceNoteDao//provide it in di.DataBaseModule
    abstract fun getEventDao() : EventDao
    abstract fun getAlarmDao() : AlarmDao
}