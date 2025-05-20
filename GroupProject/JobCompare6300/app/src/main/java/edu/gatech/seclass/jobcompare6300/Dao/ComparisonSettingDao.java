package edu.gatech.seclass.jobcompare6300.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import edu.gatech.seclass.jobcompare6300.Model.ComparisonSetting;
@Dao
public interface ComparisonSettingDao {

    @Query("SELECT * FROM weights_table LIMIT 1")
    ComparisonSetting getSettings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ComparisonSetting setting);

    @Update
    void update(ComparisonSetting setting);

    @Query("DELETE FROM weights_table")
    void clearSettings();

}
