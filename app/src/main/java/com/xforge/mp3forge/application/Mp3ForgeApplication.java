package com.xforge.mp3forge.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Intent;

import com.xforge.mp3forge.activity.MainActivity;
import com.xforge.mp3forge.dagger.ApplicationComponent;
import com.xforge.mp3forge.dagger.ApplicationModule;
import com.xforge.mp3forge.dagger.DaggerApplicationComponent;
import com.xforge.mp3forge.player.MediaPlayerAction;
import com.xforge.mp3forge.player.MediaPlayerService;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class Mp3ForgeApplication extends Application
        implements HasActivityInjector, HasSupportFragmentInjector, HasServiceInjector {

    private static ApplicationComponent applicationComponent;

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Inject
    DispatchingAndroidInjector<Service> serviceInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        this.applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceInjector;
    }
}
