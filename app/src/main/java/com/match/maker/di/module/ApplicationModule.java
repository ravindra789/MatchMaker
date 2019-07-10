package com.match.maker.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.match.maker.common.IntegerTypeAdapter;
import com.match.maker.db.MatchMakerDatabase;
import com.match.maker.di.DatabaseInfo;
import com.match.maker.preferences.CommonPreferences;
import com.match.maker.utils.AnimationUtil;
import com.match.maker.utils.PermissionUtils;
import com.match.maker.utils.Urls;
import com.match.maker.utils.Util;
import com.match.maker.utils.WSConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ravindra on 16,January,2019
 */
@Module
public class ApplicationModule {

    private final Context mContext;
    private final int TIME_OUT_DURATION = 60;


    public ApplicationModule(Application context) {
        mContext = context;
    }


    @Provides
    @Singleton
    Context providesContext() {
        return mContext;
    }


    @Provides
    @Singleton
    @Named(WSConstants.RETROFIT_WITHOUT_HEADERS)
    public Retrofit provideRetrofitWithoutHeaders(@Named(WSConstants.GSON_OF_GOOGLE) Gson gson) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    @Named(WSConstants.GSON_OF_RETROFIT)
    public Gson provideRetrofitGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    @Named(WSConstants.GSON_OF_GOOGLE)
    public Gson provideGoogleGson() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                .create();

        return gson;
    }

    @Provides
    @Singleton
    public MatchMakerDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, MatchMakerDatabase.class, dbName).fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @DatabaseInfo
    public String provideDatabaseName() {
        return WSConstants.Db.DATABASE_NAME;
    }


    @Provides
    @Singleton
    public CommonPreferences providesSharedPreferences(Context context) {
        CommonPreferences commonPreferences = CommonPreferences.getInstance();
        commonPreferences.load(context);
        return commonPreferences;
    }


    @Provides
    @Singleton
    public Util provideUtility(Context context) {
        Util util = Util.getInstance();
        util.setContext(context);
        return util;
    }

    @Provides
    @Singleton
    public AnimationUtil provideAnimation(Context context) {
        AnimationUtil animationUtil = AnimationUtil.getInstance();
        animationUtil.setContext(context);
        return animationUtil;
    }
    
     @Provides
    @Singleton
    public PermissionUtils providePermissionUtils() {
        PermissionUtils permissionUtils = PermissionUtils.getInstance();
        return permissionUtils;
    }

}
