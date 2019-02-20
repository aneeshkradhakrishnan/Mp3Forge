package com.xforge.mp3forge.dagger;

import com.xforge.mp3forge.activity.MainActivity;
import com.xforge.mp3forge.fragment.PlayListFragment;
import com.xforge.mp3forge.fragment.PlayerFragment;
import com.xforge.mp3forge.player.MediaPlayerService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidInjectionModule {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract PlayListFragment bindNotificationFragment();

    @ContributesAndroidInjector
    abstract PlayerFragment bindPlayerFragment();

    @ContributesAndroidInjector
    abstract MediaPlayerService bindMediaPlayerService();
}
