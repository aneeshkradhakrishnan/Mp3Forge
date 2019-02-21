package com.xforge.mp3forge.vm

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.databinding.ObservableField
import com.xforge.mp3forge.R
import com.xforge.mp3forge.player.MediaPlayerAction
import com.xforge.mp3forge.player.MediaPlayerService
import javax.inject.Inject


class PlayerViewModel @Inject constructor(private val playListViewModel: PlayListViewModel) {

    private var context: Context? = null

    val songTitle: ObservableField<String> = ObservableField()
    val songAlbumArt: ObservableField<Bitmap> = ObservableField()

    fun play() {
        val startIntent = Intent(context, MediaPlayerService::class.java)
        startIntent.action = MediaPlayerAction.PLAY
        context?.startService(startIntent)
        playListViewModel.currentPlayingSong()
                .flatMap { path: String -> playListViewModel.fetchSongMetaData(path) }
                .subscribe { metaData -> this.setSongMetaData(metaData) }
    }

    fun forward() {

    }

    fun rewind() {

    }

    fun next() {
        val pauseIntent = Intent(context, MediaPlayerService::class.java)
        pauseIntent.action = MediaPlayerAction.PAUSE
        context?.startService(pauseIntent)
    }

    fun prev() {

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
}