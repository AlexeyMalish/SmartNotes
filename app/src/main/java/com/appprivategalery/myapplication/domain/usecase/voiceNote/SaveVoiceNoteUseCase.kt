package com.appprivategalery.myapplication.domain.usecase.voiceNote

import com.appprivategalery.myapplication.data.model.VoiceNote
import com.appprivategalery.myapplication.domain.repository.VoiceNotesRepository

class SaveVoiceNoteUseCase(
    private val voiceNotesRepository: VoiceNotesRepository
) {
    suspend fun execute(note: VoiceNote) = voiceNotesRepository.saveVoiceNote(note)
}