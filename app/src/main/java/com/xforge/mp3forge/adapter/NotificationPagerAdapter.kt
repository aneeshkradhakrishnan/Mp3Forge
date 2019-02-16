package com.xforge.mp3forge.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xforge.mp3forge.adapter.NotificationPagerAdapter.PageItem.GRID_VIEW
import com.xforge.mp3forge.adapter.NotificationPagerAdapter.PageItem.LIST_VIEW
import com.xforge.mp3forge.view.NotificationGridView
import com.xforge.mp3forge.view.NotificationListView
import com.xforge.mp3forge.vm.BaseNotificationPageViewModel
import com.xforge.mp3forge.vm.NotificationListViewModel

class NotificationPagerAdapter(private val viewModels: List<BaseNotificationPageViewModel>, private val fm: FragmentManager?) :
        FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (forPosition(position)) {
            LIST_VIEW -> NotificationListView(viewModels[position] as NotificationListViewModel)
            GRID_VIEW -> NotificationGridView()
        }
    }

    override fun getCount(): Int {
        return viewModels.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab $position"
    }

    private fun forPosition(position: Int): PageItem {
        return when (viewModels[position]) {
            is NotificationListViewModel -> LIST_VIEW
            else -> GRID_VIEW
        }
    }

    enum class PageItem {
        LIST_VIEW,
        GRID_VIEW
    }
}