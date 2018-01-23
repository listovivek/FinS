package com.myappilication.xpress.finjan2017;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.feedback.OfflineFeedbackDB;
import com.myappilication.xpress.finjan2017.feedback.UserFeedbackList;
import com.myappilication.xpress.finjan2017.feedpost.FB_Posts;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;
import com.myappilication.xpress.finjan2017.models.login.faq.faqreq;
import com.myappilication.xpress.finjan2017.models.login.faq.faqresp;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.helpers.StaticConfig;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.searchfaq.searchreq;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.newfeedback.NewFeedbackActivity;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CalcModuleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CalcAdapter adapter;
    Boolean isSearchtoakenExpired = false;
    //calclistdatas listDatas;
    Context context;
    //List<calclistdatas> Calc = new ArrayList<>();
    int Index = 0;

    ProgressBar pb;
    public static ArrayList<String> wordList = new ArrayList<String>();


    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    NetConnectionDetector NDC;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Toolbar toolbar;
    ImageButton imageButton,btn_nb_nav;
    ArrayList<String> Calc = new ArrayList<String>();
    String Module_id;
    String word;
    String calcppf;

    public static CalcModuleActivity calcModuleActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_modules);
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final String Cal = sharedpreferences.getString("calc", "");
        Calc.add(Cal);
        Module_id = sharedpreferences.getString("cmoduleID", "");

        calcModuleActivity = CalcModuleActivity.this;

        wordList.clear();
        String[] calcname = Cal.split(",");
        List<String> newList = Arrays.asList(calcname);
        String nn = newList.get(0);
        if(nn.equalsIgnoreCase("null")){

        }else{
            wordList.addAll(newList);
        }


        editor.putString("wordList", wordList.toString());
        calcppf = sharedpreferences.getString(SharedPrefUtils.SpCalcPPF,"");
        editor.putString("calcppf",calcppf.toString());
        editor.commit();


        context = getApplicationContext();

        NDC = new NetConnectionDetector();

           recyclerView = (RecyclerView) findViewById(R.id.calc_card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);



       toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null




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





        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);











        if (NDC.isConnected(context)) {
            mtd_cac_module();


        }


       /* if (isNetworkAvailable(context)){


        }else
        {
            adapter = new FaqAdapter(db.getAllContacts());
            recyclerView.setAdapter(adapter);

            Toast.makeText(context, "You are using offline data", Toast.LENGTH_SHORT).show();



        }*/



        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                return true;

            case R.id.fin_support:
                startActivity(new Intent(getApplicationContext(), Support.class));
                return true;

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                finish();
                return true;

            case R.id.finstart_c:
                String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");
                //  String isusrgetModid = sharedpreferences.getString("isusergetmoduleid", "");

                if(isusrgetModid.equalsIgnoreCase("5")){
                    Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    finish();
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    finish();
                    startActivity(i);

                }
                return true;

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                finish();
                // finish();
                return true;

            case R.id.ss_selection:
                startActivity(new Intent(getApplicationContext(), Scheme.class));
                 finish();
                return true;

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                 finish();
                return true;*/

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                finish();
                return true;


           /* case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                finish();
                return true;
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                finish();
                return true;

            case R.id.feedback:
                if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), NewFeedbackActivity.class));
                    finish();
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

                finish();
                return true;

        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }




    private void mtd_cac_module() {
        //word = sharedpreferences.getString("wordList", "");
        //Toast.makeText(context, "Bitch"+word, Toast.LENGTH_SHORT).show();



        adapter = new CalcAdapter(wordList,context, getIntent().getStringExtra("list_of_module_id"));
        recyclerView.setAdapter(adapter);
       /* calcppf = sharedpreferences.getString(SharedPrefUtils.SpCalcPPF,"");
        editor.putString("calcppf",calcppf.toString());
        editor.commit();*/

    }

    private void mtd_refresh_token() {

        RxClient.get(CalcModuleActivity.this).Login(new loginreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {




                if (loginresp.getStatus().equals("200")){


                   // Toast.makeText(getApplicationContext(),"sucesss"+loginresp.getToken().toString(),Toast.LENGTH_LONG).show();

                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    editor.commit();
                    /*adapter.notifyDataSetChanged();*/
                   if(isSearchtoakenExpired) {
                       mtd_cac_module();
                   }
                }







            }

            @Override
            public void failure(RetrofitError error) {

                //Toast.makeText(getApplicationContext(),"Wrong Username And Password",Toast.LENGTH_LONG).show();

            }
        });

    }






   /* private void getSearchData(){
      RxClient.get(context).Searchview(remember_token, new searchreq(StaticConfig.faqModule, Search_query), new Callback<searchresp>() {
          @Override
          public void success(searchresp searchresp, Response response) {


              if (searchresp.equals("200")) {

                  for (int i = 0; i < searchresp.getResult().getInfo().length; i++) {

                      listDatas = new Faqlistdatas(searchresp.getResult().getInfo()[i].getFaq_qus(),searchresp.getResult().getInfo()[i].getFaq_ans()
                              );
                      list.add(listDatas);
                      Toast.makeText(context, "Searched"+listDatas, Toast.LENGTH_SHORT).show();
                  }

                  adapter = new FaqAdapter(list);
                  recyclerView.setAdapter(adapter);
                  db.OnDelete();
                  db.addContact(list);
                  Toast.makeText(context, "Successfully added in the database", Toast.LENGTH_SHORT).show();

                          *//*  if(1==0) {
                                adapter = new FaqAdapter(list);
                                recyclerView.setAdapter(adapter);
                                db.OnDelete();
                                db.addContact(list);
                            }else
                            {
                            }*//*
              }

          }

          @Override
          public void failure(RetrofitError error) {

          }
      });

    }*/



    /*public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }*/

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Faq Page") // TODO: Define a title for the content shown.
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
        mtd_cac_module();

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
}
