package com.appprivategalery.myapplication.domain.usecase.voiceNote

import com.appprivategalery.myapplication.data.model.VoiceNote
import com.appprivategalery.myapplication.domain.repository.VoiceNotesRepository

class DeleteVoiceNoteUseCase(private val voiceNotesRepository: VoiceNotesRepository) {
    suspend fun execute(voiceNote: VoiceNote) = voiceNotesRepository.deleteVoiceNote(voiceNote)
}