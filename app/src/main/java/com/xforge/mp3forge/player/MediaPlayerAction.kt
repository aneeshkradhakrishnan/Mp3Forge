package com.xforge.mp3forge.player

interface MediaPlayerAction {
    companion object {
        const val MAIN = "com.xforge.mp3forge.player.mediaplayeraction.main"
        const val PREV = "com.xforge.mp3forge.player.mediaplayeraction.prev"
        const val PLAY = "com.xforge.mp3forge.player.mediaplayeraction.play"
        const val NEXT = "com.xforge.mp3forge.player.mediaplayeraction.next"
        const val START_FOREGROUND = "com.xforge.mp3forge.player.mediaplayeraction.startforeground"
        const val STOP_FOREGROUND = "com.xforge.mp3forge.player.mediaplayeraction.stopforeground"
    }
}