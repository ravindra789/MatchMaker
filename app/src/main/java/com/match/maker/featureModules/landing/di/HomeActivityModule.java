package com.match.maker.featureModules.landing.di;

import android.content.Context;

import com.match.maker.di.scopes.UserScope;
import com.match.maker.featureModules.landing.repo.HomeActivityRepository;
import com.match.maker.featureModules.landing.repo.HomeActivityRestApi;
import com.match.maker.preferences.CommonPreferences;
import com.match.maker.utils.Util;
import com.match.maker.utils.WSConstants;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ravindra on 17,January,2019
 */
@Module
public class HomeActivityModule {

    @Provides
    @UserScope
    public HomeActivityRestApi providesHomeActivityRestApi(@Named(WSConstants.RETROFIT_WITHOUT_HEADERS) Retrofit retrofit) {
        return retrofit.create(HomeActivityRestApi.class);
    }


    @Provides
    @UserScope
    HomeActivityRepository provideHomeActivityRepository(HomeActivityRestApi homeActivityRestApi, Util util , Context context, CommonPreferences prefs){

        HomeActivityRepository homeActivityRepository = HomeActivityRepository.getInstance();
        homeActivityRepository.setVariables(homeActivityRestApi, util, context, prefs);

        return homeActivityRepository;

    }


}
