package com.example.workout_app.service

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class AudioPlayerService {

    private var mediaPlayer: MediaPlayer? = null

    companion object {
        val uri = "android.resource://"
        val projectName = "com.example.workout_app"
    }

    fun parseSound(applicationContext: Context, fileName: Int) {
        try {
            val playableSound: Uri? = Uri.parse("$uri$projectName/$fileName")
            setupSound(playableSound, applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupSound(uri: Uri?, applicationContext: Context) {
        mediaPlayer = MediaPlayer.create(applicationContext, uri)
        mediaPlayer?.isLooping = false
    }

    fun playSound() {
        mediaPlayer?.start()
    }

    fun dispose() {
        mediaPlayer?.let {
            it.stop()
            mediaPlayer = null
        }
    }
}