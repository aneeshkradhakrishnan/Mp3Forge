package com.xforge.mp3forge.fragment

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import com.xforge.mp3forge.R
import com.xforge.mp3forge.databinding.FragmentPlayerBinding
import com.xforge.mp3forge.vm.PlayListViewModel
import com.xforge.mp3forge.vm.PlayerViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PlayerFragment : Fragment() {
    private lateinit var viewModel:PlayerViewModel
    private lateinit var binding:FragmentPlayerBinding

    @Inject
    lateinit var playListViewModel: PlayListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        playListViewModel.initPlayList(Environment.getExternalStorageDirectory().absolutePath + "/Download")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflate(inflater, R.layout.fragment_player, container, false)
        viewModel = PlayerViewModel(context)
        binding.viewModel = viewModel
        return binding.root
    }
}