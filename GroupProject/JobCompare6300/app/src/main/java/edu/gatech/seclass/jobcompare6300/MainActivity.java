package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.AdjustWeights.AdjustWeightsActivity;
import edu.gatech.seclass.jobcompare6300.CurrentJob.CurrentJobActivity;
import edu.gatech.seclass.jobcompare6300.JobCompare.JobCompareActivity;
import edu.gatech.seclass.jobcompare6300.JobOffer.JobOfferActivity;
import edu.gatech.seclass.jobcompare6300.db.AppDatabase;

public class MainActivity extends AppCompatActivity {


    private Button buttonGoToCurrentJob, buttonGoToJobOffer, buttonAdjustComparisonSettings, buttonClearDatabase, buttonCompareJobOffers;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonGoToCurrentJob = findViewById(R.id.buttonGoToCurrentJob);
        buttonGoToJobOffer = findViewById(R.id.buttonGoToJobOffer);
        buttonAdjustComparisonSettings = findViewById(R.id.buttonAdjustComparisonSettings);
        buttonClearDatabase = findViewById(R.id.buttonClearDatabase);
        buttonCompareJobOffers = findViewById(R.id.buttonCompareJobOffers);
        db = AppDatabase.getInstance(this);

        buttonGoToCurrentJob.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrentJobActivity.class);
            startActivity(intent);
        });

        buttonGoToJobOffer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JobOfferActivity.class);
            startActivity(intent);
        });

        buttonAdjustComparisonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdjustWeightsActivity.class);
            startActivity(intent);
        });

        buttonCompareJobOffers.setOnClickListener(v -> {
            Log.d("MainActivity", "Compare Job Offers button clicked");
            Intent intent = new Intent(MainActivity.this, JobCompareActivity.class);
            startActivity(intent);
        });

        buttonClearDatabase.setOnClickListener(v -> clearDatabase());
    }

    private void clearDatabase() {
        new Thread(() -> {
            db.jobOfferDao().clearAllJobs();
            db.comparisonSettingDao().clearSettings();

            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Database Cleared", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
