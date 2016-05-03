package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Dashboard_Fragment_Home extends Fragment {

    private FragmentTabHost mTabHost;

    private android.app.ActionBar mAction;


    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    TextView welcome ;
    TextView customerID ;
    TextView balance ;
    TextView status ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dashboard__fragment__home, container, false);


        mTabHost = (FragmentTabHost) v.findViewById(android.R.id.tabhost);

        mTabHost.setup(getContext(), getFragmentManager(), android.R.id.tabcontent);

        Bundle tab1args = new Bundle();

        tab1args.putString("Username", "macslack");

        // mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Beneficiaries"), BeneficiariesTabFragment.class, tab1args);

        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("My Transactions"), TransfersTabFragment.class, tab1args);


        settings = getActivity().getSharedPreferences(PREFS_NAME, getActivity().getBaseContext().MODE_PRIVATE);

        editor = settings.edit();


        welcome = (TextView) v.findViewById(R.id.welcome);

        customerID = (TextView) v.findViewById(R.id.status);

        balance = (TextView) v.findViewById(R.id.limit);

        welcome.setText("Welcome ".concat(settings.getString("Name", "")));

        customerID.setText("ID status ".concat(settings.getString("Status", "")));

        balance.setText("Remaining online limit allowed £".concat(settings.getString("Balance", "")));


        Button next = (Button) v.findViewById(R.id.started);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {




                Intent intent = new Intent(getActivity(), Beneficary_List.class);

                startActivity(intent);


            }


        });


        return v;


    }



}
