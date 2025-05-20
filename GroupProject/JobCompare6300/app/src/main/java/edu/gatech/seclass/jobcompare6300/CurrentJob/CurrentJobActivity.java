package edu.gatech.seclass.jobcompare6300.CurrentJob;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.Model.JobOffer;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.AppDatabase;

public class CurrentJobActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextCompany, editTextLocation, editTextCOL,
            editTextSalary, editTextBonus, editTextTuition, editTextInsurance, editTextEmployeeDiscount, editTextAdoptionAssistance;

    private Button saveButton, cancelButton;

    private TextWatcher watcher;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextCOL = findViewById(R.id.editTextCOL);
        editTextSalary = findViewById(R.id.editTextSalary);
        editTextBonus = findViewById(R.id.editTextBonus);
        editTextTuition = findViewById(R.id.editTextTuition);
        editTextInsurance = findViewById(R.id.editTextInsurance);
        editTextEmployeeDiscount = findViewById(R.id.editTextEmployeeDiscount);
        editTextAdoptionAssistance = findViewById(R.id.editTextAdoptionAssistance);

        saveButton = findViewById(R.id.buttonSave);
        cancelButton = findViewById(R.id.buttonCancel);

        db = AppDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> saveCurrentJob());
        cancelButton.setOnClickListener(view -> finish());

        loadCurrentJobDetails();
        setupTextWatchers();
    }

    private void loadCurrentJobDetails() {
        new Thread(() -> {
            try {
                JobOffer currentJob = db.jobOfferDao().getCurrentJob();
                runOnUiThread(() -> {
                    if (currentJob != null) {

                        removeTextWatchers();

                        editTextTitle.setText(currentJob.getTitle());
                        editTextCompany.setText(currentJob.getCompany());
                        editTextLocation.setText(currentJob.getLocation());
                        editTextCOL.setText(String.valueOf(currentJob.getCostOfLivingIndex()));
                        editTextSalary.setText(String.valueOf(currentJob.getYearlySalary()));
                        editTextBonus.setText(String.valueOf(currentJob.getYearlyBonus()));
                        editTextTuition.setText(String.valueOf(currentJob.getTuitionReimbursement()));
                        editTextInsurance.setText(String.valueOf(currentJob.getHealthInsurance()));
                        editTextEmployeeDiscount.setText(String.valueOf(currentJob.getEmployeeDiscount()));
                        editTextAdoptionAssistance.setText(String.valueOf(currentJob.getChildAdoptionAssistance()));

                        setupTextWatchers();
                        clearErrors();

                    } else {
                        Toast.makeText(CurrentJobActivity.this, "No current job found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e("CurrentJobActivity", "Error loading job details", e);
                runOnUiThread(() -> Toast.makeText(CurrentJobActivity.this, "Error loading job details" + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void saveCurrentJob() {

        clearErrors();

        if (isValidInput()) {
            JobOffer jobOffer = new JobOffer(
                    editTextTitle.getText().toString(),
                    editTextCompany.getText().toString(),
                    editTextLocation.getText().toString(),
                    Float.parseFloat(editTextCOL.getText().toString()),
                    Float.parseFloat(editTextSalary.getText().toString()),
                    Float.parseFloat(editTextBonus.getText().toString()),
                    Float.parseFloat(editTextTuition.getText().toString()),
                    Float.parseFloat(editTextInsurance.getText().toString()),
                    Float.parseFloat(editTextEmployeeDiscount.getText().toString()),
                    Float.parseFloat(editTextAdoptionAssistance.getText().toString()),
                    true
            );

            new Thread(() -> {
                JobOffer existingJob = db.jobOfferDao().getCurrentJob();
                if (existingJob != null) {
                    jobOffer.setId(existingJob.getId());
                    db.jobOfferDao().update(jobOffer);
                    runOnUiThread(() -> Toast.makeText(CurrentJobActivity.this, "Current Job Updated", Toast.LENGTH_SHORT).show());
                } else {
                    db.jobOfferDao().clearCurrentJobs();
                    db.jobOfferDao().insert(jobOffer);
                    runOnUiThread(() -> Toast.makeText(CurrentJobActivity.this, "New Current Job Saved", Toast.LENGTH_SHORT).show());
                }
                runOnUiThread(this::finish);
            }).start();
        } else {
            Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidInput() {
        boolean valid = true;

        if (editTextTitle.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextTitle.setError("Job Title is required"));
            valid = false;
        }

        if (editTextCompany.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextCompany.setError("Company is required"));
            valid = false;
        }
        if (editTextLocation.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextLocation.setError("Location is required"));
            valid = false;
        }
        if (editTextCOL.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextCOL.setError("Cost of Living is required"));
            valid = false;
        } else {
            try {
                float COL = Float.parseFloat(editTextCOL.getText().toString());
                if (COL <= 0) {
                    runOnUiThread(() -> editTextCOL.setError("Cost of Living cannot be 0"));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                runOnUiThread(() -> editTextCOL.setError("Invalid Cost of Living"));
                valid = false;
            }
        }
        if (editTextSalary.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextSalary.setError("Yearly Salary is required"));
            valid = false;
        } else {
            try {
                float salary = Float.parseFloat(editTextSalary.getText().toString());
                if (salary < 0) {
                    runOnUiThread(() -> editTextSalary.setError("Yearly Salary cannot be negative"));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                runOnUiThread(() -> editTextSalary.setError("Invalid Yearly Salary"));
                valid = false;
            }
        }
        if (editTextBonus.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextBonus.setError("Yearly Bonus is required"));
            valid = false;
        } else {
            try {
                float bonus = Float.parseFloat(editTextBonus.getText().toString());
                if (bonus < 0) {
                    runOnUiThread(() -> editTextBonus.setError("Yearly Bonus cannot be negative"));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                runOnUiThread(() -> editTextBonus.setError("Invalid Yearly Bonus"));
                valid = false;
            }
        }
        if (editTextTuition.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextTuition.setError("Tuition Reimbursement is required"));
            valid = false;
        } else {
            try {
                float tuition = Float.parseFloat(editTextTuition.getText().toString());
                if (tuition > 15000 || tuition <= 0) {
                    runOnUiThread(() -> editTextTuition.setError("Tuition Reimbursement is out of range"));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                runOnUiThread(() -> editTextTuition.setError("Invalid Tuition Reimbursement"));
                valid = false;
            }
        }
        if (editTextInsurance.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextInsurance.setError("Health Insurance is required"));
            valid = false;
        } else {
            try {
                float insurance = Float.parseFloat(editTextInsurance.getText().toString());
                float salary = Float.parseFloat(editTextSalary.getText().toString());
                if (insurance > 1000 + 0.02 * salary || insurance <= 0) {
                    runOnUiThread(() -> editTextInsurance.setError("Health Insurance is out of range"));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                runOnUiThread(() -> editTextInsurance.setError("Invalid Health Insurance"));
                valid = false;
            }
        }
        if (editTextEmployeeDiscount.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextEmployeeDiscount.setError("Employee Discount is required"));
            valid = false;
        } else {
            try {
                float employeeDiscount = Float.parseFloat(editTextEmployeeDiscount.getText().toString());
                float salary = Float.parseFloat(editTextSalary.getText().toString());
                if (employeeDiscount > 0.18 * salary || employeeDiscount <= 0) {
                    runOnUiThread(() -> editTextEmployeeDiscount.setError("Employee Discount is out of range"));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                runOnUiThread(() -> editTextEmployeeDiscount.setError("Invalid Employee Discount"));
                valid = false;
            }
        }
        if (editTextAdoptionAssistance.getText().toString().isEmpty()) {
            runOnUiThread(() -> editTextAdoptionAssistance.setError("Adoption Assistance is required"));
            valid = false;
        } else {
            try {
                float adoptionAssistance = Float.parseFloat(editTextAdoptionAssistance.getText().toString());
                if (adoptionAssistance > 30000 || adoptionAssistance <= 0) {
                    runOnUiThread(() -> editTextAdoptionAssistance.setError("Adoption Assistance is out of range"));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                runOnUiThread(() -> editTextAdoptionAssistance.setError("Invalid Adoption Assistance"));
                valid = false;
            }
        }
        return valid;
    }

    private void clearErrors() {
        editTextTitle.setError(null);
        editTextCompany.setError(null);
        editTextLocation.setError(null);
        editTextCOL.setError(null);
        editTextSalary.setError(null);
        editTextBonus.setError(null);
        editTextTuition.setError(null);
        editTextInsurance.setError(null);
        editTextEmployeeDiscount.setError(null);
        editTextAdoptionAssistance.setError(null);
    }
    private void setupTextWatchers() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                saveButton.setEnabled(isValidInput());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        editTextTitle.addTextChangedListener(watcher);
        editTextCompany.addTextChangedListener(watcher);
        editTextLocation.addTextChangedListener(watcher);
        editTextCOL.addTextChangedListener(watcher);
        editTextSalary.addTextChangedListener(watcher);
        editTextBonus.addTextChangedListener(watcher);
        editTextTuition.addTextChangedListener(watcher);
        editTextInsurance.addTextChangedListener(watcher);
        editTextEmployeeDiscount.addTextChangedListener(watcher);
        editTextAdoptionAssistance.addTextChangedListener(watcher);
    }
    private void removeTextWatchers() {
        if (watcher != null) {
            editTextTitle.removeTextChangedListener(watcher);
            editTextCompany.removeTextChangedListener(watcher);
            editTextLocation.removeTextChangedListener(watcher);
            editTextCOL.removeTextChangedListener(watcher);
            editTextSalary.removeTextChangedListener(watcher);
            editTextBonus.removeTextChangedListener(watcher);
            editTextTuition.removeTextChangedListener(watcher);
            editTextInsurance.removeTextChangedListener(watcher);
            editTextEmployeeDiscount.removeTextChangedListener(watcher);
            editTextAdoptionAssistance.removeTextChangedListener(watcher);
        }
    }
}




