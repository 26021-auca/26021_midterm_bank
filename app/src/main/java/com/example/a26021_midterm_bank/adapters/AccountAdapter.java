package com.example.a26021_midterm_bank.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.a26021_midterm_bank.R;
import com.example.a26021_midterm_bank.database.DatabaseHelper;

public class AccountAdapter extends CursorAdapter {

    public AccountAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_account, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvNumber = view.findViewById(R.id.tvAccNumber);
        TextView tvDetails = view.findViewById(R.id.tvAccDetails);

        String accNum = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ACC_NUMBER));
        double balance = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ACC_BALANCE));
        String custName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CUST_NAME));

        tvNumber.setText("Acc: " + accNum);
        tvDetails.setText("Owner: " + custName + " | Balance: $" + balance);
    }
}
