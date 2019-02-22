package com.xforge.mp3forge.vm

import android.graphics.Bitmap

data class SongViewModel(val title: String,
                         val artistName: String,
                         val album: String,
                         val duration: Long,
                         val albumArt: Bitmap?)