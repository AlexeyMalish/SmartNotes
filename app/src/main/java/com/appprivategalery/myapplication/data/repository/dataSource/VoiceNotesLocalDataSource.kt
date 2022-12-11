package com.appprivategalery.myapplication.data.repository.dataSource

import com.appprivategalery.myapplication.data.model.VoiceNote
import kotlinx.coroutines.flow.Flow

interface VoiceNotesLocalDataSource {
    suspend fun saveVoiceNoteToDB(voiceNote: VoiceNote)
    fun savedVoiceNotes() : Flow<List<VoiceNote>>
    suspend fun deleteVoiceNoteFromDB(voiceNote: VoiceNote)
}