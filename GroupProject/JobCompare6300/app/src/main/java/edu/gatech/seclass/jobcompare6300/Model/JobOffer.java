package edu.gatech.seclass.jobcompare6300.Model;

import androidx.room.Entity;

@Entity(tableName = "job_offer_table")
public class JobOffer extends Job {

    private boolean isCurrentJob;

    public JobOffer(String title, String company, String location, float costOfLivingIndex,
                    float yearlySalary, float yearlyBonus, float tuitionReimbursement,
                    float healthInsurance, float employeeDiscount, float childAdoptionAssistance, boolean isCurrentJob) {
        super(title, company, location, costOfLivingIndex, yearlySalary, yearlyBonus, tuitionReimbursement,
                healthInsurance, employeeDiscount, childAdoptionAssistance);
        this.isCurrentJob = isCurrentJob;
    }

    public boolean isCurrentJob() {
        return this.isCurrentJob;
    }
    public void setCurrentJob(boolean isCurrentJob) {
        this.isCurrentJob = isCurrentJob;
    }
}
