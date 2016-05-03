package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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


public class Beneficary_List_Fragment extends Fragment {

    ListView bList;

    ListView mList;

    JSONArray responseArray;



    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    LinearLayout blistlayout;

    public final static String EXTRA_DATA = "com.example.jmtransfers.jmtransfer.EXTRA_DATA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beneficary__list_, container, false);

        blistlayout = (LinearLayout) rootView.findViewById(R.id.blistlayout);

        bList = (ListView) rootView.findViewById(R.id.listView);

        // bList = (ListView) rootView.findViewById(R.id.listView);

        setListViewHeightBasedOnChildren(bList);

        bList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                v.getParent().requestDisallowInterceptTouchEvent(true);

                return false;

            }
        });


        bList.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getActivity(), Confirm_Beneficary.class);

                try {


                    intent.putExtra(EXTRA_DATA, responseArray.get(position).toString());

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                startActivity(intent);



            }


        });






        settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

        editor = settings.edit();

        editor.commit();

        Bundle args = getArguments();

        StringBuilder builder = new StringBuilder();

        String WEB_SERVICE_URL = builder.append("http://jmtrax.net/app_php_files/blist.php?username=").append(settings.getString("Username","")).toString();

        WebAsyncTask Task = new WebAsyncTask();

        Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), this, "BENLIST");

        return rootView;



    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_cart){



            Intent intent = new Intent(getActivity(), Confirm_Beneficary.class);

            intent.putExtra(EXTRA_DATA, "");

            startActivity(intent);





            return true;
        }

        return super.onOptionsItemSelected(item);

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

    private class WebAsyncTask extends AsyncTask<Object, Void, JSONArray> {

        JSONArray jsonArray;

        String response = null;

        Object classObject;

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




                responseArray = (JSONArray) result.get(0);

                JSONObject f = (JSONObject) responseArray.get(0);

                if(f.getString("ERROR").length() == 0)

                {
                    bList.setAdapter(new CustomBlistAdapter(getActivity().getBaseContext(),responseArray));

                }
                else
                {

                    Snackbar snackbar = Snackbar
                            .make(blistlayout, "Get started by adding a beneficiary", Snackbar.LENGTH_LONG)
                            .setAction("ADD", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                   // Dashboard dashActivity = (Dashboard) getActivity();


                                   // dashActivity.takeactionSearch(5,"");



                                }
                            });

                    snackbar.setActionTextColor(Color.GREEN);


                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                }




            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }


}
