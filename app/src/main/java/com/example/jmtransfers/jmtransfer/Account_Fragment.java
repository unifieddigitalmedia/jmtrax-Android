package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.TextView;

import android.app.Activity;
import android.content.Context;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
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


public class Account_Fragment extends Fragment {


    EditText emailplaceholder ;

    EditText passwordplaceholder ;

    String WEB_SERVICE_URL ;


    String emailplaceholderText;

    String passwordplaceholderText ;

    TextView errortextView ;

    boolean rememberme = true;

    Switch toggle;

    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    TextView forgotView;


    private ProgressBar spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_account_, container, false);






        spinner = (ProgressBar) v.findViewById(R.id.progressBar1);


        spinner.setVisibility(View.GONE);

        emailplaceholder = (EditText) v.findViewById(R.id.emailplaceholder);

        passwordplaceholder = (EditText) v.findViewById(R.id.passwordplaceholder);

        settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        editor = settings.edit();

        emailplaceholder.setText(settings.getString("Email", ""));


        passwordplaceholder.setText(settings.getString("Password", ""));










        forgotView   = (TextView) v.findViewById(R.id.forgotView);

        forgotView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity().getBaseContext(), Forgot_Password.class);

                startActivity(intent);


            }

        });




        editor.putString("Username",emailplaceholderText);







        Button register = (Button) v.findViewById(R.id.register);

        errortextView = (TextView) v.findViewById(R.id.errortextView);

        register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                Intent intent = new Intent(getActivity().getBaseContext(), RegisterTabActivity.class);

                startActivity(intent);


            }


        });



        Button next = (Button) v.findViewById(R.id.signin);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {





                emailplaceholderText = emailplaceholder.getText().toString();

                passwordplaceholderText = passwordplaceholder.getText().toString();



                if ( emailplaceholderText.length() < 3   || passwordplaceholderText.length() < 3  )
                {

                    errortextView.setText("Both password and username are needed");


                }

                else

                {


                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://jmtrax.net/app_php_files/login.php?").append("&email=").append(emailplaceholderText)
                            .append("&password=").append(passwordplaceholderText).toString();



                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"),this,"LOG");


                }




            }


        });

        toggle = (Switch) v.findViewById(R.id.switch1);


        toggle.setChecked(true);


        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    rememberme = true;


                } else {


                    rememberme = false;

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


                spinner.setVisibility(View.VISIBLE);

                JSONObject responseArray = (JSONObject) result.get(0);





                if(responseArray.getString("ERROR").length() == 0 )

                {



                    if (rememberme)

                    {

                        editor.putString("Username", responseArray.getString("USERNAME"));



                        editor.putString("Password",passwordplaceholderText);

                        editor.putString("Email",emailplaceholderText);

                        editor.putString("Balance",responseArray.getString("BALANCE"));

                        editor.putString("Status",responseArray.getString("STATUS"));

                        editor.putString("Name", responseArray.getString("NAME"));

                        editor.commit();

                        emailplaceholder.setText("");

                        passwordplaceholder.setText("");


                        Intent intent = new Intent(getActivity(), Dashboard_Fragment.class);

                        startActivity(intent);




                    }else{


                        editor.putString("Email","");

                        editor.putString("Password", "");



                        editor.putString("Username", responseArray.getString("USERNAME"));




                        editor.commit();

                        emailplaceholder.setText("");

                        passwordplaceholder.setText("");

                        Intent intent = new Intent(getActivity().getBaseContext(), Dashboard.class);

                        startActivity(intent);


                    }


                }else {

                    spinner.setVisibility(View.GONE);

                    errortextView.setText(responseArray.getString("ERROR"));


                }




            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }



}