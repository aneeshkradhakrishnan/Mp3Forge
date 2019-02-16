package com.xforge.mp3forge.vm

import android.util.Log
import javax.inject.Inject

class NotificationGridViewModel @Inject constructor(): BaseNotificationPageViewModel() {

    fun sayHai() {
        Log.e("Test Log", "Say Hai")
    }
}