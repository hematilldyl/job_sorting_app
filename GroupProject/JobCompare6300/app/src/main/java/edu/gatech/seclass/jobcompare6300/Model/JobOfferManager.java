package edu.gatech.seclass.jobcompare6300.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.Dao.JobOfferDao;

public class JobOfferManager {
    private List<JobOffer> jobOffers;
    private JobOfferDao jobOfferDao;
    public JobOfferManager(JobOfferDao jobOfferDao) {
        this.jobOfferDao = jobOfferDao;
        this.jobOffers = jobOfferDao.getAllJobOffers();
        if (this.jobOffers == null) {
            this.jobOffers = new ArrayList<>();
        }
    }

    public List<JobOffer> displayJobOffers(ComparisonSetting comparisonSetting) {
        if (this.jobOffers.isEmpty()) return new ArrayList<>();

        List<JobOffer> rankedJobOffers = this.jobOffers;
        for (JobOffer jobOffer: rankedJobOffers) {
            float score = this.calculateJobScore(jobOffer, comparisonSetting);
            jobOffer.setJobScore(score);
            jobOfferDao.update(jobOffer);
        }
        // ranked from best to worst
        Collections.sort(rankedJobOffers, (a, b) -> Float.compare(b.getJobScore(), a.getJobScore()));
        return rankedJobOffers;
    }

    public List<Float> compareJobOffers(JobOffer jobOffer1, JobOffer jobOffer2, ComparisonSetting comparisonSetting) {
        float score1 = this.calculateJobScore(jobOffer1, comparisonSetting);
        float score2 = this.calculateJobScore(jobOffer2, comparisonSetting);
        return new ArrayList<>(List.of(score1, score2));
    }

    public float calculateJobScore(JobOffer jobOffer, ComparisonSetting comparisonSetting) {
        // jobOffer
        float AYS = jobOffer.getYearlySalary() / jobOffer.getCostOfLivingIndex() * 100;
        float AYB = jobOffer.getYearlyBonus() / jobOffer.getCostOfLivingIndex() * 100;
        float TR = jobOffer.getTuitionReimbursement();
        float HI = Math.min(jobOffer.getHealthInsurance(),1000) + 0.02f * AYS;
        float EPSD = jobOffer.getEmployeeDiscount();
        float CAA = jobOffer.getChildAdoptionAssistance();

        //weights
       int AYSW = 1;
       int AYBW = 1;
       int TRW = 1;
       int HIW = 1;
       int EPSDW = 1;
       int CAAW = 1;

       if (comparisonSetting != null){
           AYSW = comparisonSetting.getSalaryWeight();
           AYBW = comparisonSetting.getBonusWeight();
           TRW = comparisonSetting.getTuitionWeight();
           HIW = comparisonSetting.getHealthWeight();
           EPSDW = comparisonSetting.getDiscountWeight();
           CAAW = comparisonSetting.getAdoptionWeight();
       }
        float totalWeight = (float)(AYSW + AYBW + TRW + HIW + EPSDW + CAAW);

        float score = AYSW/totalWeight * AYS +
                AYBW/totalWeight * AYB +
                TRW/totalWeight * TR +
                HIW/totalWeight * HI +
                EPSDW/totalWeight * EPSD +
                CAAW/totalWeight * (CAA/5);

        return score;
    }
}
