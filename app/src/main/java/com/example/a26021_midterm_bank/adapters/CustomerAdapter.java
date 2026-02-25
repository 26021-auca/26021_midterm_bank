package com.example.a26021_midterm_bank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a26021_midterm_bank.R;
import com.example.a26021_midterm_bank.models.Customer;

import java.util.List;

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private List<Customer> customers;

    public CustomerAdapter(Context context, List<Customer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Object getItem(int position) {
        return customers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return customers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        }

        Customer customer = (Customer) getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvCustName);
        TextView tvDetails = convertView.findViewById(R.id.tvCustDetails);

        tvName.setText(customer.getFullName());
        tvDetails.setText(customer.getPhone() + " | " + customer.getEmail());

        return convertView;
    }
}
