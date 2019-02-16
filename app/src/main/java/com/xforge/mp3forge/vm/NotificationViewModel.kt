package com.xforge.mp3forge.vm

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.xforge.mp3forge.adapter.NotificationPagerAdapter
import javax.inject.Inject

class NotificationViewModel @Inject constructor(context: Context?, fm: FragmentManager?) {
    private val pageItems: MutableList<BaseNotificationPageViewModel> = mutableListOf(NotificationListViewModel(context), NotificationGridViewModel())
    val pagerAdapter: PagerAdapter = NotificationPagerAdapter(pageItems, fm)
}