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

    fun play(path: String) {
        playerHandler.post {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(path)
                mediaPlayer?.prepare()
                mediaPlayer?.start()
            } else if (!mediaPlayer!!.isPlaying) {
                mediaPlayer?.start()
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

    fun next(path: String) {
        playerHandler.post {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
            }

            if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.stop()
            }

            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(path)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }
    }

    fun forward(milliSecond: Int) {
        playerHandler.post {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.plus(milliSecond))
            mediaPlayer?.start()
        }
    }

    fun rewind(milliSecond: Int) {
        playerHandler.post {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.minus(milliSecond))
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