package com.xforge.mp3forge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.xforge.mp3forge.R
import com.xforge.mp3forge.databinding.ViewNotificationGridBinding

class NotificationGridView : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ViewNotificationGridBinding = DataBindingUtil.inflate(inflater, R.layout.view_notification_grid, container, false)
        return binding.root
    }
}