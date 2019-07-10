package com.match.maker.di.component;

import android.content.Context;

import com.match.maker.db.MatchMakerDatabase;
import com.match.maker.di.module.ApplicationModule;
import com.match.maker.preferences.CommonPreferences;
import com.match.maker.utils.AnimationUtil;
import com.match.maker.utils.Util;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by ravindra on 16,January,2019
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    @Named("WithoutHeaders")
    Retrofit provideRetrofitWithoutHeaders();

    Context providesContext();

    CommonPreferences providesSharedPreferences();

    Util provideUtility();

    AnimationUtil provideAnimation();
    
    PermissionUtils providePermissionUtils();

    MatchMakerDatabase provideAppDatabase();


}
