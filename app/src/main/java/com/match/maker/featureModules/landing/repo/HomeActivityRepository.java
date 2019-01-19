package com.match.maker.featureModules.landing.repo;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.match.maker.featureModules.landing.models.MatchingUsersResponse;
import com.match.maker.preferences.CommonPreferences;
import com.match.maker.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravindra on 17,January,2019
 */
public class HomeActivityRepository {

    private static HomeActivityRepository instance;


    private HomeActivityRestApi mRestApi;
    private Util mUtil;
    private Context mContext;
    private CommonPreferences mPrefs;


    public static HomeActivityRepository getInstance() {
        if (instance == null) {
            synchronized (HomeActivityRepository.class) {
                if (instance == null) {
                    instance = new HomeActivityRepository();
                }
            }
        }

        return instance;
    }

    public void setVariables(HomeActivityRestApi restApi, Util util, Context context, CommonPreferences prefs) {
        mRestApi = restApi;
        mUtil = util;
        mContext = context;
        mPrefs = prefs;
        //this.db = db;
    }

    public void getAllMatches(int count, final MutableLiveData<MatchingUsersResponse> allMatchesData, final MutableLiveData<Throwable> allMatchesDataError){

        mRestApi.getAllMatches().enqueue(new Callback<MatchingUsersResponse>() {
            @Override
            public void onResponse(Call<MatchingUsersResponse> call, Response<MatchingUsersResponse> response) {

                if(response.isSuccessful()){
                    allMatchesData.setValue(response.body());
                }else {
                    allMatchesDataError.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<MatchingUsersResponse> call, Throwable t) {
                allMatchesDataError.setValue(t);
            }
        });
    }

}
