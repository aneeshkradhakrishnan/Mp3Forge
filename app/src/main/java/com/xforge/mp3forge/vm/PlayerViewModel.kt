package com.xforge.mp3forge.vm

import android.content.Context
import android.content.Intent
import com.xforge.mp3forge.player.MediaPlayerAction
import com.xforge.mp3forge.player.MediaPlayerService

class PlayerViewModel(val context: Context?) {

    fun play() {
        val startIntent = Intent(context, MediaPlayerService::class.java)
        startIntent.action = MediaPlayerAction.PLAY
        context?.startService(startIntent)
    }

    fun forward() {

    }

    fun rewind() {

    }

    fun next() {
        val pauseIntent = Intent(context, MediaPlayerService::class.java)
        pauseIntent.action = MediaPlayerAction.PAUSE
        context?.startService(pauseIntent)
    }

    fun prev() {

    }
}