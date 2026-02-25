package com.example.a26021_midterm_bank;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a26021_midterm_bank.database.DatabaseHelper;
import com.example.a26021_midterm_bank.models.BankAccount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {

    Spinner spAccs, spTransType;
    EditText etAmountInput;
    Button btnGo;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        helper = new DatabaseHelper(this);

        spAccs = findViewById(R.id.spAccounts);
        spTransType = findViewById(R.id.spType);
        etAmountInput = findViewById(R.id.etAmount);
        btnGo = findViewById(R.id.btnSaveTransaction);

        fillAccounts();

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if amount is entered
                if (etAmountInput.getText().toString().equals("")) {
                    Toast.makeText(TransactionActivity.this, "Enter an amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                double money = Double.parseDouble(etAmountInput.getText().toString());
                BankAccount selectedAcc = (BankAccount) spAccs.getSelectedItem();
                String typeOfTransaction = spTransType.getSelectedItem().toString();

                // Check for balance if withdrawing
                if (typeOfTransaction.equals("Withdraw")) {
                    if (selectedAcc.getBalance() < money) {
                        Toast.makeText(TransactionActivity.this, "No enough money!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Get current date
                String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Save to database
                boolean done = helper.processTransaction(selectedAcc.getId(), typeOfTransaction, money, todayDate);
                
                if (done) {
                    Toast.makeText(TransactionActivity.this, "Transaction Done!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TransactionActivity.this, "Error in transaction", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fillAccounts() {
        List<BankAccount> allAccounts = helper.getAllAccounts();
        ArrayAdapter<BankAccount> adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allAccounts);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAccs.setAdapter(adp);
    }
}
