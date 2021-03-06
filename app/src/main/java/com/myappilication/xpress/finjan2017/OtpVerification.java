package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpResendReq;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpResendResponse;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpVerificationReq;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpVerificationResponse;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 16/5/17.
 */
public class OtpVerification extends Activity{

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Boolean isSearchtoakenExpired = false;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverification);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Boolean NewUser = sharedpreferences.getBoolean(SharedPrefUtils.SpIsNewUser, true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

   /*  if (!NewUser){
            Intent imodule = new Intent(OtpVerification.this, ModuleFinJan.class);
            startActivity(imodule);
            finish();
        }*/

        TextView resendOTP = (TextView) findViewById(R.id.resend_otp);
        TextView changeNumber = (TextView) findViewById(R.id.change_number);
        resendOTP.setPaintFlags(resendOTP.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        changeNumber.setPaintFlags(changeNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        final EditText te = (EditText) findViewById(R.id.otp_verif);

        Button submit = (Button) findViewById(R.id.otp_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(te.getText().toString().length() != 0){
                    Log.d("click button", "click button");
                    otpSend(te.getText().toString());
                }else{
                    Toast.makeText(OtpVerification.this, "Kindly fill your otp code", Toast.LENGTH_LONG);
                }
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendService();

            }
        });

        changeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void otpSend(String s) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d("otp send method", "method begin");
        String email = getIntent().getStringExtra("email");
        RxClient.get(getApplicationContext()).verifOtp(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new OtpVerificationReq(email, s), new Callback<OtpVerificationResponse>() {
                    @Override
                    public void success(OtpVerificationResponse otpVerificationResponse, Response response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("service success", "success");
                        //   mtd_refresh_token();
                        /*editor.putBoolean(SharedPrefUtils.SpIsNewUser, false);
                        editor.commit();*/

                       /* Toast.makeText(OtpVerification.this,
                                otpVerificationResponse.getMsg(), Toast.LENGTH_LONG).show();*/
                        Toast.makeText(OtpVerification.this, "Otp Verified Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(OtpVerification.this, MainActivity.class);
                        startActivity(i);
                        finish();



                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("service failure", "failure");
                        OtpVerificationResponse usere = (OtpVerificationResponse)
                                error.getBodyAs(OtpVerificationResponse.class);
                        Toast.makeText(OtpVerification.this, usere.getMsg(), Toast.LENGTH_LONG).show();


                    }
                });
    }

    private void resendService() {
        progressBar.setVisibility(View.VISIBLE);

        String email = getIntent().getStringExtra("email");
        Log.d("Email",email);
        String number = getIntent().getStringExtra("mobile");
        Log.d("Number",number);
            RxClient.get(getApplicationContext()).otpResend(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                    new OtpResendReq(email, number), new Callback<OtpResendResponse>() {
                        @Override
                        public void success(OtpResendResponse otpResendResponse, Response response) {
                            progressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(OtpVerification.this,
                                    otpResendResponse.getMsg(), Toast.LENGTH_LONG).show();
                            Toast.makeText(OtpVerification.this, "OTP has been sent to registered mobile number",
                                    Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressBar.setVisibility(View.INVISIBLE);

                            OtpResendResponse usere = (OtpResendResponse)error.getBodyAs(OtpResendResponse.class);
                          //  Toast.makeText(OtpVerification.this, usere.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    });

    }

    private void mtd_refresh_token() {
        RxClient.get(OtpVerification.this).Login(new loginreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),
                sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {

                if (loginresp.getStatus().equals("200")){
                    //Toast.makeText(getApplicationContext(),"sucesss"+loginresp.getToken().toString(),Toast.LENGTH_LONG).show();
                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    editor.commit();

                }
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(),"Wrong Username And Password",Toast.LENGTH_LONG).show();

            }
        });

    }



}
