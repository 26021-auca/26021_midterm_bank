package com.example.a26021_midterm_bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a26021_midterm_bank.database.DatabaseHelper;
import com.example.a26021_midterm_bank.models.Customer;

import java.util.List;

public class AccountActivity extends AppCompatActivity {

    EditText etAccNumber, etAccBalance;
    Spinner spCustomerList;
    Button btnSave, btnView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        db = new DatabaseHelper(this);

        etAccNumber = findViewById(R.id.etAccountNumber);
        etAccBalance = findViewById(R.id.etInitialBalance);
        spCustomerList = findViewById(R.id.spCustomers);
        btnSave = findViewById(R.id.btnSaveAccount);
        btnView = findViewById(R.id.btnViewAccounts);

        loadCustomersInSpinner();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validation for empty inputs
                if (etAccNumber.getText().toString().isEmpty() || etAccBalance.getText().toString().isEmpty()) {
                    Toast.makeText(AccountActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                String num = etAccNumber.getText().toString();
                double balance = Double.parseDouble(etAccBalance.getText().toString());

                // Validation for negative balance
                if (balance < 0) {
                    Toast.makeText(AccountActivity.this, "Balance cannot be less than 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                Customer c = (Customer) spCustomerList.getSelectedItem();
                if (c == null) {
                    Toast.makeText(AccountActivity.this, "First add a customer!", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean ok = db.addAccount(num, balance, c.getId());
                if (ok) {
                    Toast.makeText(AccountActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AccountActivity.this, "Error! Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, AccountListActivity.class));
            }
        });
    }

    private void loadCustomersInSpinner() {
        List<Customer> customers = db.getCustomers();
        ArrayAdapter<Customer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomerList.setAdapter(adapter);
    }
}
