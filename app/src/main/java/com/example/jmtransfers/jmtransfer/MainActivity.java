package com.example.jmtransfers.jmtransfer; import android.content.SharedPreferences; import android.support.v7.app.ActionBar; import android.widget.TextView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progress ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);



        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();


        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                progress.dismiss();

               Intent intent = new Intent(MainActivity.this, Dashboard.class);

                startActivity(intent);



            }
        }.start();


        ImageView logo = (ImageView) findViewById(R.id.logo);

        logo.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Dashboard.class);

              startActivity(intent);


            }
        });


    }




}
