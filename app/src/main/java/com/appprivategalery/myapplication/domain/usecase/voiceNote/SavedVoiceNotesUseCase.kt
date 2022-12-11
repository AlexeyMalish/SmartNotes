package com.appprivategalery.myapplication.domain.usecase.voiceNote

import com.appprivategalery.myapplication.data.model.VoiceNote
import com.appprivategalery.myapplication.domain.repository.VoiceNotesRepository
import kotlinx.coroutines.flow.Flow

class SavedVoiceNotesUseCase
    (
    private val voiceNotesRepository: VoiceNotesRepository
) {
    fun execute(): Flow<List<VoiceNote>> {
        return voiceNotesRepository.savedVoiceNotes()
    }
}