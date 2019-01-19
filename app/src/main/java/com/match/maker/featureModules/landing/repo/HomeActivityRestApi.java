package com.match.maker.featureModules.landing.repo;

import com.match.maker.db.tables.MatchingUsersTable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ravindra on 17,January,2019
 */
public interface HomeActivityRestApi {

    @GET("/api/")
    Call<MatchingUsersTable> getAllMatchesDynamic(@Query("results") String count);


}
