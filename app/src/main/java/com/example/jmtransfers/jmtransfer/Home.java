package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


public class Home extends Fragment {


    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    private ListView homeList;

    private String[] homeArrayPlaceholders;

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);


        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.pager);

        mViewPager.setAdapter(mDemoCollectionPagerAdapter);

        Button next = (Button) v.findViewById(R.id.started);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

                editor = settings.edit();


                String asd = settings.getString("Username", "");




                if(asd.length() == 0 )

                {


                    Intent intent = new Intent(getActivity(), Account.class);

                    startActivity(intent);


                }
                else
                {


                    Intent intent = new Intent(getActivity(), Dashboard_Fragment.class);

                    startActivity(intent);


                }


            }


        });
        return v;


    }


}