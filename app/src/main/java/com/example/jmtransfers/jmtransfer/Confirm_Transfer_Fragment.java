package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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


public class Confirm_Transfer_Fragment extends Fragment {


    public final static String EXTRA_DATA = "com.example.jmtransfers.jmtransfer.EXTRA_DATA";

    EditText firstnameplaceholder ;

    EditText lastnameplaceholder ;

    EditText phoneplaceholder ;


    String WEB_SERVICE_URL ;


    EditText usernameplaceholder ;

    JSONObject newJArray;



    Switch uksmstoggle;


    Switch ngnsmstoggle;


    Switch compliancetoggle;

    String uksms = "null";

    String ngnsms = "null";

    String compliance;

    String rate;

    TextView amountsent ;
    TextView fee ;
    TextView transferpaid ;
    TextView transfertotal ;

    TextView errortextView;

    TextView terms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_confirm__transfer_, container, false);

        terms   = (TextView) v.findViewById(R.id.terms);

        terms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent viewintent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://www.jmtrax.com/money-laundering-statement/"));
                startActivity(viewintent);

            }

        });
        firstnameplaceholder = (EditText) v.findViewById(R.id.firstnameplaceholder);

        firstnameplaceholder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {




                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/gbp.php?")
                            .append("&amount=").append(firstnameplaceholder.getText().toString()).toString();


                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL, this, "CALCULATOR");






                }
                return false;
            }
        });

        lastnameplaceholder = (EditText) v.findViewById(R.id.lastnameplaceholder);




        phoneplaceholder = (EditText) v.findViewById(R.id.phoneplaceholder);


        phoneplaceholder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {

                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/ngn.php?")
                            .append("&amount=").append(phoneplaceholder.getText().toString()).toString();


                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL, this, "CALCULATOR");
                }
                return false;
            }
        });

        usernameplaceholder = (EditText) v.findViewById(R.id.usernameplaceholder);


        usernameplaceholder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    StringBuilder builder = new StringBuilder();

                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/gbpdue.php?")
                            .append("&amount=").append(usernameplaceholder.getText().toString()).toString();


                    WebAsyncTask Task = new WebAsyncTask();


                    Task.execute(WEB_SERVICE_URL, this, "CALCULATOR");
                }
                return false;
            }
        });


        amountsent = (TextView) v.findViewById(R.id.amountsent);

        fee = (TextView) v.findViewById(R.id.fee);

        transferpaid = (TextView) v.findViewById(R.id.transferpaid);

        transfertotal = (TextView) v.findViewById(R.id.transfertotal);

        errortextView = (TextView) v.findViewById(R.id.errortextView);


        Button cancel = (Button) v.findViewById(R.id.login);

        cancel.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if(firstnameplaceholder.getText().length() == 0 ||


                        lastnameplaceholder.getText().length() == 0  ||


                        phoneplaceholder.getText().length() == 0  ||


                        usernameplaceholder.getText().length() == 0 )

                {

                    errortextView.setText("You need to enter an amount");


                }else {
                    if(compliance == "on")

                    {
                        StringBuilder builder = new StringBuilder();


                        Bundle args = getArguments();

                        try {


                            newJArray = new JSONObject(args.getString("ARG_STRING"));

                            newJArray.put("amount",firstnameplaceholder.getText());

                            newJArray.put("amounttxt",amountsent.getText());

                            newJArray.put("uksms",uksms);

                            newJArray.put("nigeriasms", ngnsms);

                            newJArray.put("comm", fee.getText());

                            newJArray.put("total", transferpaid.getText());

                            newJArray.put("ngn", transfertotal.getText());

                            newJArray.put("rate", rate);

                            Intent intent = new Intent(getActivity(),Confirm_Pay.class);

                            intent.putExtra(EXTRA_DATA, newJArray.toString());

                            startActivity(intent);




                        } catch (JSONException e) {

                            e.printStackTrace();

                        }






                    }

                    else

                    {

                        errortextView.setText("You need to agree to JM Transfers terms and conditions");


                    }

                }


            }


        });




        uksmstoggle = (Switch) v.findViewById(R.id.switch1);

        uksmstoggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    uksms = "on";


                } else {


                    uksms = "null";

                }

            }

        });


        ngnsmstoggle = (Switch) v.findViewById(R.id.switch2);

        ngnsmstoggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    ngnsms = "on";


                } else {


                    ngnsms = "null";

                }

            }

        });


        compliancetoggle = (Switch) v.findViewById(R.id.switch3);

        compliancetoggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    compliance = "on";


                } else {


                    compliance = "null";

                }

            }

        });


        return v;
    }





    public void checkout(JSONArray result) throws JSONException {


        JSONObject responseArray = (JSONObject) result.get(0);


        if(responseArray.getString("ERROR").length() == 0 )

        {



        }else { errortextView.setText(responseArray.getString("ERROR")); }




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





                 if(result.get(2).toString() == "CALCULATOR")

                {

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


                        rate = responseArray.get("RATE").toString();



                    }else { errortextView.setText(responseArray.getString("ERROR")); }






                }




            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }
}