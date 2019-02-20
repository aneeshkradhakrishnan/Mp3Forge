package com.xforge.mp3forge.player

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.HandlerThread


class MediaPlayerThread(val context: Context) :
        HandlerThread("mediaPlayerThread") {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var playerHandler: Handler

    override fun onLooperPrepared() {
        playerHandler = Handler()
    }

    fun initPlayer() {
        playerHandler.post {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }
        }
    }

    fun play(path: String) {
        playerHandler.post {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }
            if (!mediaPlayer!!.isPlaying) {
                next(path)
            }
        }
    }

    fun pause() {
        playerHandler.post {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
            }
        }
    }

    fun forward(milliSecond: Int) {
        playerHandler.post {
            mediaPlayer?.seekTo(milliSecond)
        }
    }

    fun rewind(milliSecond: Int) {
        playerHandler.post {
            mediaPlayer?.seekTo(milliSecond)
        }
    }

    fun next(path: String) {
        playerHandler.post {
            mediaPlayer?.setDataSource(path)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }
    }

    fun stopPlaying() {
        playerHandler.post {
            mediaPlayer?.release()
            mediaPlayer = null
            playerHandler.looper.quit()
        }
    }
}