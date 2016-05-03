package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
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

public class Sign_up_details extends Fragment {

    EditText firstnameplaceholder ;

    EditText lastnameplaceholder ;

    EditText phoneplaceholder ;


    EditText usernameplaceholder ;

    EditText passwordplaceholder ;

    EditText cpasswordplaceholder ;

    String WEB_SERVICE_URL ;

    EditText emailplaceholder ;

    TextView errortextView;

    SharedPreferences.Editor editor;

    SharedPreferences settings;


    public static final String PREFS_NAME = "JM-Transfers";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_sign_up_details, container, false);


        firstnameplaceholder = (EditText) v.findViewById(R.id.firstnameplaceholder);
        lastnameplaceholder = (EditText) v.findViewById(R.id.lastnameplaceholder);
        phoneplaceholder = (EditText) v.findViewById(R.id.phoneplaceholder);
        usernameplaceholder = (EditText) v.findViewById(R.id.usernameplaceholder);
        passwordplaceholder = (EditText) v.findViewById(R.id.passwordplaceholder);
        cpasswordplaceholder = (EditText) v.findViewById(R.id.cpasswordplaceholder);
        emailplaceholder = (EditText) v.findViewById(R.id.emailplaceholder);
        errortextView = (TextView) v.findViewById(R.id.errortextView);


        Button next = (Button) v.findViewById(R.id.signin);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (cpasswordplaceholder.getText().toString().length() == 0 || firstnameplaceholder.getText().toString().length() == 0 || lastnameplaceholder.getText().toString().length() == 0 || phoneplaceholder.getText().toString().length() == 0 || usernameplaceholder.getText().toString().length() == 0 || passwordplaceholder.getText().toString().length() == 0 || cpasswordplaceholder.getText().toString().length() == 0)

                {

                    errortextView.setText("All fields need to be filled out");


                } else if (!cpasswordplaceholder.getText().toString().equals(passwordplaceholder.getText().toString()))

                {


                    errortextView.setText("Your passwords do not match");


                } else if (passwordplaceholder.getText().toString().length() < 8)

                {


                    errortextView.setText("Your passwords must be atleast 8 characters in length");


                } else if (phoneplaceholder.getText().toString().length() < 10 || phoneplaceholder.getText().toString().length() > 10)

                {


                    errortextView.setText("Phone must be UK format without the zero");


                } else if (emailplaceholder.getText().toString().length() < 3)

                {

                    errortextView.setText("Please check your email");


                } else if (String.valueOf(phoneplaceholder.getText().toString().charAt(0)).equals("0"))

                {

                    errortextView.setText("Phone must be UK format without the zero");


                } else

                {

                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/signup.php?")
                            .append("&firstname=").append(firstnameplaceholder.getText().toString())
                            .append("&lastname=").append(lastnameplaceholder.getText().toString())
                            .append("&phone=").append(phoneplaceholder.getText().toString())
                            .append("&username=").append(usernameplaceholder.getText().toString())
                            .append("&password=").append(passwordplaceholder.getText().toString())
                            .append("&email=").append(emailplaceholder.getText().toString()).toString();


                    settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

                    editor = settings.edit();

                    editor.putString("Name",firstnameplaceholder.getText().toString() + lastnameplaceholder.getText().toString());

                    editor.commit();


                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), "SIGN");


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


                    RegisterTabActivity dashActivity = (RegisterTabActivity) getActivity();

                    dashActivity.signupUser(responseArray.getString("senders_ID"));


                }else { errortextView.setText(responseArray.getString("ERROR")); }




            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }

}
