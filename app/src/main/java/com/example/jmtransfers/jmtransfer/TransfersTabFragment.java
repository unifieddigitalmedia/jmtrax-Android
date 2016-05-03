
package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

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


public class TransfersTabFragment extends Fragment {

    ListView bList;

    JSONArray responseArray;

    public static final String PREFS_NAME = "JM-Transfers";

    SharedPreferences.Editor editor;


    SharedPreferences settings;


    public final static String EXTRA_DATA = "com.example.jmtransfers.jmtransfer.EXTRA_DATA";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_transfers_tab, container, false);

        bList = (ListView) rootView.findViewById(R.id.listView);

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

        Bundle args = getArguments();

        StringBuilder builder = new StringBuilder();


        bList = (ListView) rootView.findViewById(R.id.listView);

        bList.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Intent intent = new Intent(getActivity(),Transfer_Receipt_Activity.class);

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

        String WEB_SERVICE_URL = builder.append("http://jmtrax.net/app_php_files/transfers.php?username=")
                .append(settings.getString("Username","")).toString();



        WebAsyncTask Task = new WebAsyncTask();

        Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"),TransfersTabFragment.this,"TRANSFERS");

        return rootView;


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
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
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

                bList.setAdapter(new CustomTransfersAdapter(getActivity().getBaseContext(), responseArray));



            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }




}
