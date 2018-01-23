package com.myappilication.xpress.finjan2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.models.login.forget.forgotreq;
import com.myappilication.xpress.finjan2017.models.login.forget.forgotresp;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sureshmano on 3/29/2017.
 */

public class ForgotPasswordActivity  extends AppCompatActivity{
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    EditText et_forgot_email;
    Button btn_forgot_password;
    Context context;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpass);
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        et_forgot_email = (EditText) findViewById(R.id.et_forgot_email);
        btn_forgot_password = (Button) findViewById(R.id.bt_forget);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        context = getApplicationContext();

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //et_forgot_email.setText("muthu.k@quadrupleindia.com");

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.commit();

        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_forgot_email.getText().toString().length()>0){
                    mtd_forget_password();
                }else{
                    Toast.makeText(context, "Kindly give your email address", Toast.LENGTH_LONG).show();
                }
            }


        });


    }

    private void mtd_forget_password() {
        progressBar.setVisibility(View.VISIBLE);
        RxClient.get(context).ForgetPass(new forgotreq(et_forgot_email.getText().toString()),
                new Callback<forgotresp>() {
            @Override
            public void success(forgotresp forgotresp, Response response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (forgotresp.getStatus().equals("200")){
                    Toast.makeText(context, forgotresp.getInfo().getMessage(), Toast.LENGTH_LONG).show();

                    Intent imodule=new Intent(ForgotPasswordActivity.this,MainActivity.class);
                    startActivity(imodule);
                    finish();

                    try{
                        MainActivity.login.finish();
                    }catch (Exception e){

                    }
                }else{
                    Toast.makeText(context, "Invalid email address", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Service not response", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

   /* @Override
    public void onBackPressed() {

        super.onBackPressed();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.fin_support:
                startActivity(new Intent(getApplicationContext(), Support.class));
                return true;

            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
         /*   case R.id.finjan:
                startActivity(new Intent(getApplicationContext(), FinjanActivity.class));
                return true;*/
            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;
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

       // getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }



}
