package com.match.maker.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.match.maker.db.tables.MatchingUsersTable;
import com.match.maker.featureModules.landing.db.MatchingUsersDao;
import com.match.maker.utils.WSConstants;

/**
 * Created by ravindra on 17,January,2019
 */
@Database(entities = {MatchingUsersTable.class}, version = WSConstants.Db.CURRENT_DB_VERSION, exportSchema = true)
public abstract class MatchMakerDatabase extends RoomDatabase {

    private static MatchMakerDatabase INSTANCE;

    public abstract MatchingUsersDao matchingUsersDao();

    public static MatchMakerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MatchMakerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MatchMakerDatabase.class, WSConstants.Db.DATABASE_NAME)
                            //.addMigrations(MIGRATION_1_2)
                            .fallbackToDestructiveMigration()  // Delete db
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            database.getVersion();
        }
    };


}
