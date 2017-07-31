package com.myappilication.xpress.finjan2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;

/**
 * Created by sureshmano on 3/2/2017.
 */

public class FinjanCalcModule extends AppCompatActivity {

    TextView tv_sip, tv_ppf, tv_sscalc;
    Intent i;
    Toolbar toolbar;
    ImageButton imageButton;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finjan_calcmodule);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_sip = (TextView) findViewById(R.id.text_sipcalc);
        tv_ppf = (TextView) findViewById(R.id.text_ppfcalc);
        tv_sscalc = (TextView) findViewById(R.id.text_sscalc);

        tv_sip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sipint = new Intent(FinjanCalcModule.this,CalcSip.class);
                startActivity(sipint);
            }
        });

        tv_ppf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ppfint = new Intent(FinjanCalcModule.this, CalcPPF.class);
                startActivity(ppfint);
            }
        });

        tv_sscalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ssint = new Intent(FinjanCalcModule.this, CalcSS.class);
                startActivity(ssint);
            }
        });




    }



    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                return true;
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;
         /*   case R.id.finjan_video:
                startActivity(new Intent(getApplicationContext(), FinjanActivity.class));
                return true;*/
            case R.id.logout:
                sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();

                editor.putString(SharedPrefUtils.SpEmail, "");
                editor.putString(SharedPrefUtils.SpPassword, "");
                editor.putBoolean(SharedPrefUtils.SpIsNewUser, true);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("EXIT", true);
                startActivity(intent);

                finish();
                return true;


        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }




}
