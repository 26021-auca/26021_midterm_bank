package com.example.a26021_midterm_bank.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.a26021_midterm_bank.models.BankAccount;
import com.example.a26021_midterm_bank.models.Customer;
import com.example.a26021_midterm_bank.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DBNAME = "BankSystem.db";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Customer Table
        db.execSQL("CREATE TABLE customers (_id INTEGER PRIMARY KEY AUTOINCREMENT, fullName TEXT, phone TEXT, email TEXT, gender TEXT)");

        // Create Account Table with Foreign Key
        db.execSQL("CREATE TABLE accounts (_id INTEGER PRIMARY KEY AUTOINCREMENT, accountNumber TEXT, balance REAL, customerId INTEGER, " +
                "FOREIGN KEY(customerId) REFERENCES customers(_id))");

        // Create Transaction Table
        db.execSQL("CREATE TABLE transactions (_id INTEGER PRIMARY KEY AUTOINCREMENT, accountId INTEGER, type TEXT, amount REAL, transDate TEXT, " +
                "FOREIGN KEY(accountId) REFERENCES accounts(_id))");
        
        Log.d("DB", "Tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS customers");
        onCreate(db);
    }

    // --- CUSTOMER METHODS ---
    
    public boolean addCustomer(String name, String phone, String email, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullName", name);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("gender", gender);
        
        long result = db.insert("customers", null, cv);
        return result != -1; // returns true if success
    }

    public List<Customer> getCustomers() {
        List<Customer> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM customers", null);

        if (cursor.moveToFirst()) {
            do {
                Customer c = new Customer();
                c.setId(cursor.getInt(0));
                c.setFullName(cursor.getString(1));
                c.setPhone(cursor.getString(2));
                c.setEmail(cursor.getString(3));
                c.setGender(cursor.getString(4));
                list.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void deleteCustomer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("customers", "_id = " + id, null);
    }

    // --- ACCOUNT METHODS ---

    public boolean addAccount(String accNum, double bal, int cId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("accountNumber", accNum);
        cv.put("balance", bal);
        cv.put("customerId", cId);
        
        long res = db.insert("accounts", null, cv);
        return res != -1;
    }

    public List<BankAccount> getAllAccounts() {
        List<BankAccount> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM accounts", null);
        if (cursor.moveToFirst()) {
            do {
                BankAccount a = new BankAccount();
                a.setId(cursor.getInt(0));
                a.setAccountNumber(cursor.getString(1));
                a.setBalance(cursor.getDouble(2));
                a.setCustomerId(cursor.getInt(3));
                list.add(a);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void deleteAccount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("accounts", "_id = " + id, null);
    }

    // JOIN Query for showing account with customer name
    public Cursor getAccountsWithNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT accounts._id, accounts.accountNumber, accounts.balance, customers.fullName " +
                     "FROM accounts INNER JOIN customers ON accounts.customerId = customers._id";
        return db.rawQuery(sql, null);
    }

    // --- TRANSACTION METHODS ---

    public boolean processTransaction(int accId, String type, double amt, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put("accountId", accId);
            cv.put("type", type);
            cv.put("amount", amt);
            cv.put("transDate", date);
            db.insert("transactions", null, cv);

            // Update balance logic
            String updateSql;
            if (type.equals("Deposit")) {
                updateSql = "UPDATE accounts SET balance = balance + " + amt + " WHERE _id = " + accId;
            } else {
                updateSql = "UPDATE accounts SET balance = balance - " + amt + " WHERE _id = " + accId;
            }
            db.execSQL(updateSql);
            
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.endTransaction();
        }
    }
}
