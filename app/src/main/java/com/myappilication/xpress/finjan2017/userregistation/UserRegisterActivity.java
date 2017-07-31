package com.myappilication.xpress.finjan2017.userregistation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.models.login.userreg.UserRegReq;
import com.myappilication.xpress.finjan2017.models.login.userreg.UserRegResponse;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 16/5/17.
 */
public class UserRegisterActivity extends Activity {

    EditText firstName, lastName, email, password, mobile_num, couponCode;
    String str_firstName, str_lastName, str_email, str_Password, str_mobile, str_coupon,company;
    ProgressBar progressBar;
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "[0-9+ ]{7,16}";

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    NetConnectionDetector NDC;
    Context context;

    OfflineDatabaseHelper offlineDB;

    //String str_userName;

    //EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg_activity);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        context = UserRegisterActivity.this;

        offlineDB = new OfflineDatabaseHelper(context);

        //userName = (EditText) findViewById(R.id.reg_username);
        firstName = (EditText) findViewById(R.id.reg_firstname);
        lastName = (EditText) findViewById(R.id.reg_lastname);
        email = (EditText) findViewById(R.id.reg_emailid);
        password = (EditText) findViewById(R.id.reg_password);

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mobile_num = (EditText) findViewById(R.id.reg_mobilenumber);
        couponCode = (EditText) findViewById(R.id.reg_couponcode);

        Button submit = (Button) findViewById(R.id.reg_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // str_userName = userName.getText().toString();
                str_firstName = firstName.getText().toString();
                str_lastName = lastName.getText().toString();
                str_email = email.getText().toString();
                str_Password = password.getText().toString();
                str_mobile = mobile_num.getText().toString();
                str_coupon = couponCode.getText().toString();
                //callWebService();
                validation();
            }
        });

    }

    private void validation() {
       // if(str_userName.length() != 0){
            if(str_firstName.length() != 0) {
                if(str_lastName.length() != 0) {
                    if (str_email.length() != 0) {
                        if (Pattern.matches(EMAIL_REGEX, str_email)) {
                            if (str_Password.length() != 0) {
                                if (str_Password.length() > 4) {
                                    if (str_mobile.length() != 0) {
                                        if (Pattern.matches(PHONE_REGEX, str_mobile)) {
                                           // if (str_coupon.length() != 0) {

                                            if (NDC.isConnected(context)){
                                                try{
                                                    offlineDB.deleteAll();
                                                    editor.remove("couponvalidation");
                                                    editor.commit();
                                                }catch (Exception e){

                                                }

                                                callWebService();
                                            }else{
                                                Toast.makeText(UserRegisterActivity.this,
                                                        "Kindly check your network connection",
                                                        Toast.LENGTH_LONG).show();
                                            }

                                                //Toast.makeText(UserRegisterActivity.this, "fin", Toast.LENGTH_LONG).show();
                                           /* } else {
                                                Toast.makeText(UserRegisterActivity.this, "Kindly give your coupon code",
                                                        Toast.LENGTH_LONG).show();

                                            }*/
                                        } else {
                                            Toast.makeText(UserRegisterActivity.this, "Enter a valid mobile number",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(UserRegisterActivity.this, "Kindly enter your mobile number",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(UserRegisterActivity.this, "password should be more than 4 letters",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(UserRegisterActivity.this, "Kindly enter your password",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(UserRegisterActivity.this, "Oops! Invalid Email ID!",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(UserRegisterActivity.this, "Kindly enter your valid email id",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(UserRegisterActivity.this, "Kindly enter your last name",
                            Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(UserRegisterActivity.this, "Kindly enter your first name",
                        Toast.LENGTH_LONG).show();
            }
        /*}else{
            Toast.makeText(UserRegisterActivity.this, "Kindly enter your name",
                    Toast.LENGTH_LONG).show();
        }*/
    }

    private void callWebService() {

        progressBar.setVisibility(View.VISIBLE);

        String tt = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");
        String temp = null;

        if(str_coupon.length() == 0){
            temp = "null";
        }else{
            temp = str_coupon;
        }

        RxClient.get(getApplicationContext()).userReg(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new UserRegReq(str_firstName, str_lastName, str_email,
                        str_Password, str_mobile, str_coupon), new Callback<UserRegResponse>() {
                    @Override
                    public void success(UserRegResponse userRegResponse, Response response) {
                        progressBar.setVisibility(View.INVISIBLE);
                       // Log.d("response", userRegResponse.getResult());
                        /*Toast.makeText(UserRegisterActivity.this, userRegResponse.getResult(),
                                Toast.LENGTH_LONG).show();*/
                        if(str_coupon.length() != 0){
                            if(str_coupon.equalsIgnoreCase("fst104")) {
                                editor.putString("couponvalidation", "fst104");
                            }
                        }

                        editor.putString(SharedPrefUtils.SpEmail,str_email);
                        editor.putString(SharedPrefUtils.SpPassword,str_Password);
                       // editor.putString(SharedPrefUtils.SpCompanyName,"Infosys");
                        editor.commit();



                        Toast.makeText(UserRegisterActivity.this, "OTP has been sent to registered mobile number", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(UserRegisterActivity.this, OtpVerification.class);
                        in.putExtra("email", str_email);
                        in.putExtra("mobile", str_mobile);
                        startActivity(in);
                        finish();

                        try{
                            MainActivity.login.finish();
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try{
                            UserRegResponse usere = (UserRegResponse) error.getBodyAs(UserRegResponse.class);
                           Toast.makeText(UserRegisterActivity.this, usere.getMsg(), Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(UserRegisterActivity.this, "Service not response", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        /*Intent in = new Intent(UserRegisterActivity.this, OtpVerification.class);
                        startActivity(in);*/
                    }
                });

    }


    private void mtd_refresh_token() {
        final String passwordd =sharedpreferences.getString(SharedPrefUtils.SpPassword, "");
        final String emailll =sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

        RxClient.get(UserRegisterActivity.this).Login(new loginreq(emailll,
                passwordd), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {

                //   progressBar.setVisibility(View.INVISIBLE);

                //Toast.makeText(getApplicationContext(),"sucesss",Toast.LENGTH_LONG).show();

                if (loginresp.getStatus().equals("200")){

                    editor.putBoolean(SharedPrefUtils.SpIsNewUser, false);

                    editor.putString(SharedPrefUtils.SpEmail, emailll);
                    editor.putString(SharedPrefUtils.SpPassword, passwordd);
                    editor.putString(SharedPrefUtils.SpId,loginresp.getDetails().getId());
                    editor.putString(SharedPrefUtils.SpUserName,loginresp.getDetails().getName());
                    editor.putString(SharedPrefUtils.SpFirstname,loginresp.getDetails().getFirstname());
                    editor.putString(SharedPrefUtils.Splastname,loginresp.getDetails().getLastname());
                    editor.putString(SharedPrefUtils.SpCompanyName, loginresp.getDetails().getCompany_name());
                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    Toast.makeText(getApplicationContext(),"Sucesssfully logged in ",Toast.LENGTH_LONG).show();
                    editor.commit();

                    Intent i = new Intent(UserRegisterActivity.this,ModuleFinJan.class);
                    startActivity(i);
                    finish();

                    //db.addUser(etusername.getText().toString(), etpassword.getText().toString());
                  /*  if(!db.Checkuser(emailll,etpassword.getText().toString())) {
                        db.addUser(emailll, etpassword.getText().toString());
                    }*/

                    // Toast.makeText(context, ""+db, Toast.LENGTH_LONG).show();

                }
                else {


                    Toast.makeText(UserRegisterActivity.this, "Try again ", Toast.LENGTH_LONG).show();
                }







            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }
}
