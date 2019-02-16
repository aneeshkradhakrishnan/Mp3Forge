package com.xforge.mp3forge.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import com.xforge.mp3forge.R
import com.xforge.mp3forge.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentHomeBinding = inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
}