package com.example.a26021_midterm_bank;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvBankName;
    private Button btnManageCustomers, btnManageAccounts, btnManageTransactions, btnSettings;
    private NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBankName = findViewById(R.id.tvBankName);
        btnManageCustomers = findViewById(R.id.btnManageCustomers);
        btnManageAccounts = findViewById(R.id.btnManageAccounts);
        btnManageTransactions = findViewById(R.id.btnManageTransactions);
        btnSettings = findViewById(R.id.btnSettings);

        loadSettings();

        btnManageCustomers.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CustomerActivity.class)));
        btnManageAccounts.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AccountActivity.class)));
        btnManageTransactions.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TransactionActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        networkReceiver = new NetworkReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences("BankPrefs", MODE_PRIVATE);
        String bankName = prefs.getString("bankName", "My Bank");
        tvBankName.setText(bankName);
    }
}
