package com.myappilication.xpress.finjan2017.termscondition;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.myappilication.xpress.finjan2017.ChangePassword;
import com.myappilication.xpress.finjan2017.EvelouationAdapter;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.TryFinStart;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McQData;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.profileedit.profilereq;
import com.myappilication.xpress.finjan2017.models.login.profileedit.profileresp;
import com.myappilication.xpress.finjan2017.models.login.profileupdate.profileupdatereq;
import com.myappilication.xpress.finjan2017.models.login.profileupdate.profileupdateresp;
import com.myappilication.xpress.finjan2017.models.login.pushnotification.NotifyConfig;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.ArrayList;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Support extends AppCompatActivity {



    Button btn_save_changes, btn_edit_profile;

    EditText et_emailid, et_fname, et_lname, et_password, et_companyname, et_userquery;
    Button btn_userquery;
    //EditText et_username;
  //  Boolean isSearchtoakenExpired = false;



    NetConnectionDetector NCD;
    Context context;
    ProgressBar progressBar;
    Intent newintent;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Toolbar toolbar;
    ImageButton imageButton;

    private GoogleApiClient client;

    public static ArrayList<Activity> profile_Act_list = new ArrayList<>();;

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);

        NCD = new NetConnectionDetector();

        profile_Act_list.add(Support.this);

        context = getApplicationContext();


        TextView versionname = (TextView) findViewById(R.id.version_name);

        try {
           // int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            String vName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionname.setText("Current Version: "+vName);
            Log.d("", "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        et_userquery = (EditText) findViewById(R.id.edit_userquery);

        btn_userquery = (Button) findViewById(R.id.button_support);

        btn_userquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_userquery.length() == 0) {

                    et_userquery.setError("Please enter your comments");
                }else
                {

                    Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto"," support@finsafe.in", null));

                    email.putExtra(Intent.EXTRA_SUBJECT, "Finsafe Support");
                    email.putExtra(Intent.EXTRA_TEXT,
                            et_userquery.getText().toString());


                    email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(email);

                    final Handler handler = new Handler();
                    final Runnable r = new Runnable()
                    {
                        public void run()
                        {
                            et_userquery.setText("");
                        }
                    };
                    handler.postDelayed(r, 5000);
                }

            }
        });




        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();






        toolbar = (Toolbar) findViewById(R.id.toolbar);

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

















        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onBackPressed() {

       this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
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
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    startActivity(i);

                }
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

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                // finish();
                return true;

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;

            /*case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

            case R.id.feedback:
                if (NCD.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    return true;
                }else{
                    Toast.makeText(Support.this, "Kindly check your network connection",
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
                    Toast.makeText(ProfileSetting.this, "No records", Toast.LENGTH_SHORT).show();
                    return false;
                }*/

           /* case R.id.profile_menu:
               *//* startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                return true;*/
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                if(ModuleFinJan.modFinjan != null){
                    ModuleFinJan.modFinjan.finish();
                }
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

      //  getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }


    private void getprofiledatas(){

        RxClient.get(context).Editprofile(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new profilereq(sharedpreferences.getString(SharedPrefUtils.SpEmail,"") ),
                    new Callback<profileresp>() {

            @Override
            public void success(profileresp profileresp, Response response) {


if (profileresp.getStatus().equals("200")){

    progressBar.setVisibility(View.GONE);



    editor.putString(SharedPrefUtils.SpFirstname,profileresp.getResult().getInfo().getLists().getFirstname());
    editor.putString(SharedPrefUtils.Splastname,profileresp.getResult().getInfo().getLists().getLastname());
    editor.putString(SharedPrefUtils.SpEmail, profileresp.getResult().getInfo().getLists().getEmail());
    //editor.putString(SharedPrefUtils.SpCompanyName, profileresp.getResult().getInfo().getLists().getCompany_name());
    editor.putString(SharedPrefUtils.SpId,profileresp.getResult().getInfo().getLists().getId());
     editor.putString(SharedPrefUtils.SpUserName,profileresp.getResult().getInfo().getLists().getName());
    editor.putString(SharedPrefUtils.SpExpDate,profileresp.getResult().getInfo().getLists().getExp_date());
    editor.commit();

        et_fname.setText(profileresp.getResult().getInfo().getLists().getFirstname());
        et_lname.setText(profileresp.getResult().getInfo().getLists().getLastname());
        //et_username.setText(profileresp.getResult().getInfo().getLists().getName());
        et_emailid.setText(profileresp.getResult().getInfo().getLists().getEmail());

        for (int i = 0; i < profileresp.getResult().getInfo().getCorporates().length; i++) {
            /*Log.d("company name",profileresp.getResult().getInfo().getCorporates()[i].getCompany_name());
            if(profileresp.getResult().getInfo().getLists().getCompany_name().equals( profileresp.getResult().getInfo().getCorporates()[i].getId())){
               // et_companyname.setText(profileresp.getResult().getInfo().getCorporates()[i].getCompany_name());
               *//* editor.putString(SharedPrefUtils.SpCompanyName,profileresp.getResult().getInfo().getCorporates()[i].getCompany_name());
                editor.commit();*//*
            }*/
        }
}
            }

            @Override
            public void failure(RetrofitError error) {
               // Toast.makeText(getApplicationContext(),"failure t",Toast.LENGTH_LONG).show();

            }
        });




    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProfileSetting Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /*public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }*/


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotifyConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotifyConfig.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

  /*  private void mtd_refresh_token() {
       // Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();
        RxClient.get(ProfileSetting.this).Login(new loginreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {




                if (loginresp.getStatus().equals("200")){


                 //   Toast.makeText(getApplicationContext(),"sucesss"+loginresp.getToken().toString(),Toast.LENGTH_LONG).show();

                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    editor.commit();
                    *//*adapter.notifyDataSetChanged();*//*
                    if(isSearchtoakenExpired) {
                        getprofiledatas();
                    }
                }







            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(),"Please Enter All Details",Toast.LENGTH_LONG).show();

            }
        });

    }*/
}
