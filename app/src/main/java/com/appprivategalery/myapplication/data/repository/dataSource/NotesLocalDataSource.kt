package com.appprivategalery.myapplication.data.repository.dataSource

import com.appprivategalery.myapplication.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesLocalDataSource {//connects with Impl
    suspend fun saveNoteToDB(note:Note)
    fun savedNotes(): Flow<List<Note>>
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}