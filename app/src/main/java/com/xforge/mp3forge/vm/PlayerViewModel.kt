package com.xforge.mp3forge.vm

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.xforge.mp3forge.R
import com.xforge.mp3forge.player.MediaPlayerAction
import com.xforge.mp3forge.player.MediaPlayerService
import javax.inject.Inject


class PlayerViewModel @Inject constructor(private val playListViewModel: PlayListViewModel) {

    private var context: Context? = null

    val songTitle: ObservableField<String> = ObservableField()
    val songAlbumArt: ObservableField<Bitmap> = ObservableField()
    val isPlaying: ObservableBoolean = ObservableBoolean(false)

    init {
        playListViewModel.songChangeNotified()
                .flatMap { path: String -> playListViewModel.fetchSongMetaData(path) }
                .subscribe { metaData -> this.setSongMetaData(metaData) }
    }

    fun play() {
        if(!isPlaying.get()) {
            mediaPlyerAction(MediaPlayerAction.PLAY)
            isPlaying.set(true)
        } else {
            mediaPlyerAction(MediaPlayerAction.PAUSE)
            isPlaying.set(false)
        }

    }

    fun forward() {
        mediaPlyerAction(MediaPlayerAction.FOWD)
    }

    fun rewind() {
        mediaPlyerAction(MediaPlayerAction.REWD)
    }

    fun next() {
        mediaPlyerAction(MediaPlayerAction.NEXT)
    }

    fun prev() {
        mediaPlyerAction(MediaPlayerAction.PREV)
    }

    fun setContext(context: Context?) {
        this.context = context
        songAlbumArt.set(getDefaultAlbumArt())
    }

    private fun setSongMetaData(metaData: MediaMetadataRetriever) {
        songTitle.set(metaData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE))

        val data = metaData.embeddedPicture
        if (data != null) {
            songAlbumArt.set(BitmapFactory.decodeByteArray(data, 0, data!!.size))
        } else {
            songAlbumArt.set(getDefaultAlbumArt())
        }
    }

    private fun getDefaultAlbumArt(): Bitmap {
        return BitmapFactory.decodeResource(context?.resources, R.drawable.vinyl_logo)
    }

    private fun mediaPlyerAction(action: String) {
        val intent = Intent(context, MediaPlayerService::class.java)
        intent.action = action
        context?.startService(intent)
    }
}