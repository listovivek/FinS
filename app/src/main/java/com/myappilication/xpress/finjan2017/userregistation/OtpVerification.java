package com.myappilication.xpress.finjan2017.userregistation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.FaqActivity;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.ProfileSetting;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.feedback.UserFeedbackList;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpResendReq;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpResendResponse;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpVerificationReq;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpVerificationResponse;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
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

    public static Dialog mprProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverification);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Boolean NewUser = sharedpreferences.getBoolean(SharedPrefUtils.SpIsNewUser, true);

   /*  if (!NewUser){
            Intent imodule = new Intent(OtpVerification.this, ModuleFinJan.class);
            startActivity(imodule);
            finish();
        }*/

        TextView resendOTP = (TextView) findViewById(R.id.resend_otp);
        TextView changeNumber = (TextView) findViewById(R.id.change_number);
        resendOTP.setPaintFlags(resendOTP.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        changeNumber.setPaintFlags(changeNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);

        final EditText te = (EditText) findViewById(R.id.otp_verif);

        Button submit = (Button) findViewById(R.id.otp_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(te.getText().toString().length()>0){
                    otpSend(te.getText().toString());
                }else{
                    Toast.makeText(OtpVerification.this, "Kindly fill your OTP code", Toast.LENGTH_LONG).show();
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
            String email = getIntent().getStringExtra("email");
        //progressBar.setVisibility(View.VISIBLE);
        mprProgressDialog.show();

        RxClient.get(getApplicationContext()).verifOtp(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new OtpVerificationReq(email, s), new Callback<OtpVerificationResponse>() {

                    @Override
                    public void success(OtpVerificationResponse otpVerificationResponse, Response response) {
                     //   mtd_refresh_token();
                        /*editor.putBoolean(SharedPrefUtils.SpIsNewUser, false);
                        editor.commit();*/

                       /* Toast.makeText(OtpVerification.this,
                                otpVerificationResponse.getMsg(), Toast.LENGTH_LONG).show();*/
                        editor.remove("couponbaseModuleid");
                        editor.remove("isusergetmoduleid");
                        editor.remove("isusergetexpdate");
                        editor.apply();
                        Toast.makeText(OtpVerification.this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(OtpVerification.this, MainActivity.class);
                        i.putExtra("coupon_code", getIntent().getStringExtra("coupon_code"));
                        startActivity(i);
                        finish();
                        UserRegisterActivity.act.finish();
                        //progressBar.setVisibility(View.INVISIBLE);

                        mprProgressDialog.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try{
                            OtpVerificationResponse usere = (OtpVerificationResponse)
                                    error.getBodyAs(OtpVerificationResponse.class);
                            Toast.makeText(OtpVerification.this, usere.getMsg(), Toast.LENGTH_LONG).show();
                            String email = getIntent().getStringExtra("email");
                           // progressBar.setVisibility(View.INVISIBLE);
                            mprProgressDialog.dismiss();
                        }catch (Exception e){
                        }

                    }
                });
    }

    private void resendService() {
        String email = getIntent().getStringExtra("email");
        String number = getIntent().getStringExtra("mobile");
       // progressBar.setVisibility(View.VISIBLE);
        mprProgressDialog.show();
            RxClient.get(getApplicationContext()).otpResend(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                    new OtpResendReq(email, number), new Callback<OtpResendResponse>() {
                        @Override
                        public void success(OtpResendResponse otpResendResponse, Response response) {
                           Toast.makeText(OtpVerification.this,
                                    otpResendResponse.getMsg(), Toast.LENGTH_LONG).show();
                            Toast.makeText(OtpVerification.this, "OTP has been sent to registered mobile number",
                                    Toast.LENGTH_SHORT).show();
                            UserRegisterActivity.act.finish();
                            //progressBar.setVisibility(View.INVISIBLE);
                            mprProgressDialog.dismiss();

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            mprProgressDialog.dismiss();
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

                Toast.makeText(getApplicationContext(),"Wrong username and password",Toast.LENGTH_LONG).show();

            }
        });

    }



}
