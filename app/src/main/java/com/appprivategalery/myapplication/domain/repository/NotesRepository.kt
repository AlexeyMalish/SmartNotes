package com.appprivategalery.myapplication.domain.repository

import com.appprivategalery.myapplication.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun saveNote(note: Note)
    fun savedNotes(): Flow<List<Note>>
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}