package com.example.a26021_midterm_bank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    // UI elements
    EditText etBank, etCurr;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Link with XML
        etBank = findViewById(R.id.etBankName);
        etCurr = findViewById(R.id.etCurrency);
        btnSave = findViewById(R.id.btnSaveSettings);

        // Get shared preferences to load saved data
        final SharedPreferences myPrefs = getSharedPreferences("BankSettings", MODE_PRIVATE);
        etBank.setText(myPrefs.getString("bankName", "Default Bank"));
        etCurr.setText(myPrefs.getString("currency", "USD"));

        // Save button listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etBank.getText().toString();
                String currency = etCurr.getText().toString();

                // Save to SharedPreferences
                SharedPreferences.Editor myEditor = myPrefs.edit();
                myEditor.putString("bankName", name);
                myEditor.putString("currency", currency);
                myEditor.apply();

                Toast.makeText(SettingsActivity.this, "Settings Saved!", Toast.LENGTH_SHORT).show();
                finish(); // Close activity
            }
        });
    }
}
