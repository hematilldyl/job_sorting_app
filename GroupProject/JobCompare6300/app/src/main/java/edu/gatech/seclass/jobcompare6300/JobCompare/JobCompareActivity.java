package edu.gatech.seclass.jobcompare6300.JobCompare;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.gatech.seclass.jobcompare6300.Dao.ComparisonSettingDao;
import edu.gatech.seclass.jobcompare6300.Dao.JobOfferDao;
import edu.gatech.seclass.jobcompare6300.Model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.Model.JobOffer;
import edu.gatech.seclass.jobcompare6300.Model.JobOfferManager;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.AppDatabase;

public class JobCompareActivity extends AppCompatActivity {
    private JobOfferDao jobOfferDao;
    private ComparisonSettingDao comparisonSettingDao;
    private LinearLayout jobOffersListLayout;
    private Button compareButton, mainMenuButton;
    private ScrollView jobOffersListScrollView;

    private List<JobOffer> jobOffers = new ArrayList<>();
    private List<JobOffer> selectedJobs = new ArrayList<>();
    private AppDatabase db;

    private ExecutorService databaseExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_compare);

        // UI
        jobOffersListScrollView = findViewById(R.id.jobOffersListScrollView);
        jobOffersListLayout = findViewById(R.id.jobOffersListLayout);
        compareButton = findViewById(R.id.compareButton);
        mainMenuButton = findViewById(R.id.mainMenuButton);
        jobOffersListScrollView = findViewById(R.id.jobOffersListScrollView);

        // database
        db = AppDatabase.getInstance(this);
        jobOfferDao = db.jobOfferDao();
        comparisonSettingDao = db.comparisonSettingDao();
        databaseExecutor = Executors.newSingleThreadExecutor();

        // Load job offers in the background
        loadJobOffers();

        compareButton.setOnClickListener(v -> compareJobs());
        mainMenuButton.setOnClickListener(v -> finish());
    }

    private void loadJobOffers() {
        databaseExecutor.execute(() -> {
            JobOfferManager jobOfferManager = new JobOfferManager(jobOfferDao);
            ComparisonSetting comparisonSetting = comparisonSettingDao.getSettings();
            List<JobOffer> rankedOffers = jobOfferManager.displayJobOffers(comparisonSetting);
            runOnUiThread(() -> updateUIWithJobOffers(rankedOffers));
        });
    }
    private void updateUIWithJobOffers(List<JobOffer> offers) {
        jobOffersListLayout.removeAllViews();
        jobOffers.clear();
        jobOffers.addAll(offers);

        if (jobOffers.isEmpty()) {
            Toast.makeText(this, "No job offers found.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (JobOffer jobOffer : jobOffers) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(jobOffer.getTitle() + " - " + jobOffer.getCompany());
            checkBox.setTag(jobOffer);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                JobOffer selectedJob = (JobOffer) buttonView.getTag();
                if (isChecked) {
                    if (selectedJobs.size() >= 2) {
                        buttonView.setChecked(false);
                        Toast.makeText(this, "You can only select two job offers for comparison.", Toast.LENGTH_SHORT).show();
                    } else {
                        selectedJobs.add(selectedJob);
                        Log.d("JobCompare", "Added: " + selectedJob.getTitle() + " - " + selectedJob.getCompany());
                    }
                } else {
                    selectedJobs.remove(selectedJob);
                }
            });
            jobOffersListLayout.addView(checkBox);
        }
    }

    private void compareJobs() {
        if (selectedJobs.size() != 2) {
            Toast.makeText(this, "Please select exactly two jobs to compare.", Toast.LENGTH_SHORT).show();
            return;
        }

        JobOffer job1 = selectedJobs.get(0);
        JobOffer job2 = selectedJobs.get(1);

        databaseExecutor.execute(() -> {
            JobOfferManager jobOfferManager = new JobOfferManager(jobOfferDao);
            ComparisonSetting comparisonSetting = comparisonSettingDao.getSettings();
            float score1 = jobOfferManager.calculateJobScore(job1, comparisonSetting);
            float score2 = jobOfferManager.calculateJobScore(job2, comparisonSetting);

            runOnUiThread(() -> {
                ((TextView) findViewById(R.id.title1)).setText(job1.getTitle());
                ((TextView) findViewById(R.id.title2)).setText(job2.getTitle());
                ((TextView) findViewById(R.id.company1)).setText(job1.getCompany());
                ((TextView) findViewById(R.id.company2)).setText(job2.getCompany());
                ((TextView) findViewById(R.id.salary1)).setText(String.valueOf(job1.getYearlySalary()));
                ((TextView) findViewById(R.id.salary2)).setText(String.valueOf(job2.getYearlySalary()));
                ((TextView) findViewById(R.id.bonus1)).setText(String.valueOf(job1.getYearlyBonus()));
                ((TextView) findViewById(R.id.bonus2)).setText(String.valueOf(job2.getYearlyBonus()));
                ((TextView) findViewById(R.id.tuition1)).setText(String.valueOf(job1.getTuitionReimbursement()));
                ((TextView) findViewById(R.id.tuition2)).setText(String.valueOf(job2.getTuitionReimbursement()));
                ((TextView) findViewById(R.id.insurance1)).setText(String.valueOf(job1.getHealthInsurance()));
                ((TextView) findViewById(R.id.insurance2)).setText(String.valueOf(job2.getHealthInsurance()));
                ((TextView) findViewById(R.id.discount1)).setText(String.valueOf(job1.getEmployeeDiscount()));
                ((TextView) findViewById(R.id.discount2)).setText(String.valueOf(job2.getEmployeeDiscount()));
                ((TextView) findViewById(R.id.adoption1)).setText(String.valueOf(job1.getChildAdoptionAssistance()));
                ((TextView) findViewById(R.id.adoption2)).setText(String.valueOf(job2.getChildAdoptionAssistance()));
                ((TextView) findViewById(R.id.score1)).setText(String.valueOf(score1));
                ((TextView) findViewById(R.id.score2)).setText(String.valueOf(score2));
            });
        });
    }
}

