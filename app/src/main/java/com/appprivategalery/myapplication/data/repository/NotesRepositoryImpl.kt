package com.appprivategalery.myapplication.data.repository

import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.data.repository.dataSource.NotesLocalDataSource
import com.appprivategalery.myapplication.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(private val notesLocalDataSource: NotesLocalDataSource) : NotesRepository {
    override suspend fun saveNote(note: Note) {
        notesLocalDataSource.saveNoteToDB(note)
    }

    override fun savedNotes(): Flow<List<Note>> {
        return notesLocalDataSource.savedNotes()
    }

    override suspend fun updateNote(note: Note) {
        return notesLocalDataSource.updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        notesLocalDataSource.deleteNote(note)
    }
}