package com.appprivategalery.myapplication.data.repository

import com.appprivategalery.myapplication.data.model.VoiceNote
import com.appprivategalery.myapplication.data.repository.dataSource.VoiceNotesLocalDataSource
import com.appprivategalery.myapplication.domain.repository.VoiceNotesRepository
import kotlinx.coroutines.flow.Flow

class VoiceNotesRepositoryImpl(private val voiceNotesLocalDataSource: VoiceNotesLocalDataSource) : VoiceNotesRepository{
    override suspend fun saveVoiceNote(voiceNote: VoiceNote) {
        voiceNotesLocalDataSource.saveVoiceNoteToDB(voiceNote)
    }

    override fun savedVoiceNotes(): Flow<List<VoiceNote>> {
        return voiceNotesLocalDataSource.savedVoiceNotes()
    }

    override suspend fun deleteVoiceNote(voiceNote: VoiceNote) {
        voiceNotesLocalDataSource.deleteVoiceNoteFromDB(voiceNote)
    }
}