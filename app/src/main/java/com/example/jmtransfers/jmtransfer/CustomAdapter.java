package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by UnitedDigitalLondon on 21/12/15.
 */
public class CustomAdapter extends BaseAdapter {

    private static String[] searchBrandArrayList;

    private LayoutInflater mInflater;

    private Integer[] Right = {

            R.drawable.ic_keyboard_arrow_right_black_48dp
    };

    public CustomAdapter (Context context, String[] results) {


        searchBrandArrayList = results;

        mInflater = LayoutInflater.from(context);



    }


    @Override
    public int getCount() {
        return searchBrandArrayList.length;
    }

    @Override
    public Object getItem(int position) {

        Object brand = null;



        brand = searchBrandArrayList[position];


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

            convertView = mInflater.inflate(R.layout.activity_main_section_rows, null);

            holder = new ViewHolder();

            holder.txtSector = (TextView) convertView.findViewById(R.id.name);

            holder.right = (ImageView) convertView.findViewById(R.id.right);


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }



        holder.txtSector.setText(searchBrandArrayList[position]);


        holder.right.setImageResource(Right[0]);


        return convertView;



    }


    static class ViewHolder {

        TextView txtSector;

        ImageView right;

    }



}
