# Bank Management Android Application
**Project Title:** 26021_midterm_bank Student

## Overview
This is a small Bank Management system built for the Android platform as part of a midterm project. The application allows managing customers, bank accounts, and transactions with data persistence using SQLite.

## Features
- **Multi-Screen Navigation:** Dashboard, Customer Management, Account Management, and Transaction Management.
- **SQLite Database:** 3 related tables (Customers, Accounts, Transactions) with proper CRUD operations and JOIN queries.
- **Data Validation:** 
    - Ensures no empty fields are submitted.
    - Validates email formats (must contain '@').
    - Prevents negative bank balances.
- **SharedPreferences:** Saves and displays the Bank Name and Currency in the Dashboard.
- **CSV Export:** Allows exporting the list of customers to a CSV file in the internal storage.
- **BroadcastReceiver:** Detects internet connection status and notifies the user via Toast messages.
- **Custom Adapters:** Uses custom BaseAdapter and CursorAdapter for displaying lists.

## Activities
1. **MainActivity:** The dashboard with navigation buttons and bank branding.
2. **CustomerActivity:** Form to add new customers with validation and terms agreement.
3. **AccountActivity:** Form to create bank accounts for existing customers.
4. **TransactionActivity:** Interface to perform deposits and withdrawals.
5. **CustomerListActivity:** Displays all customers and allows CSV export and deletion (Long Click).
6. **AccountListActivity:** Displays all accounts with customer names (JOIN) and allows deletion (Long Click).
7. **SettingsActivity:** Update app settings like Bank Name and Currency.

## How to use
- Long click on any item in the Customer or Account lists to delete it.
- Ensure at least one customer is created before adding a bank account.
- The CSV file is saved in the app's internal storage folder.

## Database Structure
- **Customers Table:** `_id`, `fullName`, `phone`, `email`, `gender`
- **Accounts Table:** `_id`, `accountNumber`, `balance`, `customerId` (FK)
- **Transactions Table:** `_id`, `accountId` (FK), `type`, `amount`, `transDate`

## Future Improvements
- Add user authentication (Login/Register).
- Implement PDF report generation.
- Add Search functionality in List views.
