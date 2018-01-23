package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.webservice.RxClient;


/**
 * Created by Venkatesh on 28-04-16.
 */
public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;  //2 Seconds
    SharedPreferences sharedpreferences;
    Intent i;
    Context _context;
    String Permission4;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Activity _activity;
    // Toast DeclineToast;
    // Boolean SpOtpVerified;

    TextView tv_proceed;
    ImageView iv_splashscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        tv_proceed = (TextView) findViewById(R.id.text_proceed);
        iv_splashscreen = (ImageView) findViewById(R.id.imageView_splash);

        try {
            String vName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.d("version name", vName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent2Activity();
            }
        });

        iv_splashscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent2Activity();

                callwebService();
            }
        });
    }

    private void callwebService() {

        //RxClient.get(getApplicationContext())
    }


    private void Intent2Activity() {

        Intent i = new Intent(SplashActivity.this, FinstartHomeActivity.class);
        i.putExtra("coupon_code", getIntent().getStringExtra("coupon_code"));
        startActivity(i);
        // close this activity
        finish();
/*
        new Handler().postDelayed(new Runnable() {
            *//*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             *//*
             *
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, FinstartHomeActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }

    private boolean checkPermission(String permission) {

        int result = ContextCompat.checkSelfPermission(_context, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {

            return false;
        }
    }

    private void requestPermission(String Permission) {
        ActivityCompat.requestPermissions(_activity, new String[]{Permission}, PERMISSION_REQUEST_CODE);
    }


  /*  public void ShowDeclineToast() {
        if (DeclineToast == null) {
            DeclineToast.show();
        }
    }*/

}