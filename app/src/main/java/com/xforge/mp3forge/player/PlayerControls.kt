package com.xforge.mp3forge.player

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerControls @Inject constructor() {

    private var mediaPlayerService: MediaPlayerService? = null

    fun setMediaPlayerService(mediaPlayerService: MediaPlayerService) {
        this.mediaPlayerService = mediaPlayerService
    }

    fun play() {
        mediaPlayerService?.play()
    }

    fun pause() {
        mediaPlayerService?.pause()
    }

    fun forward(milliSeconds: Int) {
        mediaPlayerService?.forward(milliSeconds)
    }

    fun rewind(milliSeconds: Int) {
        mediaPlayerService?.rewind(milliSeconds)
    }

    fun next() {
        mediaPlayerService?.next()
    }

    fun previous() {
        mediaPlayerService?.previous()
    }

    fun startForground() {
        mediaPlayerService?.startPlayerForeground()
    }

    fun stopForground() {
        mediaPlayerService?.stopPlayerForeground()
    }

    fun stop() {
        mediaPlayerService?.stop()
    }
}