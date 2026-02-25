package com.example.a26021_midterm_bank.models;

public class BankAccount {
    private int id;
    private String accountNumber;
    private double balance;
    private int customerId; // Foreign Key

    public BankAccount() {}

    public BankAccount(int id, String accountNumber, double balance, int customerId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    @Override
    public String toString() {
        return accountNumber + " (Bal: " + balance + ")";
    }
}
