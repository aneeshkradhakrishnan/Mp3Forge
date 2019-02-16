package com.xforge.mp3forge.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.xforge.mp3forge.R
import com.xforge.mp3forge.databinding.ViewNotificationListBinding
import com.xforge.mp3forge.vm.NotificationListViewModel

class NotificationListView() : Fragment() {

    private var viewModel: NotificationListViewModel? = null

    @SuppressLint("ValidFragment")
    constructor(viewModel: NotificationListViewModel):this() {
        this.viewModel =  viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ViewNotificationListBinding = DataBindingUtil.inflate(inflater, R.layout.view_notification_list, container, false)
        binding.viewModel = viewModel
        return binding.root
    }
}