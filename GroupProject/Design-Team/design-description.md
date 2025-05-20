# Job Offer Comparison App Requirements

## 1. Main Menu

When the app starts, the user is presented with the **Main Menu**, which allows them to:

1. Enter or edit current job details  
2. Enter job offers  
3. Adjust comparison settings  
4. Compare job offers (disabled if no job offers were entered yet)  

The **Main Menu (mainMenu) class** provides these functions:

- `startup()`: Loads any existing data  
- `viewCurrentJob()`: Returns the current job (if it exists)  
- `viewJobOffers()`: Returns a list of job offers via `jobOfferManager`  
- `addJobOffer()`: Adds a new job offer via `jobOfferManager`  
- `editJobOffer()`: Edits an existing job offer via `jobOfferManager`  
- `compareJobOffers()`: Compares two job offers  
- `viewComparisonSettings()`: Calls the `comparisonSettings` class that contains the weights  

---

## 2. Entering Current Job Details

- Users can enter job details and either **save** or **cancel** the entry, returning to the main menu.  
- The **jobOfferManager** class handles job management.  
- The **jobOffer** class inherits from **job**, which has a boolean flag for whether the job is the current job.  
- The **job class** includes:  
  - `viewDetails()`: View job details  
  - `setDetails()`: Set job details  
- The **jobOffer class** includes:  
  - `open()`: Open a job offer  
  - `return()`: Return a job offer  
  - `cancel()`: Cancel a job offer  
  - `viewDetails()`: View job offer details  
  - `setDetails()`: Save job offer details  

---

## 3. Entering Job Offers

- Users will enter job offer details via a user interface.  
- Options available:  
  1. Save the job offer or cancel  
  2. Enter another offer  
  3. Return to the main menu  
  4. Compare the saved offer with the current job (if present)  

The **jobOfferManager** class handles this process with methods:

- `enterNewJob()`: Allows new job offers to be entered  
- `return()`: Returns to the main menu  
- `compareJobOffers()`: Compares job offers  
- `cancel()`: Cancels entry  
- `saveDetails()`: Saves an entry  
- `displayInterface()`: Calls the UI  

---

## 4. Adjusting Comparison Settings

- Users can assign integer weights to comparison factors.  
- If no weights are assigned, all factors are considered equal.  
- Users can either **save** the settings or **cancel**, both returning to the main menu.  

The **comparisonSettings** class handles this process with methods:

- `checkValidWeight()`: Ensures weights are between 1-9  
- `setWeights()`: Sets weights (default is 1)  
- `cancel()`: Cancels without saving  

---

## 5. Comparing Job Offers

- Users are shown a list of job offers (Title and Company) ranked from best to worst.  
- The current job (if present) is clearly indicated.  
- Users can select two jobs to compare, triggering a **comparison table** showing:  
  - Side-by-side job details  
  - Calculation details from Requirement 6  
- After comparison, users can **perform another comparison** or **return to the main menu**.  

The **jobOfferManager** class handles this with:

- `compareJobOffers()`: Compares jobs  
- `calculateJobScore()`: Computes job scores for ranking  
- `displayJobOffers()`: Returns the list of job offers  

---

## 6. Ranking Jobs

- A job's **score** is computed as the weighted average of its factors.  
- This is handled by the `calculateJobScore()` function in the `jobOfferManager` class.  

---

## 7. User Interface

- The UI must be **intuitive and responsive**.  
- Responsiveness is ensured through **simple class structures** and a clear design.  

---

## 8. System Assumptions

- The app runs on a **single system**, with no communication or data saving between devices.  
- This simplifies the implementation to a **self-contained application**.  
