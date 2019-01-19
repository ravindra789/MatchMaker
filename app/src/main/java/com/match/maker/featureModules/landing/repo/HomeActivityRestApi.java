package com.match.maker.featureModules.landing.repo;

import com.match.maker.featureModules.landing.models.MatchingUsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ravindra on 17,January,2019
 */
public interface HomeActivityRestApi {

    @GET("/api/?results=50")
    Call<MatchingUsersResponse> getAllMatches();
}
