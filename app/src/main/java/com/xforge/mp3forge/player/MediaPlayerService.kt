package com.xforge.mp3forge.player

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.xforge.mp3forge.R
import com.xforge.mp3forge.activity.MainActivity
import com.xforge.mp3forge.vm.PlayListViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MediaPlayerService : Service() {
    companion object {
        const val NOTIFICATION_CHANNEL = "MP3_FORGE_CHANNEL"
        const val FOREGROUND_SERVICE = 101
    }

    @Inject
    lateinit var playListViewModel: PlayListViewModel

    @Inject
    lateinit var playerControls: PlayerControls

    private lateinit var mediaPlayerThread: MediaPlayerThread

    private lateinit var pendingIntent: PendingIntent
    private lateinit var notificationIntent: Intent
    private lateinit var previousIntent: PendingIntent
    private lateinit var playIntent: PendingIntent
    private lateinit var pauseIntent: PendingIntent
    private lateinit var nextIntent: PendingIntent
    private lateinit var nm: NotificationManager
    private var lastNotification: Notification? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        playerControls.setMediaPlayerService(this)
        playListViewModel.initPlayList(Environment.getExternalStorageDirectory().absolutePath + "/Download")

        notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.action = MediaPlayerAction.MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        previousIntent = createServiceAction(MediaPlayerAction.PREV_FOREGROUND)
        playIntent = createServiceAction(MediaPlayerAction.PLAY_FOREGROUND)
        nextIntent = createServiceAction(MediaPlayerAction.NEXT_FOREGROUND)
        pauseIntent = createServiceAction(MediaPlayerAction.PAUSE_FOREGROUND)
        nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel(NOTIFICATION_CHANNEL, "Mp3 Forge Service", NotificationManager.IMPORTANCE_DEFAULT));
        }

        mediaPlayerThread = MediaPlayerThread(applicationContext)
        mediaPlayerThread.start()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when {
            intent.action == MediaPlayerAction.PREV_FOREGROUND -> {
                previous()
                startServiceAndNotification(createNotification(pauseIntent, "Pause"))
            }
            intent.action == MediaPlayerAction.PLAY_FOREGROUND -> {
                play()
                startServiceAndNotification(createNotification(pauseIntent, "Pause"))
            }
            intent.action == MediaPlayerAction.PAUSE_FOREGROUND -> {
                this.pause()
                startServiceAndNotification(createNotification(playIntent, "Play"))
            }
            intent.action == MediaPlayerAction.NEXT_FOREGROUND -> {
                next()
                startServiceAndNotification(createNotification(pauseIntent, "Pause"))
            }
        }
        return Service.START_STICKY
    }

    fun startPlayerForeground() {
        startServiceAndNotification(createNotification(playIntent, "Play"))
    }

    fun stopPlayerForeground() {
        stopForeground(true)
        stopSelf()
    }

    fun play() {
        playListViewModel.currentPlayingSong().subscribe(mediaPlayerThread::play)
    }

    fun pause() {
        mediaPlayerThread.pause()
    }

    fun stop() {
        mediaPlayerThread.stopPlaying()
    }

    fun previous() {
        playListViewModel.previousSong().subscribe(mediaPlayerThread::next)
    }

    fun next() {
        playListViewModel.nextSong().subscribe(mediaPlayerThread::next)
    }

    fun forward(milliSecond: Int) {
        mediaPlayerThread.forward(milliSecond)
    }

    fun rewind(milliSecond: Int) {
        mediaPlayerThread.rewind(milliSecond)
    }

    private fun startServiceAndNotification(notification: Notification) {
        if (null == lastNotification) {
            lastNotification = notification
            startForeground(FOREGROUND_SERVICE, notification)
            return
        }

        lastNotification = notification
        nm.notify(FOREGROUND_SERVICE, notification)

    }

    private fun createServiceAction(action: String): PendingIntent {
        val intent = Intent(this, MediaPlayerService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, 0)
    }

    private fun createNotification(playIntent: PendingIntent, playButtonText: String): Notification {
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setContentTitle("Mp3 Forge Player")
                .setTicker("Mp3 Forge Player")
                .setContentText(playListViewModel.getCurrentSongTitle())
                .setSmallIcon(R.drawable.ic_logo_black)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, "Previous", previousIntent)
                .addAction(android.R.drawable.ic_media_play, playButtonText, playIntent)
                .addAction(android.R.drawable.ic_media_next, "Next", nextIntent)
                .build()
    }
}