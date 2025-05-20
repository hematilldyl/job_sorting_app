package edu.gatech.seclass.jobcompare6300;
import edu.gatech.seclass.jobcompare6300.Dao.JobOfferDao;
import edu.gatech.seclass.jobcompare6300.Dao.ComparisonSettingDao;
import edu.gatech.seclass.jobcompare6300.Model.Job;
import edu.gatech.seclass.jobcompare6300.Model.JobOffer;
import edu.gatech.seclass.jobcompare6300.Model.ComparisonSetting;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Arrays;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import edu.gatech.seclass.jobcompare6300.Model.JobOfferManager;
import edu.gatech.seclass.jobcompare6300.db.AppDatabase;
import static org.mockito.Mockito.*;


public class JobTest {

    private Job job;
    private JobOfferManager jobOfferManager;
    private ComparisonSetting comparisonSetting;
    private JobOfferDao mockJobOfferDao;
    private AppDatabase db;
    private ComparisonSettingDao mockComparisonSettingDao;

    @Before
    public void setUp() {

        job = new Job(
                "Data Analyst",
                "MGM Resorts International",
                "Las Vegas",
                1.5f,      // Cost of Living Index
                120000f,   // Yearly Salary
                15000f,    // Yearly Bonus
                5000f,     // Tuition Reimbursement
                10000f,    // Health Insurance
                3000f,     // Employee Discount
                2000f      // Child Adoption Assistance
        );
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("Data Analyst", job.getTitle());
        assertEquals("MGM Resorts International", job.getCompany());
        assertEquals("Las Vegas", job.getLocation());
        assertEquals(1.5f, job.getCostOfLivingIndex(), 0.001);
        assertEquals(120000f, job.getYearlySalary(), 0.001);
        assertEquals(15000f, job.getYearlyBonus(), 0.001);
        assertEquals(5000f, job.getTuitionReimbursement(), 0.001);
        assertEquals(10000f, job.getHealthInsurance(), 0.001);
        assertEquals(3000f, job.getEmployeeDiscount(), 0.001);
        assertEquals(2000f, job.getChildAdoptionAssistance(), 0.001);
        assertEquals(0, job.getJobScore(), 0.001); // Default jobScore
    }

    @Test
    public void testSetters() {
        job.setTitle("Software Engineer");
        job.setCompany("Caesars Entertainment");
        job.setLocation("Las Vegas");
        job.setCostOfLivingIndex(2.0f);
        job.setYearlySalary(140000f);
        job.setYearlyBonus(20000f);
        job.setTuitionReimbursement(6000f);
        job.setHealthInsurance(12000f);
        job.setEmployeeDiscount(4000f);
        job.setChildAdoptionAssistance(3000f);
        job.setJobScore(85.5f);

        assertEquals("Software Engineer", job.getTitle());
        assertEquals("Caesars Entertainment", job.getCompany());
        assertEquals("Las Vegas", job.getLocation());
        assertEquals(2.0f, job.getCostOfLivingIndex(), 0.001);
        assertEquals(140000f, job.getYearlySalary(), 0.001);
        assertEquals(20000f, job.getYearlyBonus(), 0.001);
        assertEquals(6000f, job.getTuitionReimbursement(), 0.001);
        assertEquals(12000f, job.getHealthInsurance(), 0.001);
        assertEquals(4000f, job.getEmployeeDiscount(), 0.001);
        assertEquals(3000f, job.getChildAdoptionAssistance(), 0.001);
        assertEquals(85.5f, job.getJobScore(), 0.001);
    }

    @Test
    public void testDefaultId() {
        assertEquals(0, job.getId());
    }

    @Test
    public void testSetId() {
        job.setId(101);
        assertEquals(101, job.getId());
    }

    @Before
    public void setUpJobOfferManager() {
        mockJobOfferDao = mock(JobOfferDao.class);

        jobOfferManager = new JobOfferManager(mockJobOfferDao);

        comparisonSetting = new ComparisonSetting(3, 2, 1, 1, 1, 1);
    }



    @Test
    public void testCompareJobOffers() {

        // Step 1: Enter Two Job Offers
        JobOffer jobOffer1 = new JobOffer(
                "Software Engineer",
                "Caesars Entertainment",
                "Las Vegas",
                1.2f,      // Cost of Living Index
                120000f,   // Yearly Salary
                15000f,    // Yearly Bonus
                5000f,     // Tuition Reimbursement
                10000f,    // Health Insurance
                3000f,     // Employee Discount
                2000f,      // Child Adoption Assistance
                false      //Is Current Job?
        );

        JobOffer jobOffer2 = new JobOffer(
                "Data Analyst",
                "MGM Resorts International",
                "Las Vegas",
                1.5f,      // Cost of Living Index
                110000f,   // Yearly Salary
                12000f,    // Yearly Bonus
                4000f,     // Tuition Reimbursement
                8000f,     // Health Insurance
                2500f,     // Employee Discount
                3000f,      // Child Adoption Assistance
                true      //Is Current Job?
        );
        when(mockJobOfferDao.getAllJobOffers()).thenReturn(Arrays.asList(jobOffer1, jobOffer2));

        List<Float> scores = jobOfferManager.compareJobOffers(jobOffer1, jobOffer2, comparisonSetting);


        float expectedScore1 = jobOfferManager.calculateJobScore(jobOffer1, comparisonSetting);
        float expectedScore2 = jobOfferManager.calculateJobScore(jobOffer2, comparisonSetting);

        assertEquals(3634377.78, scores.get(0), 1.0);
        assertEquals(2639418.55, scores.get(1), 1.0);

        verify(mockJobOfferDao, times(1)).getAllJobOffers();
    }


    @Before
    public void setUpComparisonTest() {
        mockComparisonSettingDao = mock(ComparisonSettingDao.class);

        comparisonSetting = new ComparisonSetting(1, 1, 1, 1, 1, 1);

        when(mockComparisonSettingDao.getSettings()).thenReturn(comparisonSetting);
    }

    @Test
    public void testAdjustAndSaveWeights() {
        ComparisonSetting currentSettings = mockComparisonSettingDao.getSettings();
        assertEquals(1, currentSettings.getSalaryWeight());
        assertEquals(1, currentSettings.getBonusWeight());
        assertEquals(1, currentSettings.getTuitionWeight());
        assertEquals(1, currentSettings.getHealthWeight());
        assertEquals(1, currentSettings.getDiscountWeight());
        assertEquals(1, currentSettings.getAdoptionWeight());

        currentSettings.setSalaryWeight(5);
        currentSettings.setBonusWeight(3);
        currentSettings.setTuitionWeight(4);
        currentSettings.setHealthWeight(2);
        currentSettings.setDiscountWeight(6);
        currentSettings.setAdoptionWeight(7);

        mockComparisonSettingDao.update(currentSettings);

        when(mockComparisonSettingDao.getSettings()).thenReturn(currentSettings); // Simulate retrieval of saved settings
        ComparisonSetting updatedSettings = mockComparisonSettingDao.getSettings();

        assertEquals(5, updatedSettings.getSalaryWeight());
        assertEquals(3, updatedSettings.getBonusWeight());
        assertEquals(4, updatedSettings.getTuitionWeight());
        assertEquals(2, updatedSettings.getHealthWeight());
        assertEquals(6, updatedSettings.getDiscountWeight());
        assertEquals(7, updatedSettings.getAdoptionWeight());

        verify(mockComparisonSettingDao, times(1)).update(currentSettings);
    }

    @Before
    public void setUpInvalidWeights() {
        mockComparisonSettingDao = mock(ComparisonSettingDao.class);

        comparisonSetting = new ComparisonSetting(1, 1, 1, 1, 1, 1);

        when(mockComparisonSettingDao.getSettings()).thenReturn(comparisonSetting);
    }

    @Test
    public void testInvalidWeightsAreRejected() {
        ComparisonSetting currentSettings = mockComparisonSettingDao.getSettings();

        assertThrows(IllegalArgumentException.class, () -> currentSettings.setSalaryWeight(-1));
        assertThrows(IllegalArgumentException.class, () -> currentSettings.setBonusWeight(-2));
        assertThrows(IllegalArgumentException.class, () -> currentSettings.setTuitionWeight(-3));
        assertThrows(IllegalArgumentException.class, () -> currentSettings.setHealthWeight(-4));
        assertThrows(IllegalArgumentException.class, () -> currentSettings.setDiscountWeight(-5));
        assertThrows(IllegalArgumentException.class, () -> currentSettings.setAdoptionWeight(-5));

        assertEquals(1, currentSettings.getSalaryWeight());
        assertEquals(1, currentSettings.getBonusWeight());
        assertEquals(1, currentSettings.getTuitionWeight());
        assertEquals(1, currentSettings.getHealthWeight());
        assertEquals(1, currentSettings.getDiscountWeight());
        assertEquals(1, currentSettings.getAdoptionWeight());

        verify(mockComparisonSettingDao, never()).update(any(ComparisonSetting.class));
    }


    @Before
    public void setUpJobRanking() {
        mockJobOfferDao = mock(JobOfferDao.class);

        jobOfferManager = new JobOfferManager(mockJobOfferDao);

        comparisonSetting = new ComparisonSetting(3, 2, 1, 1, 1, 1);
    }

    @Test
    public void testRankingOfJobOffers() {
        JobOffer jobOffer1 = new JobOffer(
                "Software Engineer",
                "Caesars Entertainment",
                "Las Vegas",
                1.2f, 130000f, 18000f, 4000f, 9000f, 2000f, 3000f, false);

        JobOffer jobOffer2 = new JobOffer(
                "Data Analyst",
                "MGM Resorts",
                "Las Vegas",
                1.5f, 110000f, 12000f, 5000f, 8000f, 2500f, 2000f, false);

        JobOffer jobOffer3 = new JobOffer(
                "Product Manager",
                "Google",
                "Mountain View",
                1.0f, 150000f, 25000f, 7000f, 12000f, 5000f, 4000f, false);

        when(mockJobOfferDao.getAllJobOffers()).thenReturn(Arrays.asList(jobOffer1, jobOffer2, jobOffer3));

        List<JobOffer> rankedJobOffers = jobOfferManager.displayJobOffers(comparisonSetting);

        assertEquals("Product Manager", rankedJobOffers.get(0).getTitle());
        assertEquals("Software Engineer", rankedJobOffers.get(1).getTitle());
        assertEquals("Data Analyst", rankedJobOffers.get(2).getTitle());

        verify(mockJobOfferDao, times(1)).getAllJobOffers();
    }






}