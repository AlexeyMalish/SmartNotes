package com.appprivategalery.myapplication.utils

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import java.io.IOException
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class VoiceRecorder(private val context: Context) {
    var fileName: String = context.externalCacheDir!!.absolutePath + "/audiorecordtest.3gp"
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    var file: ParcelFileDescriptor? = null
    //var output: String? = null
    private var player: MediaPlayer? = null


    fun start() {
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder!!.setOutputFile(fileName)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            mediaRecorder!!.prepare()
        } catch (e: IOException) {
            Log.e("VoiceRecorder", "prepare() failed")
        }
        mediaRecorder!!.start()
    }

    private fun stop() {
        mediaRecorder!!.stop()
        mediaRecorder!!.release()
        mediaRecorder = null
    }

    private fun startPlaying() {
        player = MediaPlayer()
        try {
            player!!.setDataSource(fileName)
            player!!.prepare()
            player!!.start()
        } catch (e: IOException) {
            Log.e("VoceRecorder", "prepare() failed")
        }
    }

    private fun stopPlaying() {
        player!!.release()
        player = null
    }


    fun setUp() {
        fileName =
            context.externalCacheDir!!.absolutePath + "/audiorecordtest.3gp"
        // getCurrentDateTime().toString("MMddHHmmss") + ".mp3"
//        output = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//            .toString() + "/DareGrow/" + fileName

        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
       // mediaRecorder?.setOutputFile(output)

    }



    fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
        }
    }

    companion object {
        fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        fun getCurrentDateTime(): Date {
            return Calendar.getInstance().time
        }
    }
}