package com.xforge.mp3forge.player

interface MediaPlayerAction {
    companion object {
        const val MAIN = "com.xforge.mp3forge.player.main"
        const val PLAY = "com.xforge.mp3forge.player.play"
        const val STOP = "com.xforge.mp3forge.player.stop"
        const val PAUSE = "com.xforge.mp3forge.player.pause"
        const val NEXT = "com.xforge.mp3forge.player.next"
        const val PREV = "com.xforge.mp3forge.player.prev"
        const val FOWD = "com.xforge.mp3forge.player.fowd"
        const val REWD = "com.xforge.mp3forge.player.rewd"
        const val START_FOREGROUND = "com.xforge.mp3forge.player.foreground.start"
        const val STOP_FOREGROUND = "com.xforge.mp3forge.player.foreground.stop"
        const val PLAY_FOREGROUND = "com.xforge.mp3forge.player.foreground.play"
        const val PAUSE_FOREGROUND = "com.xforge.mp3forge.player.foreground.pause"
        const val NEXT_FOREGROUND = "com.xforge.mp3forge.player.foreground.next"
        const val PREV_FOREGROUND = "com.xforge.mp3forge.player.foreground.prev"
    }
}