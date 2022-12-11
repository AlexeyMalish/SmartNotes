package com.appprivategalery.myapplication.domain.usecase.note

import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.domain.repository.NotesRepository

class UpdateNoteUseCase(
    private val notesRepository: NotesRepository
) {
    suspend fun execute(note: Note) = notesRepository.updateNote(note)
}