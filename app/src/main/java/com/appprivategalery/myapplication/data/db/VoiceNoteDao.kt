package com.appprivategalery.myapplication.data.db

import androidx.room.*
import com.appprivategalery.myapplication.data.model.VoiceNote
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceNoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voiceNote:VoiceNote)

    @Query("SELECT * FROM voice_notes")
    fun getAllVoiceNotes(): Flow<List<VoiceNote>>

    @Delete
    suspend fun deleteVoiceNote(voiceNote: VoiceNote)
}