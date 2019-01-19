package com.match.maker.db.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by ravindra on 17,January,2019
 */
@Entity(tableName = "matching_user_table")
public class MatchingUsersTable {


    public MatchingUsersTable(long primaryMobileNumber) {
        this.primaryMobileNumber = primaryMobileNumber;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mobile_number")
    long primaryMobileNumber ;


    public long getPrimaryMobileNumber() {
        return primaryMobileNumber;
    }
}
