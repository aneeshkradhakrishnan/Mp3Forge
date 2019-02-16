package com.xforge.mp3forge.dagger;

import com.xforge.mp3forge.activity.MainActivity;
import com.xforge.mp3forge.fragment.PlayListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ViewModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract PlayListFragment bindNotificationFragment();
}
