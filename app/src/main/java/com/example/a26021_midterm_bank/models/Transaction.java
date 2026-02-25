package com.example.a26021_midterm_bank.models;

public class Transaction {
    private int id;
    private int accountId; // Foreign Key
    private String type; // Deposit or Withdraw
    private double amount;
    private String transactionDate;

    public Transaction() {}

    public Transaction(int id, int accountId, String type, double amount, String transactionDate) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }
}
