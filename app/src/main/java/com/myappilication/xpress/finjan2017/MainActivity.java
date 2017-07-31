package com.myappilication.xpress.finjan2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.userregistation.UserRegisterActivity;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    TextView tv_forgetpass,tv_register;
    ImageView iv_logo;
    EditText etusername,etpassword;
    Button bt_login;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String remember_token ="";
    NetConnectionDetector NDC;
    Context context;
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String str_email,str_password,str_offemail,str_offpass;

    Boolean isLoggedin = false;

    ProgressBar progressBar;

    DatabaseHandler db;

    public static boolean fullsrnCon = false;
    public static MainActivity login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db=new DatabaseHandler(this);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        login = MainActivity.this;

        Log.d("date", dateFormat.format(date));
        iv_logo = (ImageView) findViewById(R.id.imageView);
        etusername = (EditText) findViewById(R.id.et_username_login);
        etpassword = (EditText) findViewById(R.id.et_pass_login);
        tv_forgetpass = (TextView) findViewById(R.id.tv_forget_pass);
        bt_login = (Button) findViewById(R.id.bt_login);


        tv_register = (TextView) findViewById(R.id.text_register);
        context = getApplicationContext();
        NDC = new NetConnectionDetector();
       /*etusername.setText("pool@gmail.com");
        etpassword.setText("1234567");*/

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        tv_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ForgotPasswordActivity.class);
                startActivity(i);
              //  finish();
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ireg=new Intent(MainActivity.this, UserRegisterActivity.class);
                startActivity(ireg);
            }
        });



        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        //vivekkk

        Boolean NewUser = sharedpreferences.getBoolean(SharedPrefUtils.SpIsNewUser, true);

        str_offemail = sharedpreferences.getString(SharedPrefUtils.SpEmail,"");
        str_offpass = sharedpreferences.getString(SharedPrefUtils.SpPassword,"");


            if (NDC.isConnected(context)) {
                if (!NewUser){
                Intent imodule = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(imodule);
                finish();

            }

        }else if (!str_offemail.equals("")|| !str_offpass.equals("")){


                Intent i = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(i);
                finish();


            }





        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NDC.isConnected(context)){

                    str_email = etusername.getText().toString();
                    str_password = etpassword.getText().toString();
                    validation();

                }  else if(db.Checkuser(etusername.getText().toString(),etpassword.getText().toString())){
                        editor.putString(SharedPrefUtils.SpEmail, etusername.getText().toString());
                        editor.putString(SharedPrefUtils.SpPassword, etpassword.getText().toString());
                        editor.commit();
                        Intent i = new Intent(MainActivity.this,SplashActivity.class);
                        startActivity(i);
                        finish();
                    Toast.makeText(getApplicationContext(),"Sucesssfully logged in ",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(context, "Invalid username and password", Toast.LENGTH_SHORT).show();
                }





            }
        });

    }

    public void validation(){
        if (str_email.length()> 0) {
            if (Pattern.matches(EMAIL_REGEX, str_email)){
                if (str_password.length() != 0){
                    getlogindata();
                }else {
                    Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
                }
            }else
            {
                Toast.makeText(context, "Please enter a valid email id", Toast.LENGTH_SHORT).show();
            }

        }else
        {
            Toast.makeText(context, "Please enter your email id", Toast.LENGTH_SHORT).show();
        }
    }



    public void  getlogindata(){
        progressBar.setVisibility(View.VISIBLE);
        RxClient.get(MainActivity.this).Login(new loginreq(etusername.getText().toString(),
                etpassword.getText().toString()), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {

               progressBar.setVisibility(View.INVISIBLE);

                //Toast.makeText(getApplicationContext(),"sucesss",Toast.LENGTH_LONG).show();

                if (loginresp.getStatus().equals("200")){

                    editor.putBoolean(SharedPrefUtils.SpIsNewUser, false);

                    editor.putString(SharedPrefUtils.SpEmail, etusername.getText().toString());
                    editor.putString(SharedPrefUtils.SpPassword, etpassword.getText().toString());
                    editor.putString(SharedPrefUtils.SpId,loginresp.getDetails().getId());
                    editor.putString(SharedPrefUtils.SpUserName,loginresp.getDetails().getName());
                    editor.putString(SharedPrefUtils.SpFirstname,loginresp.getDetails().getFirstname());
                    editor.putString(SharedPrefUtils.Splastname,loginresp.getDetails().getLastname());
                    editor.putString(SharedPrefUtils.SpCompanyName, loginresp.getDetails().getCompany_name());
                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    Toast.makeText(getApplicationContext(),"Sucesssfully logged in ",Toast.LENGTH_LONG).show();
                    editor.commit();

                    Intent i = new Intent(MainActivity.this, SplashActivity.class);
                    startActivity(i);
                    finish();

                    db.addUser(etusername.getText().toString(), etpassword.getText().toString());
                   if(!db.Checkuser(etusername.getText().toString(),etpassword.getText().toString())) {
                        db.addUser(etusername.getText().toString(), etpassword.getText().toString());

                    }

                   // Toast.makeText(context, ""+db, Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(MainActivity.this, "Try again ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                try{
                    progressBar.setVisibility(View.INVISIBLE);
                    loginresp usere = (loginresp) error.getBodyAs(loginresp.class);
                  Toast.makeText(MainActivity.this, usere.getError(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Service not response", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });

    }



}
