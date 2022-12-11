package com.appprivategalery.myapplication.presentation.viewmodel.voiceNote

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appprivategalery.myapplication.domain.usecase.voiceNote.DeleteVoiceNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.voiceNote.SavedVoiceNotesUseCase
import com.appprivategalery.myapplication.domain.usecase.voiceNote.SaveVoiceNoteUseCase

class VoiceNotesModelFactory(
    private val app: Application,
    private val saveVoiceNoteUseCase: SaveVoiceNoteUseCase,
    private val savedVoiceNotesUseCase: SavedVoiceNotesUseCase,
    private val deleteVoiceNoteUseCase: DeleteVoiceNoteUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VoiceNotesViewModel(
            app,
            saveVoiceNoteUseCase,
            savedVoiceNotesUseCase,
            deleteVoiceNoteUseCase
        ) as T
    }

}