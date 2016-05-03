package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class Transfer_Receipt_Fragment extends Fragment {
    public static String ARG_STRING;

    TextView firstnameplaceholder ;

    TextView lastnameplaceholder ;

    TextView phoneplaceholder ;


    TextView usernameplaceholder ;

    TextView passwordplaceholder ;

    TextView cpasswordplaceholder ;


    TextView emailplaceholder ;


    JSONObject newJArray;



    TextView amountsent ;
    TextView fee ;
    TextView transferpaid ;
    TextView transfertotal ;
    TextView transferdate;
    TextView ordernumber;

    TextView transferbank;

    TextView transferaccount;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_transfer__receipt_, container, false);




        transferdate = (TextView) v.findViewById(R.id.transferdate);

        ordernumber = (TextView) v.findViewById(R.id.ordernumber);


        firstnameplaceholder = (TextView) v.findViewById(R.id.firstnameplaceholder);

        lastnameplaceholder = (TextView) v.findViewById(R.id.lastnameplaceholder);




        passwordplaceholder = (TextView) v.findViewById(R.id.transferstatus);



        transferaccount = (TextView) v.findViewById(R.id.transferaccount);

        transferbank = (TextView) v.findViewById(R.id.transferbank);





        cpasswordplaceholder = (TextView) v.findViewById(R.id.cpasswordplaceholder);

        emailplaceholder = (TextView) v.findViewById(R.id.emailplaceholder);





        amountsent = (TextView) v.findViewById(R.id.amountsent);

        fee = (TextView) v.findViewById(R.id.fee);

        transferpaid = (TextView) v.findViewById(R.id.transferpaid);

        transfertotal = (TextView) v.findViewById(R.id.transfertotal);



        Bundle args = getArguments();

        try {


            newJArray = new JSONObject(args.getString("ARG_STRING"));




            transferdate.setText(newJArray.get("Date").toString());

            ordernumber.setText("JM".concat(newJArray.get("order_ID").toString()));


            firstnameplaceholder.setText(newJArray.get("RecipientsFirstName").toString());

            lastnameplaceholder.setText(newJArray.get("RecipientsLastName").toString());




            passwordplaceholder.setText(newJArray.get("Status").toString());



            transferaccount.setText(newJArray.get("NigeriaBankAccount").toString());

            transferbank.setText(newJArray.get("Bank").toString());


            cpasswordplaceholder.setText(newJArray.get("PaymentRef").toString());

            emailplaceholder.setText(newJArray.get("ReasonForTransfer").toString());


            amountsent.setText(newJArray.get("Remittance").toString().concat(" GBP"));

            fee.setText(newJArray.get("CommissionCharged").toString().concat(" GBP"));

            transferpaid.setText(newJArray.get("TotalDueNGN").toString().concat(" NGN"));

            transfertotal.setText(newJArray.get("TotalDueGBP").toString().concat(" GBP"));



        } catch (JSONException e) {

            e.printStackTrace();



        }





        return v;

    }




}
