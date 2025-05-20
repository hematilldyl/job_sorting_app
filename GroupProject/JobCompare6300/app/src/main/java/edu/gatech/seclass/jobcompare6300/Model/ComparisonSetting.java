package edu.gatech.seclass.jobcompare6300.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "weights_table")
public class ComparisonSetting {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int salaryWeight;
    private int bonusWeight;
    private int tuitionWeight;
    private int healthWeight;
    private int discountWeight;
    private int adoptionWeight;

    public ComparisonSetting(int salaryWeight, int bonusWeight, int tuitionWeight,
                             int healthWeight, int discountWeight, int adoptionWeight) {
        this.salaryWeight = salaryWeight;
        this.bonusWeight = bonusWeight;
        this.tuitionWeight = tuitionWeight;
        this.healthWeight = healthWeight;
        this.discountWeight = discountWeight;
        this.adoptionWeight = adoptionWeight;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSalaryWeight() { return salaryWeight; }
    public int getBonusWeight() { return bonusWeight; }
    public int getTuitionWeight() { return tuitionWeight; }
    public int getHealthWeight() { return healthWeight; }
    public int getDiscountWeight() { return discountWeight; }
    public int getAdoptionWeight() { return adoptionWeight; }


    public void setSalaryWeight(int salaryWeight) {
        if (salaryWeight >= 0 && salaryWeight <= 9) {
            this.salaryWeight = salaryWeight;
        } else {
            throw new IllegalArgumentException("Salary weight must be between 0 and 9.");
        }
    }
    public void setBonusWeight(int bonusWeight) {
        if (bonusWeight >= 0 && bonusWeight <= 9) {
            this.bonusWeight = bonusWeight;
        } else {
            throw new IllegalArgumentException("Bonus weight must be between 0 and 9.");
        }
    }
    public void setTuitionWeight(int tuitionWeight) {
        if (tuitionWeight >= 0 && tuitionWeight <= 9) {
            this.tuitionWeight = tuitionWeight;
        } else {
            throw new IllegalArgumentException("Tuition weight must be between 0 and 9.");
        }
    }
    public void setHealthWeight(int healthWeight) {
        if (healthWeight >= 0 && healthWeight <= 9) {
            this.healthWeight = healthWeight;
        } else {
            throw new IllegalArgumentException("Health Insurance weight must be between 0 and 9.");
        }
    }
    public void setDiscountWeight(int discountWeight) {
        if (discountWeight >= 0 && discountWeight <= 9) {
            this.discountWeight = discountWeight;
        } else {
            throw new IllegalArgumentException("Employee Discount weight must be between 0 and 9.");
        }
    }
    public void setAdoptionWeight(int adoptionWeight) {
        if (adoptionWeight >= 0 && adoptionWeight <= 9) {
            this.adoptionWeight = adoptionWeight;
        } else {
            throw new IllegalArgumentException("Adoption weight must be between 0 and 9.");
        }
    }
}
