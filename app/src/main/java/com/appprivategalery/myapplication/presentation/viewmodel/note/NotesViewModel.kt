package com.appprivategalery.myapplication.presentation.viewmodel.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.appprivategalery.myapplication.data.model.Note
import com.appprivategalery.myapplication.domain.usecase.note.DeleteNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.note.SavedNotesUseCase
import com.appprivategalery.myapplication.domain.usecase.note.SaveNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.note.UpdateNoteUseCase
import kotlinx.coroutines.launch

class NotesViewModel(//change in viewModel = change in viewModelFactory
    private val app: Application,
    private val saveNoteUseCase: SaveNoteUseCase,//provide it in DI.UseCaseModule
    private val savedNotesUseCase: SavedNotesUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : AndroidViewModel(app) {

    fun saveNote(note: Note) = viewModelScope.launch {
        saveNoteUseCase.execute(note)
    }

    fun savedNotes() = liveData {
        savedNotesUseCase.execute().collect {
            emit(it)
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        updateNoteUseCase.execute(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        deleteNoteUseCase.execute(note)
    }


}