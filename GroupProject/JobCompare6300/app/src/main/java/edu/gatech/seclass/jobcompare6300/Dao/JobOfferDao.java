package edu.gatech.seclass.jobcompare6300.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.Model.JobOffer;

@Dao
public interface JobOfferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JobOffer jobOffer);

    @Update
    void update(JobOffer jobOffer);

    @Delete
    void delete(JobOffer jobOffer);

    @Query("SELECT * FROM job_offer_table ORDER BY jobScore DESC")
    List<JobOffer> getAllJobOffers();

    @Query("SELECT * FROM job_offer_table WHERE id = :jobOfferId")
    JobOffer getJobOfferById(int jobOfferId);

    @Query("SELECT * FROM job_offer_table WHERE isCurrentJob = 1 LIMIT 1")
    JobOffer getCurrentJob();

    @Query("UPDATE job_offer_table SET isCurrentJob = 0 WHERE isCurrentJob = 1")
    void clearCurrentJobs();

    @Query("DELETE FROM job_offer_table")
    void deleteAllJobOffers();

    @Query("DELETE FROM job_offer_table WHERE id = :jobOfferId")
    void deleteJobById(int jobOfferId);

    @Query("DELETE FROM job_offer_table")
    void clearAllJobs();

}
