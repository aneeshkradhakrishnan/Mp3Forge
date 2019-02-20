package com.xforge.mp3forge.dagger;

import com.xforge.mp3forge.application.Mp3ForgeApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        AndroidInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<Mp3ForgeApplication> {
}
