package com.xforge.mp3forge.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xforge.mp3forge.R
import com.xforge.mp3forge.databinding.FragmentNotificationsBinding
import com.xforge.mp3forge.vm.NotificationViewModel

class PlayListFragment : Fragment() {

    private var fm: FragmentManager? = null

    private var viewModel: NotificationViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = NotificationViewModel(inflater.context, fm)
        val binding: FragmentNotificationsBinding = inflate(inflater, R.layout.fragment_notifications, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    fun setFm(fm: FragmentManager) {
        this.fm = fm
    }
}