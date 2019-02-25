package com.xforge.mp3forge.vm

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.SystemClock
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.xforge.mp3forge.R
import com.xforge.mp3forge.player.PlayerControls
import javax.inject.Inject


class PlayerViewModel @Inject constructor(private val playListViewModel: PlayListViewModel, private val playerControls: PlayerControls) {

    private var context: Context? = null
    private var audioManager: AudioManager? = null
    private var mediaSession: MediaSession? = null
    private var defaultAlbumArt: Bitmap? = null

    val songTitle: ObservableField<String> = ObservableField()
    val songAlbumArt: ObservableField<Bitmap> = ObservableField()
    val isPlaying: ObservableBoolean = ObservableBoolean(false)

    init {
        playListViewModel.songChangeNotified()
                .flatMap { path: String -> playListViewModel.getSongInformation(path) }
                .subscribe { metaData -> this.setSongInformation(metaData) }
    }

    fun play() {
        if (!isPlaying.get()) {
            playerControls.play()
            isPlaying.set(true)
        } else {
            playerControls.pause()
            isPlaying.set(false)
        }

    }

    fun forward() {
        playerControls.forward(10000)
    }

    fun rewind() {
        playerControls.rewind(10000)
    }

    fun next() {
        playerControls.next()
    }

    fun prev() {
        playerControls.previous()
    }

    fun setContext(context: Context?) {
        this.context = context
        defaultAlbumArt = getDefaultAlbumArt()
        songAlbumArt.set(getDefaultAlbumArt())
        initAudioManager()
    }

    private fun setSongInformation(songViewModel: SongViewModel) {
        setAppMediaInformation(songViewModel)
        setBlueToothMediaInformation(songViewModel)
    }

    private fun initAudioManager() {
        if (audioManager == null) {
            audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        }

        if (mediaSession == null) {
            mediaSession = MediaSession(context, "Mp3ForgeMediaSession")
            mediaSession?.isActive = true

        }
    }

    private fun setAppMediaInformation(songViewModel: SongViewModel) {
        songTitle.set(songViewModel.title)
        songAlbumArt.set(getAlbumArt(songViewModel))
    }

    private fun setBlueToothMediaInformation(songViewModel: SongViewModel) {

        val metadata = MediaMetadata.Builder()
                .putString(MediaMetadata.METADATA_KEY_TITLE, songViewModel.title)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, songViewModel.artistName)
                .putString(MediaMetadata.METADATA_KEY_ALBUM, songViewModel.album)
                .putLong(MediaMetadata.METADATA_KEY_DURATION, songViewModel.duration)
                .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, getAlbumArt(songViewModel))
                .putBitmap(MediaMetadata.METADATA_KEY_ART, getAlbumArt(songViewModel))
                .putBitmap(MediaMetadata.METADATA_KEY_DISPLAY_ICON, getAlbumArt(songViewModel))
                .build()

        mediaSession?.setMetadata(metadata)

        val state = PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY)
                .setState(PlaybackState.STATE_PLAYING, 1, 1.0f, SystemClock.elapsedRealtime())
                .build()

        mediaSession?.setPlaybackState(state)
    }

    private fun getAlbumArt(songViewModel: SongViewModel): Bitmap? {
        if (songViewModel.albumArt == null) {
            return defaultAlbumArt
        }

        return songViewModel.albumArt
    }


    private fun getDefaultAlbumArt(): Bitmap {
        return BitmapFactory.decodeResource(context?.resources, R.drawable.vinyl_logo)
    }
}