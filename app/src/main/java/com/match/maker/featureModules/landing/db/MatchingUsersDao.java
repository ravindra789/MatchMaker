package com.match.maker.featureModules.landing.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.match.maker.db.tables.MatchingUsersTable;

/**
 * Created by ravindra on 17,January,2019
 */
@Dao
public interface MatchingUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MatchingUsersTable... matchingUsersTables);

}
