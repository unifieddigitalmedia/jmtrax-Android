package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class Forgot_Password_Fragment extends Fragment {

    EditText emailplaceholder ;

    TextView errortextView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_forgot__password, container, false);




        emailplaceholder = (EditText) v.findViewById(R.id.emailplaceholder);

        errortextView = (TextView) v.findViewById(R.id.errortextView);

        Button next = (Button) v.findViewById(R.id.signin);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (emailplaceholder.getText().toString().length() == 0) {

                    errortextView.setText("Your email is needed needed");


                } else

                {


                    StringBuilder builder = new StringBuilder();

                    String WEB_SERVICE_URL = builder.append("http://jmtrax.net/app_php_files/forgotpassword.php?").append("&email=").append(emailplaceholder.getText().toString())
                            .toString();


                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL, this, "FORGOT");


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
