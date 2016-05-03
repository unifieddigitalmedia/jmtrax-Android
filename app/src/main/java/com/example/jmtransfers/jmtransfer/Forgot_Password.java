package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Forgot_Password extends AppCompatActivity {

    private NestedScrollView nestedScroll;

    SharedPreferences.Editor editor;

    SharedPreferences settings;

    public static final String PREFS_NAME = "JM-Transfers";

    TabLayout tabLayout;

    FrameLayout mframeLayout;

    ListView bList;



    String WEB_SERVICE_URL ;


    EditText emailplaceholder ;

    TextView errortextView ;

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

            @Override
            public void onClick(View v) {

                settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                editor = settings.edit();

                editor.putString("Username", "");


                editor.commit();


                Intent intent = new Intent(getBaseContext(), Dashboard.class);


                startActivity(intent);
            }
        });



        getSupportActionBar().setDisplayShowTitleEnabled(false);



        nestedScroll = (NestedScrollView) findViewById(R.id.mainscroll);

        nestedScroll.setFillViewport(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#007f12"));

        tabLayout.addTab(tabLayout.newTab().setText("SEND MONEY"), 0, true);

        tabLayout.addTab(tabLayout.newTab().setText("ACCOUNT"), 1, false);

        tabLayout.addTab(tabLayout.newTab().setText("HELP"), 2, false);

        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mframeLayout = (FrameLayout) findViewById(R.id.content_frame);

        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new Forgot_Password_Fragment()).commit();

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

    private class WebAsyncTask extends AsyncTask<Object, Void, JSONArray> {

        JSONArray jsonArray;

        String response = null;

        Object classObject;

        Confirm_Transfer_Fragment calculatorActivity;

        @Override
        protected JSONArray doInBackground(Object... params) {

            try

            {

                URL actualapiURL = new URL((String) params[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) actualapiURL.openConnection();

                httpURLConnection.setRequestMethod("GET");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                InputStream inputStream = httpURLConnection.getInputStream();

                response = streamToString(inputStream);

                jsonArray = (JSONArray) new JSONTokener(response).nextValue();

                jsonArray.put(params[1]);

                jsonArray.put(params[2]);

            }

            catch (Exception e)

            {

                e.printStackTrace();

            }



            return jsonArray;





        }

        private String streamToString(InputStream inputStream) throws IOException {

            String string = "";

            if (inputStream!= null) {

                StringBuilder stringBuilder = new StringBuilder();

                String line;

                try {

                    BufferedReader reader = new BufferedReader(

                            new InputStreamReader(inputStream));

                    while ((line = reader.readLine()) != null) {

                        stringBuilder.append(line);

                    }

                    reader.close();

                } finally {

                    inputStream.close();

                }

                string = stringBuilder.toString();
            }

            return string;
        }



        protected void onPostExecute(JSONArray result) {


            try {




                if(result.get(2).toString() == "FORGOT")

                {

                    JSONObject responseArray = (JSONObject) result.get(0);


                    if(responseArray.getString("ERROR").length() == 0 )

                    {




                    }else { errortextView.setText(responseArray.getString("ERROR")); }


                }




            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }


}
