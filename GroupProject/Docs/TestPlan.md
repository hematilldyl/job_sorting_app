# Test Plan

This document provides the test plan for the job offer comparison app we are to build.

**Author**: tean097

## 1 Testing Strategy

### 1.1 Overall strategy

The testing process will include the following levels and will be performed by team member mvillegas34:

- **Unit Testing**:  
    
  - We will be testing individual classes and methods in isolation.


- **Integration Testing**:  
    
  - We will be verifying interactions between classes, such as the jobOfferManager and comparisonSettings classes.  
  - This will be done once unit testing is complete.


- **System Testing**:  
    
  - We will implement system testing to ensure the entire application meets the requirements and functions as expected.  
  - This will be done after integration testing is complete.


- **Regression Testing**:  
    
  - Ensures that new changes or fixes do not break existing functionality.  
  - This will be only done if we decide to make changes and fixes in our code.

### 1.2 Test Selection

- **Black-box Testing**:  
    
  - We will use this for system and integration testing to verify that the application meets its functional requirements without focusing on internal implementation.  
  - This will allow us to perform early test design even before our code is written


- **White-box Testing**:  
    
  - We will be using this for unit testing to ensure code coverage, including branch and statement coverage.

### 1.3 Adequacy Criterion

These are our adequacy criterion:

- For unit testing, a 95% branch coverage criterion will be used.  
    
- For system testing, all functional requirements must have at least one corresponding test case.  
    
- For integration testing, all key interactions between modules will be tested

### 1.4 Bug Tracking

Bugs and enhancement requests will be tracked using separate document with the following information

- Description of the bug.  
- Steps to reproduce.  
- Expected and actual behavior.

### 1.5 Technology

We will be using the following technology for testing:

- Unit Testing Framework: JUnit for automated testing of Java classes.

## 2 Test Cases

| Test Case | Purpose | Steps | Expected Result | Actual Result | Pass/Fail | Notes |
| :---- | :---- | :---- | :---- | :---- | :---- | :---- |
| Test 1 | Verify main menu loads successfully | 1\. Launch the application. | Main menu displays options for job management. | Application launched and displays main menu options for job management | Pass | Worked as expected |
| Test 2 | Validate job details can be entered and saved | 1\. Navigate to current job entry screen. 2\. Enter job details. 3\. Save details. | Job details are saved and viewable. | Jobs can be entered and saved | Pass | This works correctly. |
| Test 3 | Check job offer comparison functionality | 1\. Enter two job offers. 2\. Initiate comparison. | Comparison table with calculated scores is displayed. | I can enter two job offers and the comparisons are displayed | Pass | The results are outputted as expected with correct results. |
| Test 4 | Ensure weights are adjustable in settings | 1\. Navigate to comparison settings. 2\. Adjust weights. 3\. Save changes. | Weights are updated and saved correctly. | Weights can be changed and saved | Pass | Worked as expected |
| Test 5 | Verify invalid weights are rejected | 1\. Navigate to comparison settings. 2\. Enter invalid weights (e.g., 0 or 10). 3\. Attempt to save. | Error message is displayed, and weights are not saved. | No error is thrown | Pass | We have added validation and now it works properly |
| Test 6 | Test ranking of job offers | 1\. Enter three job offers with varying details. 2\. View ranked list. | Job offers are ranked correctly based on weights. | When testing it returned an empty value | Pass | This passes the test in the front end. |
| Test 7 | Check return to main menu from any screen | 1\. Navigate to any secondary screen. 2\. Select return option. | Main menu is displayed. | I navigated to all possible screens and can return to the main menu | Pass | Runs as expected |

