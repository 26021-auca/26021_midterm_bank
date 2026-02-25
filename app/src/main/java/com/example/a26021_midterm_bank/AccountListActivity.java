package com.example.a26021_midterm_bank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a26021_midterm_bank.adapters.AccountAdapter;
import com.example.a26021_midterm_bank.database.DatabaseHelper;

public class AccountListActivity extends AppCompatActivity {

    ListView lvAccounts;
    DatabaseHelper db;
    AccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        lvAccounts = findViewById(R.id.lvAccounts);
        db = new DatabaseHelper(this);

        showAllAccounts();

        // Delete account when user holds the item
        lvAccounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // The 'id' parameter here is the _id from the database because we use CursorAdapter
                final int accountId = (int) id;

                AlertDialog.Builder builder = new AlertDialog.Builder(AccountListActivity.this);
                builder.setTitle("Delete Confirmation");
                builder.setMessage("Are you sure you want to delete this account?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteAccount(accountId);
                        Toast.makeText(AccountListActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                        showAllAccounts(); // refresh
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();

                return true;
            }
        });
    }

    private void showAllAccounts() {
        Cursor cursor = db.getAccountsWithNames();
        adapter = new AccountAdapter(this, cursor, false);
        lvAccounts.setAdapter(adapter);
    }
}
