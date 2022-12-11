package com.appprivategalery.myapplication.presentation.viewmodel.voiceNote

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.appprivategalery.myapplication.data.model.VoiceNote
import com.appprivategalery.myapplication.domain.usecase.voiceNote.DeleteVoiceNoteUseCase
import com.appprivategalery.myapplication.domain.usecase.voiceNote.SavedVoiceNotesUseCase
import com.appprivategalery.myapplication.domain.usecase.voiceNote.SaveVoiceNoteUseCase
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Paths

class VoiceNotesViewModel(
    private val app: Application,
    private val saveVoiceNoteUseCase: SaveVoiceNoteUseCase,
    private val savedVoiceNotesUseCase: SavedVoiceNotesUseCase,
    private val deleteVoiceNoteUseCase: DeleteVoiceNoteUseCase
) : AndroidViewModel(app) {

    fun saveVoiceNote(voiceNote:VoiceNote) = viewModelScope.launch {
        saveVoiceNoteUseCase.execute(voiceNote)
    }

    fun savedVoiceNotes() = liveData {
        savedVoiceNotesUseCase.execute().collect {
            emit(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteVoiceNote(voiceNote: VoiceNote) = viewModelScope.launch {
        Files.delete(Paths.get(voiceNote.memoryLocation))
        deleteVoiceNoteUseCase.execute(voiceNote)
    }

}