package com.appprivategalery.myapplication.domain.repository

import com.appprivategalery.myapplication.data.model.VoiceNote
import kotlinx.coroutines.flow.Flow

interface VoiceNotesRepository {
    suspend fun saveVoiceNote(voiceNote:VoiceNote)
    fun savedVoiceNotes() : Flow<List<VoiceNote>>
    suspend fun deleteVoiceNote(voiceNote: VoiceNote)
}