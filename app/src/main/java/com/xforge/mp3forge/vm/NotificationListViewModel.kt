package com.xforge.mp3forge.vm

import android.content.Context
import androidx.databinding.ObservableField
import com.xforge.mp3forge.adapter.NotificationListAdapter

class NotificationListViewModel(context: Context?) : BaseNotificationPageViewModel() {

    val adapter: NotificationListAdapter = NotificationListAdapter(context, arrayOf(
            NotificationItemViewModel(ObservableField("1"), ObservableField("Black or White"), ObservableField("Album - Dangerous")),
            NotificationItemViewModel(ObservableField("2"), ObservableField("Dirty Dina"), ObservableField("Album - Bad")),
            NotificationItemViewModel(ObservableField("3"), ObservableField("Jam"), ObservableField("Album - Dangerous")),
            NotificationItemViewModel(ObservableField("4"), ObservableField("Dangerous"), ObservableField("Album - Dangerous")),
            NotificationItemViewModel(ObservableField("5"), ObservableField("The way you make me feel"), ObservableField("Album - Bad")),
            NotificationItemViewModel(ObservableField("6"), ObservableField("Beat it"), ObservableField("Album - Thriller")),
            NotificationItemViewModel(ObservableField("7"), ObservableField("Thriller"), ObservableField("Album - Thriller")),
            NotificationItemViewModel(ObservableField("8"), ObservableField("Billie Jean"), ObservableField("Album - Thriller")),
            NotificationItemViewModel(ObservableField("9"), ObservableField("Leave Me Alone"), ObservableField("Album - Bad")),
            NotificationItemViewModel(ObservableField("10"), ObservableField("PYT"), ObservableField("Album - Thriller")),
            NotificationItemViewModel(ObservableField("11"), ObservableField("Remember the Time"), ObservableField("Album - Dangerous")),
            NotificationItemViewModel(ObservableField("12"), ObservableField("Another Part of Me"), ObservableField("Album - Bad")),
            NotificationItemViewModel(ObservableField("13"), ObservableField("Who is it"), ObservableField("Album - Dangerous")),
            NotificationItemViewModel(ObservableField("14"), ObservableField("In the Closet"), ObservableField("Album - Dangerous")),
            NotificationItemViewModel(ObservableField("15"), ObservableField("Smooth Criminal"), ObservableField("Album - Bad")),
            NotificationItemViewModel(ObservableField("16"), ObservableField("Wanna be Starin Somethin"), ObservableField("Album - Thriller")),
            NotificationItemViewModel(ObservableField("17"), ObservableField("Girl is Mine"), ObservableField("Album - Thriller")),
            NotificationItemViewModel(ObservableField("18"), ObservableField("Human Nature"), ObservableField("Album - Thriller")),
            NotificationItemViewModel(ObservableField("19"), ObservableField("Speed Demon"), ObservableField("Album - Bad")),
            NotificationItemViewModel(ObservableField("20"), ObservableField("Liberian Girl"), ObservableField("Album - Bad"))
            ))

}