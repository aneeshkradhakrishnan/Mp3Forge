package com.xforge.mp3forge.player

interface MediaPlayerAction {
    companion object {
        const val MAIN = "com.xforge.mp3forge.player.main"
        const val START_SERVICE = "com.xforge.mp3forge.player.service.start"
        const val PLAY_FOREGROUND = "com.xforge.mp3forge.player.foreground.play"
        const val PAUSE_FOREGROUND = "com.xforge.mp3forge.player.foreground.pause"
        const val NEXT_FOREGROUND = "com.xforge.mp3forge.player.foreground.next"
        const val PREV_FOREGROUND = "com.xforge.mp3forge.player.foreground.prev"
    }
}