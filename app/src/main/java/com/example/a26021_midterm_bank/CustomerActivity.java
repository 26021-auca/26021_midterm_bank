package com.example.a26021_midterm_bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a26021_midterm_bank.database.DatabaseHelper;

public class CustomerActivity extends AppCompatActivity {

    // Declare UI components
    EditText etName, etPhone, etEmail;
    RadioGroup rgGender;
    CheckBox cbTerms;
    Button btnSave, btnView;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // Initialize database helper
        myDb = new DatabaseHelper(this);

        // Link components to XML IDs
        etName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        rgGender = findViewById(R.id.rgGender);
        cbTerms = findViewById(R.id.cbTerms);
        btnSave = findViewById(R.id.btnSaveCustomer);
        btnView = findViewById(R.id.btnViewCustomers);

        // Save button listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from inputs
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                
                // Simple validation
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(CustomerActivity.this, "Fill all boxes!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.contains("@")) {
                    Toast.makeText(CustomerActivity.this, "Email must have @", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!cbTerms.isChecked()) {
                    Toast.makeText(CustomerActivity.this, "Agree to terms first", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get selected gender
                int id = rgGender.getCheckedRadioButtonId();
                RadioButton rb = findViewById(id);
                String gender = rb.getText().toString();

                // Call database method
                boolean success = myDb.addCustomer(name, phone, email, gender);
                if (success) {
                    Toast.makeText(CustomerActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(CustomerActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // View button listener
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerActivity.this, CustomerListActivity.class);
                startActivity(i);
            }
        });
    }

    private void clearFields() {
        etName.setText("");
        etPhone.setText("");
        etEmail.setText("");
        cbTerms.setChecked(false);
    }
}
