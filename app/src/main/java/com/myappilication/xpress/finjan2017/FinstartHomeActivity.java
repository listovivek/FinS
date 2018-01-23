package com.myappilication.xpress.finjan2017;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.ccavenue.ServiceHandler;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.MyCamsActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSReq;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.GsonService;
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
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FinstartHomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton imageButton;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    WebView web;
    ProgressBar progressBar;

    NetConnectionDetector NDC;
    Context context;
    SearchView searchviewfaqlist;
    ImageView iv_searchiconall;
    String Search_query = "";
    int Index = 0;
    EditText et_searchcurhide;
    public static FinstartHomeActivity homeActivity;

    public static Dialog mprProgressDialog;

    OfflineDatabaseHelper offlineDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finstart_home_activity);

        String versionName = "";
        int versionCode = -1;

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //testDialog();


        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (MainActivity.login != null) {
                        MainActivity.login.finish();
                    }
                } catch (Exception e) {
                }

                finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = FinstartHomeActivity.this;
        homeActivity = FinstartHomeActivity.this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imageView = (ImageView) findViewById(R.id.imageView1);

        String img = "http://183.82.33.232:8094/Dashboard_Back.jpg";

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);

        // et_searchcurhide = (EditText) findViewById(R.id.edit_hidecur);

        //et_searchcurhide.setFocusable(true);

        // load image as Drawable

        searchviewfaqlist = (SearchView) findViewById(R.id.faq_searchall);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchviewfaqlist.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchviewfaqlist.setIconifiedByDefault(false);
        searchviewfaqlist.setFocusable(false);
        searchviewfaqlist.requestFocusFromTouch();

        //searchviewfaqlist.setInputType(InputType.TYPE_CLASS_TEXT);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        offlineDB = new OfflineDatabaseHelper(FinstartHomeActivity.this);


        searchviewfaqlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchviewfaqlist.setFocusableInTouchMode(true);
                return false;
            }
        });


        Picasso.with(getApplicationContext()).load(img).resize(2800, 1600).centerCrop().into(imageView);
        searchviewfaqlist.setIconified(false);

        ImageView searchViewIcon =
                (ImageView) searchviewfaqlist.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchViewIcon.setVisibility(View.GONE);

        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        linearLayoutSearchView.addView(searchViewIcon);
        searchViewIcon.setAdjustViewBounds(true);
        searchViewIcon.setMaxWidth(0);
        searchViewIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                (Gravity.RIGHT)));
        searchViewIcon.setImageDrawable(null);

        searchviewfaqlist.setFocusable(false);
        searchviewfaqlist.setFocusableInTouchMode(true);

        iv_searchiconall = (ImageView) findViewById(R.id.serchviewall_icon);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search_query = "";

                if (!Search_query.equals(query)) {
                    Search_query = query;

                    Index = 0;
                    if (NDC.isConnected(context)) {
                        if (Search_query.length() >= 3) {

                            searchviewfaqlist.clearFocus();

                            Intent intent = new Intent(FinstartHomeActivity.this, FaqListActivity.class);
                            intent.putExtra("Search_query", Search_query);


                            editor.putString("Search_query", Search_query);
                            editor.commit();

                            startActivity(intent);
                        } else {

                            Toast.makeText(context, "Minimum 3 character length Required", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }

                return false;
            }

            public boolean onQueryTextChange(String newText) {
                iv_searchiconall.setVisibility(View.GONE);

                if (newText.isEmpty()) {
                    iv_searchiconall.setVisibility(View.VISIBLE);
                }
                // this is your adapter that will be filtered
                return true;
            }
        };

        searchviewfaqlist.setOnQueryTextListener(queryTextListener);


        final ImageButton view = (ImageButton) findViewById(R.id.Try_finjan);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (NDC.isConnected(context)) {
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    startActivity(i);
                }else{
                    Toast.makeText(FinstartHomeActivity.this,
                            "Kindly check your network connection", Toast.LENGTH_LONG).show();
                }


            }

        });

        final TextView view1 = (TextView) findViewById(R.id.What_is_finstart);

        view1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(getApplicationContext(),Have_a_query.class);
                startActivity(i);*/
                if (NDC.isConnected(context)) {
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    startActivity(i);
                }else{
                    Toast.makeText(FinstartHomeActivity.this,
                            "Kindly check your network connection", Toast.LENGTH_LONG).show();
                }

            }
        });

        String temp_courseName = "Have a query - Ask Finpedia";
        SpannableString spanString = new SpannableString(temp_courseName);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);

        final TextView haveAQuery = (TextView) findViewById(R.id.have_a_Query);
        haveAQuery.setText(spanString);
        haveAQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(getApplicationContext(),Have_a_query.class);
                startActivity(i);*/

                Intent i = new Intent(getApplicationContext(), FaqCategroyLIstActivity.class);
                ModuleFinJan.courseID = "5";
                startActivity(i);
            }
        });
    }

    private void testDialog() {

        Dialog GuideDialog = new Dialog(FinstartHomeActivity.this);
        GuideDialog.setContentView(R.layout.testing_dialog);
        GuideDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //GuideDialog.setCancelable(false);
        GuideDialog.show();
    }

    private void validateCouponCode(String coupon_code) {

        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
        String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");

        RxClient.get(getApplicationContext()).couponbasedCourse(token, new CouponBSReq(email, coupon_code),
                new Callback<CouponBSResponse>() {
                    @Override
                    public void success(CouponBSResponse couponBSResponse, Response response) {

                        if (couponBSResponse.getStatus().equalsIgnoreCase("200")) {
                            String message = couponBSResponse.getModule_id();

                            if (message.equalsIgnoreCase("5")) {

                                Intent i = new Intent(getApplicationContext(),
                                        ListofModuleFinjan.class);
                                i.putExtra("moduleID", message);
                                ModuleFinJan.courseID = message;
                                ModuleFinJan.courseName = "Finstart";
                                startActivity(i);
                                finish();

                                editor.putString("couponbaseModuleid", message);
                                editor.commit();
                                //   Toast.makeText(TryFinStart.this, message, Toast.LENGTH_LONG).show();
                            } else {
                                Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            CouponBSResponse usere = (CouponBSResponse) error.getBodyAs(CouponBSResponse.class);

                            String message = usere.getModule_id();
                            if (message.equalsIgnoreCase("5")) {

                                Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                                i.putExtra("moduleID", message);
                                ModuleFinJan.courseID = message;
                                ModuleFinJan.courseName = "Finstart";
                                startActivity(i);
                                finish();

                                editor.putString("couponbaseModuleid", message);
                                editor.commit();
                                //   Toast.makeText(TryFinStart.this, message, Toast.LENGTH_LONG).show();
                            } else {
                                /*Toast.makeText(FinstartHomeActivity.this, "This Coupon code is not valid for finstart",
                                        Toast.LENGTH_LONG).show();*/
                                Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                                startActivity(i);

                            }

                        } catch (Exception e) {

                        }
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

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                return true;

            case R.id.learning_center:
                startActivity(new Intent(getApplicationContext(), Learning_centre.class));
                // finish();
                return true;

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                // finish();
                return true;

            case R.id.ss_selection:
                startActivity(new Intent(getApplicationContext(), Scheme.class));
                // finish();
                return true;

/*
            case R.id.my_cams:
                startActivity(new Intent(getApplicationContext(), MyCamsActivity.class));
                // finish();
                return true;
*/

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;

            case R.id.finstart_c:

                // String cModid = sharedpreferences.getString("couponbaseModuleid", "");

                // validateCouponCode(getIntent().getStringExtra("coupon_code"));
                  /*Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    startActivity(i);*/

                String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");
                //  String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");

                if (isusrgetModid.equalsIgnoreCase("5")) {
                    Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    startActivity(i);
                    //finish();

                    editor.putString("couponbaseModuleid", "5");
                    editor.commit();

                } else {
                   /* Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    startActivity(i);*/
                    if (NDC.isConnected(context)) {
                        isUserAlreadygetCoupon();
                    }else{
                        Toast.makeText(FinstartHomeActivity.this,
                                "Kindly check your network connection", Toast.LENGTH_LONG).show();
                    }


                }


                /*if(cModid.equalsIgnoreCase("5")){
                    Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    startActivity(i);

                }*/


               /* Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                i.putExtra("moduleID", "5");
                ModuleFinJan.courseID = "5";
                ModuleFinJan.courseName = "Finstart";
                startActivity(i);*/

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
                } else {
                    Toast.makeText(getApplicationContext(), "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

            case R.id.fin_support:
                startActivity(new Intent(getApplicationContext(), Support.class));
                return true;

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
                finish();

                editor.remove("couponbaseModuleid");
                editor.remove("isusergetmoduleid");
                editor.remove("isusergetexpdate");
                editor.apply();

                offlineDB.deleteAll();

                return true;
        }
        return false;
    }


    private void isUserAlreadygetCoupon() {

        ServiceHandler sh = new ServiceHandler();

        final String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
        String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Authorization", token));
        params.add(new BasicNameValuePair("email", email));

       // mtd_refresh_token();

       // progressBar.setVisibility(View.VISIBLE);

        mprProgressDialog.show();

      /*  MyAsyncTask mm = new MyAsyncTask();
        mm.execute();*/


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

                                if(mm_id.equalsIgnoreCase("5")){
                                    Log.d("", "");
                                    break;
                                }
                            }

                            editor.putString("buymodulename", mm_Name);
                            editor.putString("buymoduleamount", mm_amount);
                            editor.putString("buyamount", mm_id);
                            editor.commit();


                            Intent i = new Intent(getApplicationContext(), TryFinStart.class);

                            startActivity(i);

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


                                Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                                i.putExtra("moduleID", "5");
                                ModuleFinJan.courseID = "5";
                                ModuleFinJan.courseName = "Finstart";
                                startActivity(i);
                                //finish();

                                editor.putString("couponbaseModuleid", "5");
                                editor.commit();
                            }
                        }
                        mprProgressDialog.dismiss();
                        //progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("error", error.toString());
                        //progressBar.setVisibility(View.INVISIBLE);
                        try{
                            UserAlreadyCouponRes usere = (UserAlreadyCouponRes)
                                    error.getBodyAs(UserAlreadyCouponRes.class);



                            if(usere.getStatus().equalsIgnoreCase("402")){
                                mtd_refresh_token();
                            }else{
                                Toast.makeText(FinstartHomeActivity.this, error.toString(),
                                        Toast.LENGTH_LONG).show();
                                mprProgressDialog.dismiss();
                            }
                        }catch (Exception e){

                        }

                    }
                });

    }

    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(FinstartHomeActivity.this).Login(new loginreq(sharedpreferences.
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
                            isUserAlreadygetCoupon();
                           // progressBar.setVisibility(View.INVISIBLE);
                        }
                    };
                    handler.postDelayed(runnable, 500);

                }
            }

            @Override
            public void failure(RetrofitError error) {
               // progressBar.setVisibility(View.INVISIBLE);
                mprProgressDialog.dismiss();
                Log.d("refresh token", "refresh token error");
                Toast.makeText(FinstartHomeActivity.this, "Service not response",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }

    @Override
    protected void onResume() {
        if (searchviewfaqlist != null) {

            searchviewfaqlist.setQuery("", false);
            searchviewfaqlist.clearFocus();
            //searchviewfaqlist.onActionViewCollapsed();
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        /*if (searchviewfaqlist != null) {
            searchviewfaqlist.setQuery("", false);
            searchviewfaqlist.clearFocus();

        }
*/
        super.onResume();
    }

    public class MyAsyncTask extends AsyncTask<String, String, String> {

        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
        String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");


        GsonService sh = new GsonService();
        String result=null;

        @Override
        protected String doInBackground(String... params) {

            try {

                List<NameValuePair> mRequestParams = new ArrayList<NameValuePair>();
               // mRequestParams.add(new BasicNameValuePair("Authorization", token));
                mRequestParams.add(new BasicNameValuePair("email", email));

                HttpResponse httpResponse = null;
                if(mRequestParams != null){
                    HttpPost httpGet = new HttpPost(StaticConfig.ROOT_URL+"/getCourseForUser");

                    httpGet.setEntity(new UrlEncodedFormEntity(mRequestParams));
                    httpGet.addHeader("Authorization", token);
                    httpGet.setHeader("Content-type", "application/json");
                   // httpGet.setHeader("Content-type", "application/json");

                    DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());

                    httpResponse = httpClient.execute(httpGet);
                }else{

                }

                int statusCode = httpResponse.getStatusLine().getStatusCode();

                String reason = httpResponse.getStatusLine().getReasonPhrase();

                StringBuilder sb = new StringBuilder();
                if (statusCode == 200) {
                    HttpEntity entity = httpResponse.getEntity();
                    InputStream inputStream = entity.getContent();
                    BufferedReader bReader = new BufferedReader(
                            new InputStreamReader(inputStream, "UTF-8"), 8);
                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sb.append(line);
                    }
                } else {
                    sb.append(reason);
                }
               result = sb.toString();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }




}

