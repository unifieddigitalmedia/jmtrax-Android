package com.example.jmtransfers.jmtransfer; import android.content.Intent;
import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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


public class TransferCalulator extends Fragment {

    EditText firstnameplaceholder ;

    EditText lastnameplaceholder ;

    EditText phoneplaceholder ;


    String WEB_SERVICE_URL ;


    EditText usernameplaceholder ;


    TextView amountsent ;
    TextView fee ;
    TextView transferpaid ;
    TextView transfertotal ;

    TextView errortextView;
    LinearLayout welcomelayout;



    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_transfer_calulator, container, false);


        errortextView = (TextView) v.findViewById(R.id.errortextView);

        amountsent = (TextView) v.findViewById(R.id.amountsent);

        fee = (TextView) v.findViewById(R.id.fee);

        transferpaid = (TextView) v.findViewById(R.id.transferpaid);

        transfertotal = (TextView) v.findViewById(R.id.transfertotal);

        firstnameplaceholder = (EditText) v.findViewById(R.id.firstnameplaceholder);

        firstnameplaceholder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {



                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ) {




                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/gbp.php?")
                            .append("&amount=").append(firstnameplaceholder.getText().toString()).toString();


                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL, this, "MONEYCALCULATOR");






                }

                return false;

            }
        });

        lastnameplaceholder = (EditText) v.findViewById(R.id.lastnameplaceholder);




        phoneplaceholder = (EditText) v.findViewById(R.id.phoneplaceholder);


        phoneplaceholder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ) {

                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/ngn.php?")
                            .append("&amount=").append(phoneplaceholder.getText().toString()).toString();


                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL, this, "MONEYCALCULATOR");
                }
                return false;
            }
        });

        usernameplaceholder = (EditText) v.findViewById(R.id.usernameplaceholder);


        usernameplaceholder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT ) {
                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/gbpdue.php?")
                            .append("&amount=").append(usernameplaceholder.getText().toString()).toString();


                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL, this, "MONEYCALCULATOR");
                }
                return false;
            }
        });


        Button cancel = (Button) v.findViewById(R.id.login);

        cancel.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

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


                JSONObject responseArray = (JSONObject) result.get(0);


                if(responseArray.getString("ERROR").length() == 0 )

                {


                    firstnameplaceholder.setText(responseArray.get("GBP").toString());

                    lastnameplaceholder.setText(responseArray.get("COMM").toString());

                    phoneplaceholder.setText(responseArray.get("NGN").toString());

                    usernameplaceholder.setText(responseArray.get("DUE").toString());

                    amountsent.setText(responseArray.get("GBP").toString().concat(" GBP"));

                    fee.setText(responseArray.get("COMM").toString().concat(" GBP"));

                    transferpaid.setText(responseArray.get("NGN").toString().concat(" NGN"));

                    transfertotal.setText(responseArray.get("DUE").toString().concat(" GBP"));


                }else { errortextView.setText(responseArray.getString("ERROR")); }



            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }


}
