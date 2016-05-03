package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by UnitedDigitalLondon on 17/11/15.
 */
public class CustomTransfersAdapter extends BaseAdapter {


    private static JSONArray searchBrandArrayList;

    private LayoutInflater mInflater;


    private Integer[] Right = {

            R.drawable.ic_keyboard_arrow_right_black_48dp
    };

    public CustomTransfersAdapter (Context context, JSONArray results) {


        searchBrandArrayList = results;

        mInflater = LayoutInflater.from(context);


    }


    @Override
    public int getCount() {
        return searchBrandArrayList.length();
    }

    @Override
    public Object getItem(int position) {

        Object brand = null;

        try {
            brand = searchBrandArrayList.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return brand;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.bactivity_main_section_rows, null);

            holder = new ViewHolder();

            holder.txtName = (TextView) convertView.findViewById(R.id.name);

            holder.txtAccount = (TextView) convertView.findViewById(R.id.account);

            holder.txtBank = (TextView) convertView.findViewById(R.id.bank);

            holder.right = (ImageView) convertView.findViewById(R.id.right);


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        try {


            JSONObject f = (JSONObject) searchBrandArrayList.get(position);

            holder.txtName.setText(("JM").concat(f.getString("order_ID")));

            holder.txtAccount.setText(f.getString("RecipientsFirstName").concat(" ").concat(f.getString("RecipientsLastName")).concat("-").concat(" ").concat("NGN").concat(f.getString("TotalDueNGN")));

            holder.txtBank.setText(f.getString("Date"));

            holder.right.setImageResource(Right[0]);



        } catch (JSONException e) {

            e.printStackTrace();


        }

        return convertView;



    }


    static class ViewHolder {

        TextView txtName;
        TextView txtAccount;
        TextView txtBank;
        ImageView right;


    }



}
