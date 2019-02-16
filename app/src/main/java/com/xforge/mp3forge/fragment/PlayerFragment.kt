package com.xforge.mp3forge.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import com.xforge.mp3forge.R
import com.xforge.mp3forge.databinding.FragmentDashboardBinding

class PlayerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentDashboardBinding = inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }
}