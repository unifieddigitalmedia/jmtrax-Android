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
import android.widget.ArrayAdapter;
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



public class Help_Fragment extends Fragment {

    EditText to ;

    EditText subject ;

    EditText message ;

    TextView errortextView ;

    String WEB_SERVICE_URL ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help_, container, false);

        errortextView = (TextView) v.findViewById(R.id.errortextView);
        to = (EditText) v.findViewById(R.id.to);
        subject = (EditText) v.findViewById(R.id.subject);
        message = (EditText) v.findViewById(R.id.message);


        Button send = (Button) v.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (to.getText().toString().length() == 0 || subject.getText().toString().length() == 0 || message.getText().toString().length() == 0)

                {

                    errortextView.setText("All fields need to be filled out");


                } else {


                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/help.php?")
                            .append("&email=").append(to.getText().toString())
                            .append("&subject=").append(subject.getText().toString())
                            .append("&message=").append(message.getText().toString()).toString();

                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL, this, "MAIL");


                }


            }


        });

        return v;
    }



    private class WebAsyncTask extends AsyncTask<Object, Void, JSONArray> {

        JSONArray jsonArray;

        String response = null;

        Object classObject;

        Confirm_Beneficary_Fragment BenActivity;

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


                }else { errortextView.setText(responseArray.getString("ERROR")); }


            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }

}
