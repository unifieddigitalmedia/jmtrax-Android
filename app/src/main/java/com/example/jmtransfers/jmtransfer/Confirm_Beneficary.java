package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.TextView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Confirm_Beneficary extends AppCompatActivity {

    private NestedScrollView nestedScroll;      SharedPreferences.Editor editor;      SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    TabLayout tabLayout;


    FrameLayout mframeLayout;

    ListView bList;

String args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);       getSupportActionBar().setDisplayHomeAsUpEnabled(false);          getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        getSupportActionBar().setCustomView(R.layout.action_bar);


        TextView tab = (TextView) findViewById(R.id.mytext);


        tab.setOnClickListener(new View.OnClickListener() {


            @Override             public void onClick(View v) {


                settings =  getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                editor = settings.edit();

                editor.putString("Username", "");

                editor.commit();

                Intent intent = new Intent(getBaseContext(),Dashboard.class);


                startActivity(intent);                }         });

        setSupportActionBar(toolbar);       getSupportActionBar().setDisplayHomeAsUpEnabled(false);          getSupportActionBar().setDisplayShowTitleEnabled(false);

        nestedScroll = (NestedScrollView) findViewById(R.id.mainscroll);

        nestedScroll.setFillViewport(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#007f12"));

        tabLayout.addTab(tabLayout.newTab().setText("SEND MONEY"), 0, true);

        tabLayout.addTab(tabLayout.newTab().setText("ACCOUNT"), 1, false);

        tabLayout.addTab(tabLayout.newTab().setText("HELP"), 2, false);


        mframeLayout = (FrameLayout) findViewById(R.id.content_frame);

        Intent intent = getIntent();

        args = intent.getStringExtra(Beneficary_List_Fragment.EXTRA_DATA);

        Confirm_Beneficary_Fragment payFragment = new Confirm_Beneficary_Fragment();


        Bundle arg = new Bundle();

        arg.putString("ARG_STRING", args);


        payFragment.setArguments(arg);


        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, payFragment).commit();

        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener());

        bList = (ListView) findViewById(R.id.welcomelist);


        bList.setAdapter(new CustomAdapter(this, getResources().getStringArray(R.array.home_menu_array)));


        setListViewHeightBasedOnChildren(bList);

        bList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        bList.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0)


                {


                    Intent intent = new Intent(getBaseContext(), MoneyTransferCalculator.class);

                    startActivity(intent);


                } else if (position == 1)


                {


                    settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                    editor = settings.edit();


                    String asd = settings.getString("Username", "");




                    if(asd.length() == 0 )

                    {



                        Intent intent = new Intent(getBaseContext(), Account.class);

                        startActivity(intent);


                    }
                    else
                    {


                        Intent intent = new Intent(getBaseContext(), Dashboard_Fragment.class);

                        startActivity(intent);

                    }
                    Intent intent = new Intent(getBaseContext(), Account.class);

                    startActivity(intent);

                } else if (position == 2)


                {

                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("http://www.jmtrax.net/"));
                    startActivity(viewIntent);


                } else if (position == 3)


                {

                    Intent viewintent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("http://www.jmtrax.com/money-laundering-statement/"));
                    startActivity(viewintent);


                } else if (position == 4)


                {


                    Intent intent = new Intent(getBaseContext(), Help.class);

                    startActivity(intent);

                }


            }


        });



    }




    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);

        int totalHeight = 0;

        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {

            view = listAdapter.getView(i, view, listView);

            if (i == 0)

                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.MATCH_PARENT));


            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += view.getMeasuredHeight() + 20;

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);

    }


    private class OnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {


            int pos = tab.getPosition();



            if ( tab.getPosition() == 0 )


            {


                Intent intent = new Intent(getBaseContext(), MoneyTransferCalculator.class);

                startActivity(intent);



            }
            else if ( tab.getPosition() == 1 )


            {


                settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                editor = settings.edit();


                String asd = settings.getString("Username", "");

                Log.i("name",asd);

                Log.i("name",String.valueOf(asd.length() == 0));



                if(asd.length() == 0 )

                {



                    Intent intent = new Intent(getBaseContext(), Account.class);

                    startActivity(intent);


                }
                else
                {


                    Intent intent = new Intent(getBaseContext(), Dashboard_Fragment.class);

                    startActivity(intent);

                }

            }

            else if ( tab.getPosition() == 2 )


            {



                Intent intent = new Intent(getBaseContext(), Help.class);

                startActivity(intent);



            }







        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {



        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {



            int pos = tab.getPosition();



            if ( tab.getPosition() == 0 )


            {


                Intent intent = new Intent(getBaseContext(), MoneyTransferCalculator.class);

                startActivity(intent);



            }
            else if ( tab.getPosition() == 1 )


            {


                settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                editor = settings.edit();

                String asd = settings.getString("Username", "");


                if(asd.length() == 0 )

                {



                    Intent intent = new Intent(getBaseContext(), Account.class);

                    startActivity(intent);


                }
                else
                {


                    Intent intent = new Intent(getBaseContext(), Dashboard_Fragment.class);

                    startActivity(intent);

                }



            }

            else if ( tab.getPosition() == 2 )


            {



                Intent intent = new Intent(getBaseContext(), Help.class);

                startActivity(intent);



            }





        }
    }


}
