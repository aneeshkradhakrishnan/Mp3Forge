package com.xforge.mp3forge.vm

import android.media.MediaMetadataRetriever
import io.reactivex.Single
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

    fun currentPlayingSong(): Single<String> {
        return Single.just(playList[0])
    }

    fun fetchSongMetaData(path: String): Single<MediaMetadataRetriever> {
        val metaData = MediaMetadataRetriever()
        metaData.setDataSource(path)
        return Single.just(metaData)
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