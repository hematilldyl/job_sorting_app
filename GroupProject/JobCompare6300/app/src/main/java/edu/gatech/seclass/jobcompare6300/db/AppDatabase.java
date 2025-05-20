package edu.gatech.seclass.jobcompare6300.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.gatech.seclass.jobcompare6300.Dao.JobOfferDao;
import edu.gatech.seclass.jobcompare6300.Model.JobOffer;
import edu.gatech.seclass.jobcompare6300.Dao.ComparisonSettingDao;
import edu.gatech.seclass.jobcompare6300.Model.ComparisonSetting;

@Database(entities = {JobOffer.class, ComparisonSetting.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract JobOfferDao jobOfferDao();
    public abstract ComparisonSettingDao comparisonSettingDao();
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "jobCompare_db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
