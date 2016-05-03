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
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class Receipt_Fragment extends Fragment {



    public final static String EXTRA_DATA = "com.example.jmtransfers.jmtransfer.EXTRA_DATA";


    String WEB_SERVICE_URL ;

    ListView bList;

    JSONObject newJArray;
    ListView bList1;

    JSONArray responseArray;

    LinearLayout blistlayout;
    TextView amount ;
    TextView bname;
    TextView sender;
    TextView address;
    TextView city;
    TextView postcode;
    TextView country;
    TextView phone;
    TextView purpose;
    TextView delivery;
    TextView date;
    TextView paymethod;
    TextView fee;
    TextView rate;
    TextView total;
    TextView totalngn;
    TextView tamount;

    TextView account;
    TextView sortcode;
    TextView shopaccount;

    Bundle args;

    TextView receiver;

    String currentDateTimeString;



    SharedPreferences.Editor editor;

    SharedPreferences settings;      public static final String PREFS_NAME = "JM-Transfers";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_receipt_, container, false);


        amount = (TextView) rootView.findViewById(R.id.amount);
        tamount = (TextView) rootView.findViewById(R.id.tamount);
        bname = (TextView) rootView.findViewById(R.id.bname);
        sender = (TextView) rootView.findViewById(R.id.sender);
        address = (TextView) rootView.findViewById(R.id.address);
        city = (TextView) rootView.findViewById(R.id.city);
        postcode = (TextView) rootView.findViewById(R.id.postcode);
        country = (TextView) rootView.findViewById(R.id.country);

        account = (TextView) rootView.findViewById(R.id.account);

        sortcode = (TextView) rootView.findViewById(R.id.sortcode);


        shopaccount = (TextView) rootView.findViewById(R.id.shopaccount);


        phone = (TextView) rootView.findViewById(R.id.phone);
        purpose = (TextView) rootView.findViewById(R.id.purpose);
        delivery = (TextView) rootView.findViewById(R.id.delivery);
        date = (TextView) rootView.findViewById(R.id.date);
        paymethod = (TextView) rootView.findViewById(R.id.paymentmethod);
        fee = (TextView) rootView.findViewById(R.id.tfee);
        rate = (TextView) rootView.findViewById(R.id.rate);
        total = (TextView) rootView.findViewById(R.id.total);
        totalngn = (TextView) rootView.findViewById(R.id.totalngn);

        receiver = (TextView) rootView.findViewById(R.id.receiver);

        args = getArguments();

        Calendar c = Calendar.getInstance();



        try {


            newJArray = new JSONObject(args.getString("ARG_STRING"));


            amount.setText(newJArray.getString("ngn"));
            bname.setText(newJArray.getString("RecipientsFirstName").concat(" ").concat(newJArray.getString("RecipientsLastName")));
            sender.setText(newJArray.getString("sender_firstname").concat(" ").concat(newJArray.getString("sender_lastname")));
            address.setText(newJArray.getString("sender_firstline"));
            city.setText(newJArray.getString("sender_city"));
            postcode.setText(newJArray.getString("sender_postcode"));
            country.setText(newJArray.getString("sender_country"));
            phone.setText(newJArray.getString("sender_phone"));
            purpose.setText(newJArray.getString("ReasonForTransfer"));
            delivery.setText(newJArray.getString("pay"));


            shopaccount.setText(newJArray.getString("ShopAcc"));

            currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


            date.setText(currentDateTimeString);
            paymethod.setText(newJArray.getString("PaymentRef"));
            fee.setText(newJArray.getString("comm"));
            rate.setText("1 GBP equals ".concat(newJArray.getString("rate")).concat(" Nigerian Naira"));
            total.setText(newJArray.getString("ngn"));
            totalngn.setText(newJArray.getString("total"));
            tamount.setText(newJArray.getString("amount"));
            receiver.setText(newJArray.getString("RecipientsFirstName").concat(" ").concat(newJArray.getString("RecipientsLastName")));


        } catch (JSONException e) {



            e.printStackTrace();
        }




        StringBuilder builder = new StringBuilder();


        try {


            newJArray = new JSONObject(args.getString("ARG_STRING"));

            StringBuilder builder1 = new StringBuilder();

            WEB_SERVICE_URL = builder1.append("http://www.jmtrax.net/app_php_files/getsortcode.php?shopacc=").append(newJArray.get("ShopAcc")) .toString();


            WebAsyncTask Task = new WebAsyncTask();

            Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"),this,"SORTCODE");



        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button next = (Button) rootView.findViewById(R.id.signin);

        next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                try {

                    newJArray = new JSONObject(args.getString("ARG_STRING"));

                    StringBuilder builder = new StringBuilder();



                    WEB_SERVICE_URL = builder.append("http://www.jmtrax.net/app_php_files/transfer.php?")
                            .append("&amount=").append(newJArray.get("amount"))
                            .append("&recID=").append(newJArray.get("recipient_ID"))
                            .append("&senderID=").append(newJArray.get("senders_ID"))
                            .append("&uksms=").append(newJArray.get("uksms"))
                            .append("&nigeriasms=").append(newJArray.get("nigeriasms"))
                            .append("&pay=").append(newJArray.get("pay"))
                            .append("&currentDateTimeString=").append(currentDateTimeString)
                            .toString();



                    WebAsyncTask Task = new WebAsyncTask();

                    Task.execute(WEB_SERVICE_URL.replaceAll(" ", "%20"),this,"CHECKOUT");

                } catch (JSONException e) {

                    e.printStackTrace();

                }



            }


        });




        return rootView;
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





                if(result.get(2).toString() == "CHECKOUT")

                {


                    try {


                        JSONObject f = (JSONObject) result.get(0);

                        settings = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);

                        editor = settings.edit();

                        editor.putString("Balance",f.getString("BALANCE"));


                        Intent intent = new Intent(getActivity(),Dashboard_Fragment.class);

                        intent.putExtra(EXTRA_DATA, newJArray.toString());

                        startActivity(intent);


                    } catch (JSONException e) {


                        e.printStackTrace();


                    }


                }
                else if(result.get(2).toString() == "SORTCODE")

                {

                    try {


                        JSONObject f = (JSONObject) result.get(0);


                        account.setText(f.getString("sortcode"));

                        sortcode.setText(f.getString("account"));




                    } catch (JSONException e) {


                        e.printStackTrace();


                    }

                }




            } catch (JSONException e) {


                e.printStackTrace();


            }


        }


    }

}