package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class Sign_up_address extends Fragment {

    String senders_ID;

    EditText firstnameplaceholder ;

    EditText lastnameplaceholder ;

    EditText phoneplaceholder ;

    public final static String TAB_POSITION = "";

    EditText usernameplaceholder ;

    EditText passwordplaceholder ;

    EditText cpasswordplaceholder ;

    String WEB_SERVICE_URL ;

    EditText emailplaceholder ;

    TextView errortextView;


    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_sign_up_address, container, false);



        firstnameplaceholder = (EditText) v.findViewById(R.id.passwordplaceholder);
        lastnameplaceholder = (EditText) v.findViewById(R.id.cpasswordplaceholder);
        phoneplaceholder = (EditText) v.findViewById(R.id.townplaceholder);
        usernameplaceholder = (EditText) v.findViewById(R.id.countyplaceholder);
        passwordplaceholder = (EditText) v.findViewById(R.id.postcodeplaceholder);
        errortextView = (TextView) v.findViewById(R.id.errortextView);

        Bundle args = getArguments();


        senders_ID = args.getString("sendersID");


        Button next = (Button) v.findViewById(R.id.confirm);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (firstnameplaceholder.getText().toString().length() == 0 ||
                        lastnameplaceholder.getText().toString().length() == 0 ||
                        phoneplaceholder.getText().toString().length() == 0 ||
                        usernameplaceholder.getText().toString().length() == 0 ||
                        passwordplaceholder.getText().toString().length() == 0

                        )

                {

                    errortextView.setText("All fields need to be filled out");


                } else if (passwordplaceholder.getText().toString().length() < 6)

                {


                    errortextView.setText("Your postcode must be at least 6 characters in length");


                } else

                {





                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/signup_address.php?")
                            .append("&firstline=").append(firstnameplaceholder.getText().toString())
                            .append("&secondline=").append(lastnameplaceholder.getText().toString())
                            .append("&town=").append(phoneplaceholder.getText().toString())
                            .append("&county=").append(usernameplaceholder.getText().toString())
                            .append("&postcode=").append(passwordplaceholder.getText().toString())
                            .append("&sender_ID=").append(senders_ID).toString();

                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), this, "SIGN_ADDRESS");


                }


            }


        });

        return v;




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



                JSONObject responseArray = (JSONObject) result.get(0);



                if(responseArray.getString("ERROR").length() == 0 )

                {

                                       settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

                    editor = settings.edit();

                    editor.putString("Username",responseArray.getString("username"));
                    editor.putString("Status","Verified");
                    editor.putString("Balance","800.00");

                    editor.commit();

                    Intent intent = new Intent(getContext(), Dashboard_Fragment.class);

                    intent.putExtra(TAB_POSITION, 1);



                    startActivity(intent);



                }else { errortextView.setText(responseArray.getString("ERROR")); }





            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }

}
