package com.myappilication.xpress.finjan2017;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.ccavenue.WebViewActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSReq;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.UserAlreadyCouponReq;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.UserAlreadyCouponRes;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.newfeedback.NewFeedbackActivity;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TryFinStart extends AppCompatActivity {

    WebView web;
    ProgressBar progressBar;

    NetConnectionDetector NDC;
    Context context;
    Toolbar toolbar;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    Button buy_fin;

    ProgressBar progressBar1;

    AlertDialog al;

    ProgressDialog dialog1;

    public static Dialog mprProgressDialog;

    boolean condition=false;

    OfflineDatabaseHelper offlineDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_finstart);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = TryFinStart.this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        dialog1 = new ProgressDialog(TryFinStart.this);

        buy_fin = (Button) findViewById(R.id.buy_finstart);

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);

        offlineDB = new OfflineDatabaseHelper(TryFinStart.this);

        buy_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NDC.isConnected(context)){
                    dialogst();
                }else{
                    Toast.makeText(TryFinStart.this,
                            "Kindly check your network connection", Toast.LENGTH_SHORT).
                            show();
                }

            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        try {
            //String couponcode = sharedpreferences.getString("couponbaseModuleid", "");

            String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");
            if(isusrgetModid.equalsIgnoreCase("5")){
                buy_fin.setVisibility(View.GONE);
                condition=true;
            }else{


                if (NDC.isConnected(context)) {
                    mprProgressDialog.show();
                    isUserAlreadygetCoupon();
                }else{
                    Toast.makeText(TryFinStart.this,
                            "Kindly check your network connection", Toast.LENGTH_LONG).show();
                }


            }
        }catch (Exception e){

        }


        web = (WebView) findViewById(R.id.commentsView);
       // progressBar = (ProgressBar) findViewById(R.id.progressBar);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(StaticConfig.html_Base+"finstart.html");
    }






    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            //progressBar.setVisibility(View.GONE);
           // progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            if(condition==true){
                mprProgressDialog.dismiss();
                condition=false;
            }

          // mprProgressDialog.dismiss();
           // progressBar.setVisibility(View.GONE);
          // buy_fin.setVisibility(View.VISIBLE);


        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private void dialogst() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TryFinStart.this);
        View bView = getLayoutInflater().inflate(R.layout.custom_couponcode, null);
        dialogBuilder.setView(bView);
        dialogBuilder.setCancelable(false);
        TextView title = (TextView) bView.findViewById(R.id.buy);
        TextView price = (TextView) bView.findViewById(R.id.price);

        String bAmount = sharedpreferences.getString("buymoduleamount", "");
        String bName = sharedpreferences.getString("buymodulename", "");

        if(bAmount.length()>0&&
                bAmount!=null){
            price.setText(bName +" - "+"₹"+bAmount);

        }
        String temp_courseName = "Buy Now";
        SpannableString spanString = new SpannableString(temp_courseName);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        title.setText(spanString);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(TryFinStart.this,
                        "Purchase developing process going on", Toast.LENGTH_LONG).show();*/
               // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Intent i = new Intent(TryFinStart.this, WebViewActivity.class);
               // i.putExtra("amount", getIntent().getStringExtra("mm_amount"));
                startActivity(i);

                InputMethodManager inputManager = (InputMethodManager)
                        context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });

        //title.setText(avCourseList.get(Integer.valueOf(pp)));


        Button discard_btn = (Button) bView.findViewById(R.id.discard_button);
        Button send_btn = (Button) bView.findViewById(R.id.send_button);

        progressBar1 = (ProgressBar) bView.findViewById(R.id.progressBar1);
        final EditText coupon = (EditText) bView.findViewById(R.id.edit1);

        al = dialogBuilder.create();
        al.show();

        discard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                al.dismiss();
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(coupon.getText().toString().length()>0){



                    //pushCoupon(coupon, al, mBottomSheetDialog);


                    validateCouponCode(coupon.getText().toString());

                    /*if(coupon.getText().toString().equalsIgnoreCase("fst104")){
                        editor.putString("couponvalidation", "fst104");
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                        i.putExtra("moduleID", "5");
                        ModuleFinJan.courseID = "5";
                        ModuleFinJan.courseName = "Finstart";
                        al.dismiss();
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(TryFinStart.this,
                                "Kindly give valid coupon code", Toast.LENGTH_LONG).show();
                    }*/

                }else{
                    Toast.makeText(TryFinStart.this,
                            "Kindly give your coupon code", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(WebViewActivity.status!=null&&
                WebViewActivity.status.length()>0){

           // dialog1 = new ProgressDialog(TryFinStart.this);

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TryFinStart.this);
            View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
            dialogBuilder.setView(bView);
            dialogBuilder.setCancelable(false);

            TextView title = (TextView) bView.findViewById(R.id.dialog_text);
            title.setText(WebViewActivity.status);

            Button discard_btn = (Button) bView.findViewById(R.id.feedback_okbtn);

            final AlertDialog ald = dialogBuilder.create();
            ald.show();

            discard_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(WebViewActivity.status.
                            equalsIgnoreCase("Sorry, Your transaction Cancelled!")){
                       /* ald.dismiss();
                        WebViewActivity.status=null;*/

                        //al.dismiss();
                        ald.dismiss();
                        WebViewActivity.status=null;

                       /* Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                        i.putExtra("moduleID", "5");
                        ModuleFinJan.courseID = "5";
                        ModuleFinJan.courseName = "Finstart";
                        startActivity(i);
                        finish();

                        isUserAlreadygetCoupon();*/

                    }else if(WebViewActivity.status.
                            equalsIgnoreCase("Thank you, Your transaction successfully completed!")) {
                        al.dismiss();
                        ald.dismiss();
                        WebViewActivity.status=null;


                       /* dialog1.setMessage("Please wait...");
                        dialog1.setCancelable(false);
                        dialog1.show();*/

                        Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                        i.putExtra("moduleID", "5");
                        ModuleFinJan.courseID = "5";
                        ModuleFinJan.courseName = "Finstart";
                        startActivity(i);
                        finish();

                      // editor.putString("isusergetmoduleid", "5");

                        isUserAlreadygetCoupon();
                    }else if(WebViewActivity.status.
                            equalsIgnoreCase("Sorry, Your transaction declined!")) {
                        ald.dismiss();
                        WebViewActivity.status=null;
                    }else if(WebViewActivity.status.
                            equalsIgnoreCase("Sorry, Your status not known!")) {
                        ald.dismiss();
                        WebViewActivity.status=null;
                    }

                    else{
                      //  al.dismiss();
                        ald.dismiss();
                        WebViewActivity.status=null;
                    }

                }
            });


        }
    }

    private void isUserAlreadygetCoupon() {

        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

        mprProgressDialog.show();

        RxClient.get(getApplicationContext()).getUserCourseDtls(sharedpreferences.
                        getString(SharedPrefUtils.SpRememberToken, ""), new UserAlreadyCouponReq(email),
                new Callback<UserAlreadyCouponRes>() {
                    @Override
                    public void success(UserAlreadyCouponRes userRes, Response response) {

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

                                if(mm_id.equalsIgnoreCase("5")) {
                                    Log.d("", "");
                                    break;
                                }
                            }

                            editor.putString("buymodulename", mm_Name);
                            editor.putString("buymoduleamount", mm_amount);
                            editor.putString("buyamount", mm_id);
                            editor.commit();

                            buy_fin.setVisibility(View.VISIBLE);

                        }else{
                           String modID=null;
                            String exp=null;
                            for(int t=0; t<userRes.getResult().getInfo().getRegistered().length; t++){
                                modID = userRes.getResult().getInfo().getRegistered()[t].getModule_id();
                                exp = userRes.getResult().getInfo().getRegistered()[t].getExpiry_date();
                            }
                            Log.d("module exp", exp);

                            if(modID!=null && exp!=null){
                                // db.setIsUsrgCoupon(email, modID, exp);
                                editor.putString("isusergetmoduleid", modID);
                                editor.putString("isusergetexpdate", exp);
                                editor.commit();
                            }
                        }

                       //dialog1.dismiss();
                        try{
                            mprProgressDialog.dismiss();
                        }catch (Exception e){
                            Log.d("progress bar error", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("e", "e");

                        try{
                            UserAlreadyCouponRes usere = (UserAlreadyCouponRes)
                                    error.getBodyAs(UserAlreadyCouponRes.class);
                            if(usere.getStatus().equalsIgnoreCase("402")){
                                mtd_refresh_token(null);
                            }
                        }catch (Exception e){

                        }
                    }
                });
    }


    private void validateCouponCode(final String coupon_code) {

        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
        String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken,"");
       // progressBar1.setVisibility(View.VISIBLE);

        mprProgressDialog.show();

        RxClient.get(getApplicationContext()).couponbasedCourse(token, new CouponBSReq(email, coupon_code),
                new Callback<CouponBSResponse>() {
                    @Override
                    public void success(CouponBSResponse couponBSResponse, Response response) {

                        if(couponBSResponse.getStatus().equalsIgnoreCase("200")){

                            String message = couponBSResponse.getModule_id();

                            if(message.equalsIgnoreCase("5")){
                                Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                                i.putExtra("coupon_image", couponBSResponse.getCoupon_image());

                                if(couponBSResponse.getCoupon_image()!=null){
                                    editor.putString("coupon_image", couponBSResponse.getCoupon_image());
                                    editor.commit();
                                }

                                i.putExtra("moduleID", message);
                                ModuleFinJan.courseID = message;
                                ModuleFinJan.courseName = "Finstart";
                                startActivity(i);
                                finish();

                                isUserAlreadygetCoupon();


                               // progressBar1.setVisibility(View.GONE);


                                InputMethodManager inputManager = (InputMethodManager)
                                        context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                               /* editor.putString("couponbaseModuleid", message);
                                editor.commit();*/
                             //   Toast.makeText(TryFinStart.this, message, Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(TryFinStart.this, "Invalid coupon code",
                                        Toast.LENGTH_LONG).show();
                               // progressBar1.setVisibility(View.GONE);
                                mprProgressDialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try{
                            CouponBSResponse usere = (CouponBSResponse) error.getBodyAs(CouponBSResponse.class);

                           // String message = usere.getModule_id();
                            String m = usere.getMsg();

                            Toast.makeText(TryFinStart.this, m,
                                    Toast.LENGTH_LONG).show();



                            InputMethodManager inputManager = (InputMethodManager)
                                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                            if(usere.getStatus().equalsIgnoreCase("402")){
                                mtd_refresh_token(coupon_code);
                            }else{
                                //progressBar1.setVisibility(View.GONE);
                                mprProgressDialog.dismiss();
                            }

                            /*if(message.equalsIgnoreCase("5")){
                                //   Toast.makeText(TryFinStart.this, message, Toast.LENGTH_LONG).show();
                            }else{

                            }*/
                        }catch (Exception e){
                            Log.d("", "");
                        }
                    }
                });
    }

    private void mtd_refresh_token(final String coupon_code) {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(TryFinStart.this).Login(new loginreq(sharedpreferences.
                getString(SharedPrefUtils.SpEmail, ""),
                sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {

                if (loginresp.getStatus().equals("200")) {

                    editor.putString(SharedPrefUtils.SpRememberToken, loginresp.getToken().toString());
                    editor.commit();

                    final Handler handler = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if(coupon_code!=null){
                                validateCouponCode(coupon_code);
                            }else{
                                isUserAlreadygetCoupon();
                            }

                           // progressBar1.setVisibility(View.GONE);
                        }
                    };
                    handler.postDelayed(runnable, 500);

                }
            }

            @Override
            public void failure(RetrofitError error) {
               // progressBar1.setVisibility(View.INVISIBLE);
                mprProgressDialog.dismiss();
                Log.d("refresh token", "refresh token error");
                Toast.makeText(TryFinStart.this, "Service not response",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }


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

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";

                return true;

            case R.id.finstart_c:
                String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");
                //  String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");

                if(isusrgetModid.equalsIgnoreCase("5")){
                    Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    startActivity(i);
                }else{

                    /*Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    startActivity(i);*/
                    Toast.makeText(TryFinStart.this, "Please purchase finstart course to proceed",
                            Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                // finish();
                return true;

            case R.id.ss_selection:
                startActivity(new Intent(getApplicationContext(), Scheme.class));
                // finish();
                return true;

            case R.id.learning_center:
                startActivity(new Intent(getApplicationContext(), Learning_centre.class));
                // finish();
                return true;

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;


            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                return true;
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/

            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;

            case R.id.feedback:
                if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), NewFeedbackActivity.class));
                    return true;
                }else{
                    Toast.makeText(getApplicationContext(), "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

            /*case R.id.feedback_list:
                OfflineFeedbackDB feedbackDB = new OfflineFeedbackDB(this);
                String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
                ArrayList<String> list = feedbackDB.getdata(em);
                if(list.size() > 0){
                    startActivity(new Intent(getApplicationContext(), UserFeedbackList.class));
                    return true;
                }else{
                    Toast.makeText(getApplicationContext(), "No records", Toast.LENGTH_SHORT).show();
                    return false;
                }*/
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

                editor.remove("couponbaseModuleid");
                editor.remove("isusergetmoduleid");
                editor.remove("isusergetexpdate");
                editor.apply();

                offlineDB.deleteAll();

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
