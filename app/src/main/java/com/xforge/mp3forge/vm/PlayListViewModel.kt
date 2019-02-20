package com.xforge.mp3forge.vm

import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayListViewModel @Inject constructor() {

    private val playList: MutableList<String> = mutableListOf()

    fun initPlayList(dir: String) {
        if (playList.size > 0) {
            return
        }

        findSongs(dir)
    }

    fun currentPlayingSong(): String {
        return playList[0]
    }

    private fun findSongs(dir: String) {
        val fileList: Array<out File> = File(dir).listFiles() ?: return

        for (file in fileList) {
            if (file.isDirectory && file.canRead()) {
                return findSongs(file.absolutePath)
            }

            if (file.name.endsWith(".mp3", true)) {
                playList.add(file.absolutePath)
            }
        }
        return
    }
}