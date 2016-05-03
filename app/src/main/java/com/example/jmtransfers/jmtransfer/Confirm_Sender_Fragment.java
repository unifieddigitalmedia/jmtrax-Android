package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

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


public class Confirm_Sender_Fragment extends Fragment {


    public final static String EXTRA_DATA = "com.example.jmtransfers.jmtransfer.EXTRA_DATA";


    public static String ARG_STRING;


    EditText firstnameplaceholder;

    EditText lastnameplaceholder;

    EditText phoneplaceholder;


    EditText usernameplaceholder;

    EditText passwordplaceholder;

    EditText cpasswordplaceholder;


    EditText townplaceholder;

    EditText countyplaceholder;

    EditText postcodeplaceholder;

    EditText emailplaceholder;


    JSONObject responseObject;

    JSONArray responseArray;

    TextView errortextView;



    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    String WEB_SERVICE_URL = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_confirm__sender_, container, false);

        firstnameplaceholder = (EditText) v.findViewById(R.id.firstnameplaceholder);

        lastnameplaceholder = (EditText) v.findViewById(R.id.lastnameplaceholder);

        phoneplaceholder = (EditText) v.findViewById(R.id.phoneplaceholder);

        emailplaceholder = (EditText) v.findViewById(R.id.emailplaceholder);

        townplaceholder = (EditText) v.findViewById(R.id.townplaceholder);

        postcodeplaceholder = (EditText) v.findViewById(R.id.postcodeplaceholder);

        passwordplaceholder = (EditText) v.findViewById(R.id.passwordplaceholder);

        cpasswordplaceholder = (EditText) v.findViewById(R.id.cpasswordplaceholder);

        countyplaceholder = (EditText) v.findViewById(R.id.countyplaceholder);

        errortextView = (TextView) v.findViewById(R.id.errortextView);

        Bundle args = getArguments();


        try {

            responseObject = new JSONObject(args.getString("ARG_STRING"));


        } catch (JSONException e) {

            e.printStackTrace();

        }


        settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

        editor = settings.edit();

        editor.commit();


        StringBuilder builder = new StringBuilder();

        WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/settings.php?")
                .append("username=").append(settings.getString("Username","")).toString();


        WebAsyncTask Task = new WebAsyncTask();

        Task.execute(WEB_SERVICE_URL,Confirm_Sender_Fragment.this,"SETUPS");



        Button cancel = (Button) v.findViewById(R.id.confirm);

        cancel.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (firstnameplaceholder.getText().toString().length() == 0
                        || lastnameplaceholder.getText().toString().length() == 0
                        || phoneplaceholder.getText().toString().length() == 0
                        || emailplaceholder.getText().toString().length() == 0
                        || townplaceholder.getText().toString().length() == 0
                        || postcodeplaceholder.getText().toString().length() == 0
                        || passwordplaceholder.getText().toString().length() == 0
                        || cpasswordplaceholder.getText().toString().length() == 0
                        || countyplaceholder.getText().toString().length() == 0)

                {

                    errortextView.setText("All fields need to be filled out");


                } else if (phoneplaceholder.getText().toString().length() < 10 || phoneplaceholder.getText().toString().length() > 10)

                {


                    errortextView.setText("Phone must be UK format withou the zero");


                } else if (emailplaceholder.getText().toString().length() < 3)

                {

                    errortextView.setText("Please check your email");


                } else

                {


                    settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

                    editor = settings.edit();

                    editor.commit();


                    StringBuilder builder = new StringBuilder();



                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/updateSender.php?username=")
                            .append(settings.getString("Username",""))
                            .append("&firstname=").append(firstnameplaceholder.getText().toString())
                            .append("&lastname=").append(lastnameplaceholder.getText().toString())
                            .append("&phone=").append(phoneplaceholder.getText().toString())
                            .append("&email=").append(emailplaceholder.getText().toString())
                            .append("&postcode=").append(postcodeplaceholder.getText().toString())
                            .append("&town=").append(townplaceholder.getText().toString())
                            .append("&firstline=").append(passwordplaceholder.getText().toString())
                            .append("&secondline=").append(cpasswordplaceholder.getText().toString())
                            .append("&county=").append(countyplaceholder.getText().toString()).toString();


                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), Confirm_Sender_Fragment.this, "CONFIRMS");


                }


            }



        });


        return v;

    }




    private class WebAsyncTask extends AsyncTask<Object, Void, JSONArray> {

        JSONArray jsonArray;

        String response = null;

        Object classObject;

        Confirm_Sender_Fragment CONFIRMS;

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



                if(result.get(2).toString() == "CONFIRMS")
                {


                    JSONObject f = (JSONObject) result.get(0);




                    if(f.getString("ERROR").length() == 0 )

                    {



                        responseObject.put("recipient_ID",responseObject.get("recipient_ID"));
                        responseObject.put("sender_firstname",firstnameplaceholder.getText());
                        responseObject.put("sender_lastname",lastnameplaceholder.getText());
                        responseObject.put("sender_phone",phoneplaceholder.getText());
                        responseObject.put("sender_country",firstnameplaceholder.getText());
                        responseObject.put("sender_postcode",postcodeplaceholder.getText());
                        responseObject.put("sender_city",townplaceholder.getText());
                        responseObject.put("sender_firstline", passwordplaceholder.getText());


                        Intent intent = new Intent(getActivity(),Confirm_Transfer.class);

                        intent.putExtra(EXTRA_DATA, responseObject.toString());

                        startActivity(intent);



                    }else { errortextView.setText(f.getString("ERROR")); }


                }
                else if(result.get(2).toString() == "SETUPS")
                {


                    JSONObject responseArray = (JSONObject) result.get(0);




                    firstnameplaceholder.setText(responseArray.get("FirstName").toString());

                    lastnameplaceholder.setText(responseArray.get("LastName").toString());

                    phoneplaceholder.setText(responseArray.get("Phone").toString());

                    emailplaceholder.setText(responseArray.get("Email").toString());

                    postcodeplaceholder.setText(responseArray.get("Postcode").toString());

                    townplaceholder.setText(responseArray.get("Town").toString());

                    passwordplaceholder.setText(responseArray.get("Line1").toString());

                    cpasswordplaceholder.setText(responseArray.get("Line2").toString());

                    countyplaceholder.setText(responseArray.get("County").toString());



                }

            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }

}
