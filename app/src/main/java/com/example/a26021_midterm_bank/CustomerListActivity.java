package com.example.a26021_midterm_bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a26021_midterm_bank.adapters.CustomerAdapter;
import com.example.a26021_midterm_bank.database.DatabaseHelper;
import com.example.a26021_midterm_bank.models.Customer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {

    ListView lv;
    Button btnExport;
    DatabaseHelper db;
    List<Customer> list;
    CustomerAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        lv = findViewById(R.id.lvCustomers);
        btnExport = findViewById(R.id.btnExportCSV);
        db = new DatabaseHelper(this);

        refreshList();

        // Delete customer on long click
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Customer selected = list.get(position);
                
                new AlertDialog.Builder(CustomerListActivity.this)
                    .setTitle("Delete Customer")
                    .setMessage("Do you want to delete " + selected.getFullName() + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteCustomer(selected.getId());
                            Toast.makeText(CustomerListActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                            refreshList();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
                
                return true;
            }
        });

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToCSV();
            }
        });
    }

    private void refreshList() {
        list = db.getCustomers();
        adp = new CustomerAdapter(this, list);
        lv.setAdapter(adp);
    }

    private void saveToCSV() {
        if (list.isEmpty()) {
            Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        File path = getExternalFilesDir(null);
        File file = new File(path, "customers_list.csv");

        try {
            FileWriter writer = new FileWriter(file);
            writer.append("ID,Name,Phone,Email\n");
            for (Customer c : list) {
                writer.append(c.getId() + "," + c.getFullName() + "," + c.getPhone() + "," + c.getEmail() + "\n");
            }
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
