package com.match.maker.featureModules.landing.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.match.maker.db.tables.MatchingUsersTable;
import com.match.maker.featureModules.landing.repo.HomeActivityRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ravindra on 17,January,2019
 */
public class HomeActivityViewModel extends AndroidViewModel {


    @Inject
    HomeActivityRepository repository;


    // get matches result
    private MutableLiveData<MatchingUsersTable> allMatchesData = new MutableLiveData<>();
    private MutableLiveData<Throwable> allMatchesDataError = new MutableLiveData<>();

    //
    private MutableLiveData<List<MatchingUsersTable>> matchingUsersTableLiveData = new MutableLiveData<>();

    //
    private MutableLiveData<Integer> dbCount = new MutableLiveData<>();


    public HomeActivityViewModel(@NonNull Application application) {
        super(application);
    }

    // get matches result
    public void getAllMatches(int count) {
        repository.getAllMatches(count, allMatchesData, allMatchesDataError);
    }

    public MutableLiveData<MatchingUsersTable> getAllMatchesData() {
        return allMatchesData;
    }

    public MutableLiveData<Throwable> getAllMatchesDataError() {
        return allMatchesDataError;
    }

    //
    public void  getDataFromDb() {
        repository.getMatchingDataFromDb(matchingUsersTableLiveData);
    }
    public LiveData<List<MatchingUsersTable>> getMatchingUsersTableLiveData() {
        return matchingUsersTableLiveData;
    }

    //
    public void getDbDataCount() {
        repository.getMatchingDataDbCount(dbCount);
    }
    public MutableLiveData<Integer> getDbCount() {
        return dbCount;
    }
}
