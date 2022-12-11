package com.appprivategalery.myapplication.presentation.viewmodel.note

import android.app.Application
import androidx.lifecycle.*
import com.appprivategalery.myapplication.domain.usecase.note.DeleteNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.note.SavedNotesUseCase
import com.appprivategalery.myapplication.domain.usecase.note.SaveNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.note.UpdateNoteUseCase

class NotesViewModelFactory(
    private val app: Application,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val savedNotesUseCase: SavedNotesUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(
            app,
            saveNoteUseCase,
            savedNotesUseCase,
            updateNoteUseCase,
            deleteNoteUseCase
        ) as T
    }

}
