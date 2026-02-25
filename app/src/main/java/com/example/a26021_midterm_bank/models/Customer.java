package com.example.a26021_midterm_bank.models;

public class Customer {
    private int id;
    private String fullName;
    private String phone;
    private String email;
    private String gender;

    public Customer() {}

    public Customer(int id, String fullName, String phone, String email, String gender) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() {
        return fullName;
    }
}
