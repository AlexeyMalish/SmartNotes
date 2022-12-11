package com.appprivategalery.myapplication.domain.usecase.note

import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.domain.repository.NotesRepository

class SaveNoteUseCase(
    private val notesRepository: NotesRepository//provide it in DI.RepositoryModule
    ) {
    suspend fun execute(note:Note) = notesRepository.saveNote(note)
}