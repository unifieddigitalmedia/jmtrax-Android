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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


public class Confirm_Beneficary_Fragment extends Fragment {
    public static String ARG_STRING;

    EditText firstnameplaceholder ;

    EditText lastnameplaceholder ;

    EditText phoneplaceholder ;


    EditText usernameplaceholder ;

    EditText passwordplaceholder ;

    EditText cpasswordplaceholder ;


    EditText emailplaceholder ;


    EditText shopacc ;


    TextView errortextView ;

    JSONObject newJArray;



    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";

    Bundle args;

    Spinner spinnerbank;

    Spinner spinnerbank1;

    public final static String EXTRA_DATA = "com.example.jmtransfers.jmtransfer.EXTRA_DATA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_confirm__beneficary_, container, false);


        WebAsyncTask Task = new WebAsyncTask();

        Task.execute("http://www.jmtrax.net/app_php_files/getbanks.php?", Confirm_Beneficary_Fragment.this, "GETBANKS");


        WebAsyncTask Task1 = new WebAsyncTask();

        Task1.execute("http://www.jmtrax.net/app_php_files/shop_acc.php?", Confirm_Beneficary_Fragment.this, "GETPBANKS");

        firstnameplaceholder = (EditText) v.findViewById(R.id.firstnameplaceholder);
        lastnameplaceholder = (EditText) v.findViewById(R.id.lastnameplaceholder);
        phoneplaceholder = (EditText) v.findViewById(R.id.phoneplaceholder);
        usernameplaceholder = (EditText) v.findViewById(R.id.usernameplaceholder);
        passwordplaceholder = (EditText) v.findViewById(R.id.passwordplaceholder);
        cpasswordplaceholder = (EditText) v.findViewById(R.id.cpasswordplaceholder);
        emailplaceholder = (EditText) v.findViewById(R.id.emailplaceholder);
        errortextView = (TextView) v.findViewById(R.id.errortextView);
        shopacc  = (EditText) v.findViewById(R.id.shopacc);

        args = getArguments();


        if(args.getString("ARG_STRING").length() != 0)

        {


            try {


                newJArray = new JSONObject(args.getString("ARG_STRING"));

                firstnameplaceholder.setText(newJArray.get("RecipientsFirstName").toString());
                lastnameplaceholder.setText(newJArray.get("RecipientsLastName").toString());
                phoneplaceholder.setText(newJArray.get("RecipientsPhone").toString());
                usernameplaceholder.setText(newJArray.get("Bank").toString());
                passwordplaceholder.setText(newJArray.get("NigeriaBankAccount").toString());
                cpasswordplaceholder.setText(newJArray.get("PaymentRef").toString());
                emailplaceholder.setText(newJArray.get("ReasonForTransfer").toString());
                shopacc.setText(newJArray.get("ShopAcc").toString());




            } catch (JSONException e) {

                e.printStackTrace();



            }




        }

        spinnerbank = (Spinner) v.findViewById(R.id.spinnerbank);

        spinnerbank1 = (Spinner) v.findViewById(R.id.spinner2);


        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.payment_ref_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(position == 0 && args.getString("ARG_STRING").length() != 0)

                {

                    try {


                        newJArray = new JSONObject(args.getString("ARG_STRING"));

                        cpasswordplaceholder.setText(newJArray.get("PaymentRef").toString());


                    } catch (JSONException e) {


                        e.printStackTrace();


                    }


                }
                else

                {


                    cpasswordplaceholder.setText((CharSequence) parent.getItemAtPosition(position));
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


                cpasswordplaceholder.setText("");


            }
        });




        Spinner spinner1 = (Spinner) v.findViewById(R.id.spinner1);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.payment_reason_array, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0 && args.getString("ARG_STRING").length() != 0)

                {


                    try {


                        newJArray = new JSONObject(args.getString("ARG_STRING"));

                        emailplaceholder.setText(newJArray.get("ReasonForTransfer").toString());


                    } catch (JSONException e) {

                        e.printStackTrace();


                    }

                } else

                {


                    emailplaceholder.setText((CharSequence) parent.getItemAtPosition(position));


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                emailplaceholder.setText("");

            }
        });



        Button cancel = (Button) v.findViewById(R.id.add);

        cancel.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                StringBuilder builder = new StringBuilder();

                if ( firstnameplaceholder.getText().toString() == "" || lastnameplaceholder.getText().toString() == "" || phoneplaceholder.getText().toString() == "" ||
                        usernameplaceholder.getText().toString() =="" || passwordplaceholder.getText().toString() == "" || cpasswordplaceholder.getText().toString() == "" || emailplaceholder.getText().toString() == "" )
                {


                    errortextView.setText("All feilds are requiered");

                }

                else if ( passwordplaceholder.getText().toString().length() < 10)

                {


                    errortextView.setText("Bank account must be at least 10 characters in length ");


                }

                else

                {


                    settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

                    editor = settings.edit();

                    editor.commit();


                    String WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/add.php?")
                            .append("&firstname=").append(firstnameplaceholder.getText().toString())
                            .append("&lastname=").append(lastnameplaceholder.getText().toString())
                            .append("&phone=").append(phoneplaceholder.getText().toString())
                            .append("&bank=").append(usernameplaceholder.getText().toString())
                            .append("&account=").append(passwordplaceholder.getText().toString())
                            .append("&reference=").append(cpasswordplaceholder.getText().toString())
                            .append("&reason=").append(emailplaceholder.getText().toString())
                            .append("&username=").append(settings.getString("Username", ""))
                            .append("&shopAcc=").append(shopacc.getText()).toString();


                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), this, "ADDB");


                }





            }


        });




        Button send = (Button) v.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if ( firstnameplaceholder.getText().toString() == "" || lastnameplaceholder.getText().toString() == "" || phoneplaceholder.getText().toString() == "" || usernameplaceholder.getText().toString() =="" || passwordplaceholder.getText().toString() == "" || cpasswordplaceholder.getText().toString() == "" || emailplaceholder.getText().toString() == "" )
                {


                    errortextView.setText("All feilds are requiered");

                }

                else if ( passwordplaceholder.getText().toString().length() < 10)

                {


                    errortextView.setText("Bank account must be at least 10 characters in length ");


                }

                else
                {

                    StringBuilder builder = new StringBuilder();

                    String WEB_SERVICE_URL = null;

                    try {

                        WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/updater.php?id=")
                                .append(newJArray.get("recipient_ID").toString())
                                .append("&firstname=").append(firstnameplaceholder.getText().toString())
                                .append("&lastname=").append(lastnameplaceholder.getText().toString())
                                .append("&phone=").append(phoneplaceholder.getText().toString())
                                .append("&bank=").append(usernameplaceholder.getText().toString())
                                .append("&account=").append(passwordplaceholder.getText().toString())
                                .append("&reference=").append(cpasswordplaceholder.getText().toString())
                                .append("&reason=").append(emailplaceholder.getText().toString())
                                .append("&shopAcc=").append(shopacc.getText()).toString();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), this, "UPDATEB");


                }




            }


        });


        Button update = (Button) v.findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if ( firstnameplaceholder.getText().toString() == "" || lastnameplaceholder.getText().toString() == "" || phoneplaceholder.getText().toString() == "" || usernameplaceholder.getText().toString() =="" || passwordplaceholder.getText().toString() == "" || cpasswordplaceholder.getText().toString() == "" || emailplaceholder.getText().toString() == "" )
                {


                    errortextView.setText("All feilds are requiered");

                }

                else if ( passwordplaceholder.getText().toString().length() < 10)

                {


                    errortextView.setText("Bank account must be at least 10 characters in length ");


                }

                else
                {

                    StringBuilder builder = new StringBuilder();

                    String WEB_SERVICE_URL = null;

                    try {

                        WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/updater.php?id=")
                                .append(newJArray.get("recipient_ID").toString())
                                .append("&firstname=").append(firstnameplaceholder.getText().toString())
                                .append("&lastname=").append(lastnameplaceholder.getText().toString())
                                .append("&phone=").append(phoneplaceholder.getText().toString())
                                .append("&bank=").append(usernameplaceholder.getText().toString())
                                .append("&account=").append(passwordplaceholder.getText().toString())
                                .append("&reference=").append(cpasswordplaceholder.getText().toString())
                                .append("&reason=").append(emailplaceholder.getText().toString())
                                .append("&shopAcc=").append(shopacc.getText()).toString();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), this, "UPDATEB");


                }





            }


        });


        Button delete = (Button) v.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                StringBuilder builder = new StringBuilder();

                String WEB_SERVICE_URL = null;

                try {

                    WEB_SERVICE_URL = builder.append("http://jmtrax.net/app_php_files/deleteb.php?id=")
                            .append(newJArray.get("recipient_ID").toString()).toString();


                } catch (JSONException e) {


                    e.printStackTrace();


                }


                WebAsyncTask Task = new WebAsyncTask();

                Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"), this, "DELETEB");




            }


        });



        return v;

    }





    public void takeaction(JSONArray result) throws JSONException {




    }



    private class BankOnItemSelectedListener implements AdapterView.OnItemSelectedListener {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(position == 0 && args.getString("ARG_STRING").length() != 0)

            {
                try {


                    newJArray = new JSONObject(args.getString("ARG_STRING"));

                    usernameplaceholder.setText(newJArray.get("Bank").toString());

                } catch (JSONException e) {

                    e.printStackTrace();

                }



            }
            else

            {

                usernameplaceholder.setText((CharSequence) parent.getItemAtPosition(position));
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class PBankOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0 && args.getString("ARG_STRING").length() != 0)

            {
                try {


                    newJArray = new JSONObject(args.getString("ARG_STRING"));

                    shopacc.setText(newJArray.get("ShopAcc").toString());


                } catch (JSONException e) {


                    e.printStackTrace();


                }



            }
            else

            {

                shopacc.setText((CharSequence) parent.getItemAtPosition(position));

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
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



                if (result.get(2).toString() == "DELETEB")
                {

                    BenActivity = (Confirm_Beneficary_Fragment) classObject;




                }

                else if(result.get(2).toString() == "ADDB" || result.get(2).toString() =="UPDATEB")

                {

                    JSONObject f = (JSONObject) result.get(0);


                    if(f.getString("ERROR").length() == 0 )

                    {




                        newJArray = new JSONObject();

                        newJArray.put("recipient_ID", f.getString("RECID"));

                        newJArray.put("senders_ID", f.getString("SENDERS_ID"));

                        newJArray.put("RecipientsFirstName",firstnameplaceholder.getText().toString());

                        newJArray.put("RecipientsLastName", lastnameplaceholder.getText().toString());

                        newJArray.put("RecipientsPhone", phoneplaceholder.getText().toString());

                        newJArray.put("NigeriaBankAccount",passwordplaceholder.getText().toString());

                        newJArray.put("Bank",usernameplaceholder.getText().toString());

                        newJArray.put("PaymentRef", cpasswordplaceholder.getText().toString());

                        newJArray.put("ShopAcc", shopacc.getText().toString());

                        newJArray.put("ReasonForTransfer", emailplaceholder.getText().toString());

                        Intent intent = new Intent(getActivity(),Confirm_Sender.class);

                        intent.putExtra(EXTRA_DATA, newJArray.toString());

                        startActivity(intent);



                    }else { errortextView.setText(f.getString("ERROR")); }

                }
                else if(result.get(2).toString() == "GETBANKS")

                {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    adapter.add("Select a Bank...");

                    JSONArray responseArray = (JSONArray) result.get(0);


                    for (int i = 0; i < responseArray.length(); ++i)
                    {

                        adapter.add(responseArray.getString(i));

                    }

                    spinnerbank.setAdapter(adapter);

                    spinnerbank.setOnItemSelectedListener(new BankOnItemSelectedListener());


                }
                else if(result.get(2).toString() == "GETPBANKS")

                {

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item);

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    adapter2.add("Select a Payee Bank...");

                    JSONArray responseArray = (JSONArray) result.get(0);


                    for (int i = 0; i < responseArray.length(); ++i)
                    {

                        adapter2.add(responseArray.getString(i));

                    }

                    spinnerbank1.setAdapter(adapter2);

                    spinnerbank1.setOnItemSelectedListener(new PBankOnItemSelectedListener());


                }


            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }

}