package edu.gatech.seclass.jobcompare6300.AdjustWeights;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.MainActivity;
import edu.gatech.seclass.jobcompare6300.Model.ComparisonSetting;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.AppDatabase;

public class AdjustWeightsActivity extends AppCompatActivity {
    private SeekBar seekSalary, seekBonus, seekTuition, seekHealth, seekDiscount, seekAdoption;
    private TextView txtSalary, txtBonus, txtTuition, txtHealth, txtDiscount, txtAdoption;
    private Button btnSave, btnCancel;
    private AppDatabase db;

    private int weightSalary = 1, weightBonus = 1, weightTuition = 1, weightHealth = 1,
            weightDiscount = 1, weightAdoption = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_weight);

        seekSalary = findViewById(R.id.seekSalary);
        seekBonus = findViewById(R.id.seekBonus);
        seekTuition = findViewById(R.id.seekTuition);
        seekHealth = findViewById(R.id.seekHealth);
        seekDiscount = findViewById(R.id.seekDiscount);
        seekAdoption = findViewById(R.id.seekAdoption);

        txtSalary = findViewById(R.id.tvSalaryWeight);
        txtBonus = findViewById(R.id.tvYearlyBonus);
        txtTuition = findViewById(R.id.tvTuition);
        txtHealth = findViewById(R.id.tvHealth);
        txtDiscount = findViewById(R.id.tvDiscount);
        txtAdoption = findViewById(R.id.tvAdoption);

        btnSave = findViewById(R.id.buttonSave);
        btnCancel = findViewById(R.id.buttonCancel);

        db = AppDatabase.getInstance(this);

        btnSave.setOnClickListener(v -> saveWeightSetting());
        btnCancel.setOnClickListener(view -> finish());

        initializeSeekBars();


        seekSalary.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightSalary = progress;
                txtSalary.setText("Salary Weight: " + weightSalary);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBonus.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightBonus = progress;
                txtBonus.setText("Bonus Weight: " + weightBonus);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekTuition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightTuition = progress;
                txtTuition.setText("Tuition Weight: " + weightTuition);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekHealth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightHealth = progress;
                txtHealth.setText("Health Weight: " + weightHealth);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekDiscount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightDiscount = progress;
                txtDiscount.setText("Discount Weight: " + weightDiscount);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekAdoption.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightAdoption = progress;
                txtAdoption.setText("Adoption Weight: " + weightAdoption);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


    private void initializeSeekBars() {
        new Thread(() -> {
            ComparisonSetting setting = db.comparisonSettingDao().getSettings();
            if (setting != null) {
                weightSalary = setting.getSalaryWeight();
                weightBonus = setting.getBonusWeight();
                weightTuition = setting.getTuitionWeight();
                weightHealth = setting.getHealthWeight();
                weightDiscount = setting.getDiscountWeight();
                weightAdoption = setting.getAdoptionWeight();
            }

            runOnUiThread(() -> {
                seekSalary.setProgress(weightSalary);
                seekBonus.setProgress(weightBonus);
                seekTuition.setProgress(weightTuition);
                seekHealth.setProgress(weightHealth);
                seekDiscount.setProgress(weightDiscount);
                seekAdoption.setProgress(weightAdoption);

                txtSalary.setText("Salary Weight: " + weightSalary);
                txtBonus.setText("Bonus Weight: " + weightBonus);
                txtTuition.setText("Tuition Weight: " + weightTuition);
                txtHealth.setText("Health Weight: " + weightHealth);
                txtDiscount.setText("Discount Weight: " + weightDiscount);
                txtAdoption.setText("Adoption Weight: " + weightAdoption);
            });
        }).start();
    }




    private void saveWeightSetting() {
        new Thread(() -> {
            ComparisonSetting setting = new ComparisonSetting(weightSalary, weightBonus, weightTuition,
                    weightHealth, weightDiscount, weightAdoption);

            db.comparisonSettingDao().clearSettings();
            db.comparisonSettingDao().insert(setting);

            runOnUiThread(() -> {
                Intent intent = new Intent(AdjustWeightsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}
