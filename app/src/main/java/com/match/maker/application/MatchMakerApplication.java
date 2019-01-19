package com.match.maker.application;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.match.maker.di.component.ApplicationComponent;
import com.match.maker.di.component.DaggerApplicationComponent;
import com.match.maker.di.module.ApplicationModule;

/**
 * Created by ravindra on 16,January,2019
 */
public class MatchMakerApplication extends MultiDexApplication {

    private static MatchMakerApplication applicationContext;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ApplicationComponent mApplicationComponent;

    public static MatchMakerApplication app() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}