package com.xforge.mp3forge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.xforge.mp3forge.vm.NotificationItemViewModel
import com.xforge.mp3forge.databinding.ViewNotificationListItemBinding

class NotificationListAdapter(context:Context?, private val values: Array<NotificationItemViewModel>)
    : ArrayAdapter<NotificationItemViewModel>(context, -1, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater:LayoutInflater  = LayoutInflater.from(parent.context)
        val binding:ViewNotificationListItemBinding = ViewNotificationListItemBinding.inflate(inflater, parent, false)
        binding.viewModel = values[position]
        return binding.root
    }
}