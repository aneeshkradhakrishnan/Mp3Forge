package com.xforge.mp3forge.adapter

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class BindingAdapterK {
    companion object {
        @JvmStatic
        @BindingAdapter("viewPager")
        fun TabLayout.bindViewPager(viewPager: ViewPager) {
            this.setupWithViewPager(viewPager)
        }
    }
}