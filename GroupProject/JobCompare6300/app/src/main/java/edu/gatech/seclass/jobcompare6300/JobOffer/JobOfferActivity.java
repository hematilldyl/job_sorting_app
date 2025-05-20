package edu.gatech.seclass.jobcompare6300.JobOffer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.Model.JobOffer;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.AppDatabase;

public class JobOfferActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextCompany, editTextLocation, editTextCOL,
            editTextSalary, editTextBonus, editTextTuition, editTextInsurance, editTextEmployeeDiscount, editTextAdoptionAssistance;

    private RadioGroup radioGroupCurrentJob;
    private RadioButton radioButtonYes, radioButtonNo;
    private Button saveButton, cancelButton;

    private boolean isCurrentJob;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextCOL = findViewById(R.id.editTextCOL);
        editTextSalary = findViewById(R.id.editTextSalary);
        editTextBonus = findViewById(R.id.editTextBonus);
        editTextTuition = findViewById(R.id.editTuition);
        editTextInsurance = findViewById(R.id.editInsurance);
        editTextEmployeeDiscount = findViewById(R.id.editEmployeeDiscount);
        editTextAdoptionAssistance = findViewById(R.id.editAdoptionAssistance);

        radioGroupCurrentJob = findViewById(R.id.radioGroupCurrentJob);
        radioButtonYes = findViewById(R.id.radioButtonYes);
        radioButtonNo = findViewById(R.id.radioButtonNo);

        saveButton = findViewById(R.id.buttonSave);
        cancelButton = findViewById(R.id.buttonCancel);

        db = AppDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> saveJobOffer());
        cancelButton.setOnClickListener(view -> finish());
    }

    private void saveJobOffer() {
        clearErrors();

        if (radioGroupCurrentJob.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select whether this is your current job", Toast.LENGTH_SHORT).show();
            return;
        }

        isCurrentJob = radioGroupCurrentJob.getCheckedRadioButtonId() == R.id.radioButtonYes;

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
                    isCurrentJob
            );

            new Thread(() -> {
                if (isCurrentJob) {
                    db.jobOfferDao().clearCurrentJobs();
                }
                db.jobOfferDao().insert(jobOffer);
                runOnUiThread(() -> {
                    Toast.makeText(JobOfferActivity.this, "Job Offer Saved", Toast.LENGTH_SHORT).show();
                    clearForm();
                });
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

    private void clearForm() {
        editTextTitle.setText("");
        editTextCompany.setText("");
        editTextLocation.setText("");
        editTextCOL.setText("");
        editTextSalary.setText("");
        editTextBonus.setText("");
        editTextTuition.setText("");
        editTextInsurance.setText("");
        editTextEmployeeDiscount.setText("");
        editTextAdoptionAssistance.setText("");

        radioGroupCurrentJob.clearCheck();

        editTextTitle.requestFocus();
    }

}
