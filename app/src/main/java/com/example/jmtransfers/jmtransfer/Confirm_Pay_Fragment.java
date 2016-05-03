package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Confirm_Pay_Fragment extends Fragment {


    public final static String EXTRA_DATA = "com.example.jmtransfers.jmtransfer.EXTRA_DATA";

    ListView bList;

    JSONObject newJArray;

    ListView bList1;

    JSONArray responseArray;


    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    LinearLayout blistlayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_confirm__pay_, container, false);

        blistlayout = (LinearLayout) rootView.findViewById(R.id.blistlayout);

        bList = (ListView) rootView.findViewById(R.id.listView);

        String[] values = new String[] { "Bank" };

        bList.setAdapter(new CustomPayAdapter(getActivity().getBaseContext(), values));

        Bundle args = getArguments();

        try {

            newJArray = new JSONObject(args.getString("ARG_STRING"));


        } catch (JSONException e) {

            e.printStackTrace();

        }
        bList.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                try {

                    newJArray.put("pay",bList.getItemAtPosition(position));

                    Intent intent = new Intent(getActivity(),Receipt.class);

                    intent.putExtra(EXTRA_DATA, newJArray.toString());

                    startActivity(intent);



                } catch (JSONException e) {


                    e.printStackTrace();

                }


            }


        });





        return  rootView;
    }

}
