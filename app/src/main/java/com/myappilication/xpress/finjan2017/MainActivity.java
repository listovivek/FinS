package com.myappilication.xpress.finjan2017;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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

import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSReq;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.UserAlreadyCouponReq;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.UserAlreadyCouponRes;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
import com.myappilication.xpress.finjan2017.userregistation.UserRegisterActivity;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
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

    public static Dialog mprProgressDialog;

    public static boolean fullsrnCon = false;
    public static MainActivity login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db=new DatabaseHandler(this);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);

        try {
            int vName = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            Log.d("version name", ""+vName);

            /*MyAsyncTa mm = new MyAsyncTa();
            mm.execute();*/




        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


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


    public class MyAsyncTa extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String version = null;
            try {
                version = Jsoup.connect("https://play.google.com/store/apps/details?id="
                        + MainActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();

                Log.d("version name", version);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return version;
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {


            }

        }
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
     //   progressBar.setVisibility(View.VISIBLE);
        mprProgressDialog.show();
        RxClient.get(MainActivity.this).Login(new loginreq(etusername.getText().toString().trim(),
                etpassword.getText().toString().trim()), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {

              // progressBar.setVisibility(View.INVISIBLE);

                //Toast.makeText(getApplicationContext(),"sucesss",Toast.LENGTH_LONG).show();

                if (loginresp.getStatus().equals("200")){

                    editor.putBoolean(SharedPrefUtils.SpIsNewUser, false);

                    editor.putString(SharedPrefUtils.SpEmail, etusername.getText().toString().trim());
                    editor.putString(SharedPrefUtils.SpPassword, etpassword.getText().toString().trim());
                    editor.putString(SharedPrefUtils.SpId,loginresp.getDetails().getId());
                    editor.putString(SharedPrefUtils.SpUserName,loginresp.getDetails().getName());
                    editor.putString(SharedPrefUtils.SpFirstname,loginresp.getDetails().getFirstname());
                    editor.putString(SharedPrefUtils.Splastname,loginresp.getDetails().getLastname());
                    editor.putString(SharedPrefUtils.SpCompanyName, loginresp.getDetails().getCompany_name());
                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    Toast.makeText(getApplicationContext(),"Sucesssfully logged in ",Toast.LENGTH_LONG).show();
                    editor.commit();

                    if(getIntent().getStringExtra("coupon_code")!=null&&getIntent()
                            .getStringExtra("coupon_code").length()>0){
                        validateCouponCode(getIntent().getStringExtra("coupon_code"));


                    }else{
                    }

                    OfflineDatabaseHelper offlinedB = new OfflineDatabaseHelper(MainActivity.this);
                    int cursor = offlinedB.checkinDatabase();
                    if(cursor==0){
                    }

                    ListofModuleFinjan.course_ID = "5";

                    if(loginresp.getDetails().getFinished_course_details() != null){
                        for(int n=0; n<loginresp.getDetails().getFinished_course_details().length; n++){
                            offlinedB.setMCQcompleted(loginresp.getDetails().getFinished_course_details()[n].
                                    getCourse_id(), "true");

                            offlinedB.setCalcCompleted(loginresp.getDetails().getFinished_course_details()[n].
                                    getCourse_id(), "true");

                            offlinedB.setFaqCompleted(loginresp.getDetails().getFinished_course_details()[n].
                                    getCourse_id(), "true");

                            offlinedB.setvideoComplete(loginresp.getDetails().getFinished_course_details()[n].
                                    getCourse_id(), "true");

                        }
                    }

                   /* if(getIntent().getStringExtra("coupon_code")!=null&&getIntent()
                            .getStringExtra("coupon_code").length()>0){

                       // Toast.makeText(MainActivity.this, "dibe ", Toast.LENGTH_LONG).show();
                        validateCouponCode(getIntent().getStringExtra("coupon_code"));
                    }*/

                    db.addUser(etusername.getText().toString().trim(), etpassword.getText().toString().trim());
                   if(!db.Checkuser(etusername.getText().toString().trim(),etpassword.getText().toString().trim())) {
                        db.addUser(etusername.getText().toString().trim(), etpassword.getText().toString().trim());

                    }

                    Intent i = new Intent(MainActivity.this, SplashActivity.class);

                    i.putExtra("coupon_code", getIntent().getStringExtra("coupon_code"));
                    startActivity(i);
                    finish();

                    // Toast.makeText(context, ""+db, Toast.LENGTH_LONG).show();

                    isUserAlreadygetCoupon();
                    mprProgressDialog.dismiss();
                }

                else {
                    Toast.makeText(MainActivity.this, loginresp.getError(), Toast.LENGTH_LONG).show();
                    mprProgressDialog.dismiss();
                }
            }

            @Override
            public void failure(RetrofitError error) {

                try{
                    mprProgressDialog.dismiss();

                   // progressBar.setVisibility(View.INVISIBLE);
                    loginresp usere = (loginresp) error.getBodyAs(loginresp.class);
                  Toast.makeText(MainActivity.this, usere.getError(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Service not response", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        });

    }

    private void isUserAlreadygetCoupon() {

        final String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

        RxClient.get(getApplicationContext()).getUserCourseDtls(sharedpreferences.
                        getString(SharedPrefUtils.SpRememberToken, ""), new UserAlreadyCouponReq(email),
                new Callback<UserAlreadyCouponRes>() {
                    @Override
                    public void success(UserAlreadyCouponRes userRes, Response response) {
                        Log.d("response successful", "200");

                        int res = userRes.getResult().getInfo().getRegistered().length;

                        if(res == 0){
                            Log.d("e", "e");
                            String mm_id =null;
                            String mm_Name = null;
                            String mm_amount = null;
                            for(int i=0; i<userRes.getResult().getInfo().getPendingCourses().length; i++){
                                mm_Name = userRes.getResult().getInfo().getPendingCourses()[i].getModule_name();
                                mm_amount = userRes.getResult().getInfo().getPendingCourses()[i].getModule_amount();
                                mm_id = userRes.getResult().getInfo().getPendingCourses()[i].getId();

                                if(mm_id.equalsIgnoreCase("5")){
                                    Log.d("", "");
                                    break;
                                }

                            }

                            editor.putString("buymodulename", mm_Name);
                            editor.putString("buymoduleamount", mm_amount);
                            editor.putString("buyamount", mm_id);
                            editor.commit();

                        }else{
                            String modID=null;
                            String exp=null;
                            for(int t=0; t<userRes.getResult().getInfo().getRegistered().length; t++){
                                modID = userRes.getResult().getInfo().getRegistered()[t].getModule_id();
                                exp = userRes.getResult().getInfo().getRegistered()[t].getExpiry_date();
                            }
                            if(modID!=null && exp!=null){
                               // db.setIsUsrgCoupon(email, modID, exp);
                                editor.putString("isusergetmoduleid", modID);
                                editor.putString("isusergetexpdate", exp);
                                editor.commit();
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("error", error.toString());
                    }
                });

    }


    private void validateCouponCode(String coupon_code) {

        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

        RxClient.get(getApplicationContext()).couponbasedCourse(sharedpreferences.
                        getString(SharedPrefUtils.SpRememberToken, ""), new CouponBSReq(email, coupon_code),
                new Callback<CouponBSResponse>() {
                    @Override
                    public void success(CouponBSResponse couponBSResponse, Response response) {

                        if(couponBSResponse.getStatus().equalsIgnoreCase("200")){
                            String moduleId = couponBSResponse.getModule_id();

                            if(moduleId.equalsIgnoreCase("5")){
                                editor.putString("isusergetmoduleid", moduleId);
                                editor.commit();
                            }

                           // Log.d("reg_coupon_code", couponBSResponse.getCoupon_image());
                            if(couponBSResponse.getCoupon_image()!=null){
                                editor.putString("reg_m_coupon_image", couponBSResponse.getCoupon_image());
                                editor.putString("reg_d_coupon_image", couponBSResponse.getCoupon_image());
                                editor.commit();
                            }

                            // Toast.makeText(FinstartHomeActivity.this, message, Toast.LENGTH_LONG).show();

                        }
                        /*else if(couponBSResponse.getStatus().equalsIgnoreCase("401")){
                            try{
                                String message = couponBSResponse.getMsg();
                                Toast.makeText(MainActivity.this, message,
                                        Toast.LENGTH_LONG).show();
                            }catch (Exception e){

                            }
                        }*/
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try{
                            CouponBSResponse usere = (CouponBSResponse) error.getBodyAs(CouponBSResponse.class);

                            String message = usere.getModule_id();

                            if(message.equalsIgnoreCase("5")){

                                //   Toast.makeText(TryFinStart.this, message, Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(MainActivity.this, "This Coupon code is not valid for finstart",
                                        Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){

                        }
                    }
                });
    }



}
