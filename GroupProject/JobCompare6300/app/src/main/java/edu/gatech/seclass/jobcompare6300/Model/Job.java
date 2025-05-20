package edu.gatech.seclass.jobcompare6300.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "job_table")
public class Job {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String company;
    private String location;
    private float costOfLivingIndex;
    private float yearlySalary;
    private float yearlyBonus;
    private float tuitionReimbursement;
    private float healthInsurance;
    private float employeeDiscount;
    private float childAdoptionAssistance;
    private float jobScore;

    public Job(String title, String company, String location, float costOfLivingIndex,
               float yearlySalary, float yearlyBonus, float tuitionReimbursement,
               float healthInsurance, float employeeDiscount, float childAdoptionAssistance) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.costOfLivingIndex = costOfLivingIndex;
        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.tuitionReimbursement = tuitionReimbursement;
        this.healthInsurance = healthInsurance;
        this.employeeDiscount = employeeDiscount;
        this.childAdoptionAssistance = childAdoptionAssistance;
        this.jobScore = 0;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public float getCostOfLivingIndex() {
        return costOfLivingIndex;
    }
    public void setCostOfLivingIndex(float costOfLivingIndex) {
        this.costOfLivingIndex = costOfLivingIndex;
    }
    public float getYearlySalary() {
        return yearlySalary;
    }
    public void setYearlySalary(float yearlySalary) {
        this.yearlySalary = yearlySalary;
    }
    public float getYearlyBonus() {
        return yearlyBonus;
    }
    public void setYearlyBonus(float yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }
    public float getTuitionReimbursement() {
        return tuitionReimbursement;
    }
    public void setTuitionReimbursement(float tuitionReimbursement) {
        this.tuitionReimbursement = tuitionReimbursement;
    }
    public float getHealthInsurance() {
        return healthInsurance;
    }
    public void setHealthInsurance(float healthInsurance) {
        this.healthInsurance = healthInsurance;
    }
    public float getEmployeeDiscount() {
        return employeeDiscount;
    }
    public void setEmployeeDiscount(float employeeDiscount) {
        this.employeeDiscount = employeeDiscount;
    }
    public float getChildAdoptionAssistance() {
        return childAdoptionAssistance;
    }
    public void setChildAdoptionAssistance(float childAdoptionAssistance) {
        this.childAdoptionAssistance = childAdoptionAssistance;
    }
    public float getJobScore() {
        return jobScore;
    }
    public void setJobScore(float jobScore) {
        this.jobScore = jobScore;
    }
}
