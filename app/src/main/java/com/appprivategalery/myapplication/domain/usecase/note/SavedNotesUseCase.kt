package com.appprivategalery.myapplication.domain.usecase.note

import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class SavedNotesUseCase(private val notesRepository: NotesRepository) {
    fun execute(): Flow<List<Note>>{
        return notesRepository.savedNotes()
    }
}