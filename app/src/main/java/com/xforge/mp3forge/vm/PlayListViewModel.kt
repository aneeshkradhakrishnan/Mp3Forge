package com.xforge.mp3forge.vm

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayListViewModel @Inject constructor() {

    private val playList: MutableList<String> = mutableListOf()
    private val songChanged: PublishSubject<String> = PublishSubject.create()
    private var currentIndex = 0;

    fun initPlayList(dir: String) {
        if (playList.size > 0) {
            return
        }

        findSongs(dir)
    }

    fun currentPlayingSong(): Maybe<String> {
        return getSong(currentIndex)
    }

    fun nextSong(): Maybe<String> {
        if (currentIndex >= playList.size - 1) {
            return Maybe.empty()
        }
        currentIndex = currentIndex.plus(1)
        return getSong(currentIndex)
    }

    fun previousSong(): Maybe<String> {
        if (currentIndex <= 0) {
            return Maybe.empty()
        }
        currentIndex = currentIndex.minus(1)
        return getSong(currentIndex)
    }


    fun getSongInformation(path: String): Observable<SongViewModel> {
        val mediaMetadataRetriever: MediaMetadataRetriever = getMetaData(path)

        var albumArt: Bitmap? = null
        val data = mediaMetadataRetriever.embeddedPicture
        if (data != null) {
            albumArt = BitmapFactory.decodeByteArray(data, 0, data!!.size)
        }

        val songViewModel = SongViewModel(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
                mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong(),
                albumArt
        )
        return Observable.just(songViewModel)
    }

    fun getCurrentSongTitle(): String {
        return getMetaData(playList[currentIndex]).extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
    }

    fun songChangeNotified(): PublishSubject<String> {
        return songChanged
    }

    private fun getMetaData(path: String): MediaMetadataRetriever {
        val metaData = MediaMetadataRetriever()
        metaData.setDataSource(path)
        return metaData
    }

    private fun getSong(index: Int): Maybe<String> {
        songChanged.onNext(playList[index])
        return Maybe.just(playList[index])
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