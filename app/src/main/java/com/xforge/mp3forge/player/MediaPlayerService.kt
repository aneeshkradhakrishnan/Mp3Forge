package com.xforge.mp3forge.player

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.xforge.mp3forge.R
import com.xforge.mp3forge.activity.MainActivity


class MediaPlayerService : Service() {
    companion object {
        const val NOTIFICATION_CHANNEL = "MP3_FORGE_CHANNEL"
        const val FOREGROUND_SERVICE = 101
        var songIncrement: Int = 0
    }

    private lateinit var bitmap: Bitmap
    private lateinit var pendingIntent: PendingIntent
    private lateinit var notificationIntent: Intent
    private lateinit var previousIntent: PendingIntent
    private lateinit var playIntent: PendingIntent
    private lateinit var nextIntent: PendingIntent

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.action = MediaPlayerAction.MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        previousIntent = createServiceAction(MediaPlayerAction.PREV)
        playIntent = createServiceAction(MediaPlayerAction.PLAY)
        nextIntent = createServiceAction(MediaPlayerAction.NEXT)
        bitmap = (ContextCompat.getDrawable(this, R.drawable.ic_logo) as BitmapDrawable).bitmap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var nm: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(NotificationChannel(NOTIFICATION_CHANNEL, "Mp3 Forge Service", NotificationManager.IMPORTANCE_DEFAULT));
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when {
            intent.action == MediaPlayerAction.START_FOREGROUND -> {
                startForeground(FOREGROUND_SERVICE, createNotification("Song Title $songIncrement", "Play"))
            }
            intent.action == MediaPlayerAction.PREV -> {
                startForeground(FOREGROUND_SERVICE, createNotification("Song Title ${--songIncrement}", "Pause"))
            }
            intent.action == MediaPlayerAction.PLAY -> {
                startForeground(FOREGROUND_SERVICE, createNotification("Song Title $songIncrement", "Pause"))
            }
            intent.action == MediaPlayerAction.NEXT -> {
                startForeground(FOREGROUND_SERVICE, createNotification("Song Title ${++songIncrement}", "Pause"))
            }
            intent.action == MediaPlayerAction.STOP_FOREGROUND -> {
                stopForeground(true)
                stopSelf()
            }
        }
        return Service.START_STICKY
    }

    private fun createServiceAction(action: String): PendingIntent {
        val intent = Intent(this, MediaPlayerService::class.java)
        intent.action = action
        return PendingIntent.getService(this, 0, intent, 0)
    }

    private fun createNotification(contentText: String, playButtonText: String): Notification {
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setContentTitle("Mp3 Forge Player")
                .setTicker("Mp3 Forge Player")
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_logo)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, "Previous", previousIntent)
                .addAction(android.R.drawable.ic_media_play, playButtonText, playIntent)
                .addAction(android.R.drawable.ic_media_next, "Next", nextIntent).build()
    }
}