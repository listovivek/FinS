package com.myappilication.xpress.finjan2017;

import android.app.Activity;
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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;
import com.myappilication.xpress.finjan2017.models.login.faq.faqreq;
import com.myappilication.xpress.finjan2017.models.login.faq.faqresp;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.models.login.searchfaq.searchreq;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqOfflineDatabase;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FaqActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<faqresp> data;
    private FaqAdapter adapter;
    Boolean isSearchtoakenExpired = false;
    Faqlistdatas listDatas;
    Context context;
    List<Faqlistdatas> list = new ArrayList<>();
    DatabaseHandler db;
    SearchView searchviewfaq;
    int Index = 0;
    String Search_query = "";

    ProgressBar pb;
    TextView tv_profile, tv_finjancourses,tv_calculator, tv_faq,tv_finjan,tv_signout;
    FaqCategroyLIstActivity FCL;


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

    public static ArrayList<Activity> faq_act = new ArrayList<>();

    OfflineDatabaseHelper mOfflineDatabaseHelper;
    Button btn;

    FaqOfflineDatabase offlineDatabase;


    ImageView iv_searchicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("oncreate", "oncreate call");
        setContentView(R.layout.faq);
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        mOfflineDatabaseHelper = new OfflineDatabaseHelper(FaqActivity.this);

        offlineDatabase = new FaqOfflineDatabase(FaqActivity.this);

        db = new DatabaseHandler(this);
        faq_act.add(FaqActivity.this);

        TextView txt = (TextView) findViewById(R.id.faq_txt);
        String faqName = getIntent().getStringExtra("faqname");
        txt.setText(faqName);

        context = getApplicationContext();

        NDC = new NetConnectionDetector();

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);



        /*btn = (Button) findViewById(R.id.faq_finished);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*if(DashBoard.mDashBoard != null){
                    DashBoard.mDashBoard.finish();
                }
                mOfflineDatabaseHelper.setFaqCompleted(getIntent().getStringExtra("list_of_module_id"), "true");
                Intent intent = new Intent(FaqActivity.this, DashBoard.class);
                intent.putExtra("list_of_module_id", getIntent().getStringExtra("list_of_module_id"));
                startActivity(intent);*//*
                db.setFaqFinished(ListofModuleFinjan.course_ID, "true");
                finish();
            }
        });*/



      //  pb.setVisibility(View.VISIBLE);


        //Toast.makeText(context, "fd"+sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""), Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

       searchviewfaq = (SearchView) findViewById(R.id.faq_search);
        btn_nb_nav = (ImageButton) findViewById(R.id.toolbar_normal_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null




        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


       searchviewfaq.setFocusable(false);

        tv_profile = (TextView) findViewById(R.id.nb_profile);
        tv_calculator = (TextView) findViewById(R.id.nb_calculator);
        tv_faq = (TextView) findViewById(R.id.nb_faq);
        tv_finjan = (TextView) findViewById(R.id.nb_finjan);
        tv_finjancourses = (TextView) findViewById(R.id.nb_finjancourses);
        tv_signout = (TextView) findViewById(R.id.nb_signout);


        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchviewfaq.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchviewfaq.setIconifiedByDefault(false);


        searchviewfaq.setIconified(false);


        ImageView searchViewIcon =
                (ImageView)searchviewfaq.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchViewIcon.setVisibility(View.GONE);

        ViewGroup linearLayoutSearchView =
                (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        linearLayoutSearchView.addView(searchViewIcon);
        searchViewIcon.setAdjustViewBounds(true);
        searchViewIcon.setMaxWidth(0);
        searchViewIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,(Gravity.RIGHT)));
        //searchViewIcon.setImageDrawable(null);

        iv_searchicon = (ImageView) findViewById(R.id.serchvie_icon);



        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {



            @Override
            public boolean onQueryTextSubmit(String query) {
                Search_query = "";
                if (!Search_query.equals(query)) {

                    Search_query = query;
                    list.clear();
                    Index = 0;
                    if (NDC.isConnected(context)) {
                        if (Search_query.length() >= 3) {

                            searchviewfaq.clearFocus();

                            getSearchData();

                        }else {

                            Toast.makeText(context, "Minimum 3 character length Required", Toast.LENGTH_LONG).show();
                        }

                    } else {

                        list = offlineDatabase.getofflinesearchvalue(Search_query);

                        //list=db.getContact((Search_query));
                            adapter = new FaqAdapter(list);
                            recyclerView.setAdapter(adapter);
                       // btn.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
                        //Toast.makeText(context, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }


            public boolean onQueryTextChange(String newText) {
                iv_searchicon.setVisibility(View.GONE);

              if(newText.isEmpty()){
                    iv_searchicon.setVisibility(View.VISIBLE);
                    adapter = new FaqAdapter(FaqCategroyLIstActivity.faqList);
                    recyclerView.setAdapter(adapter);



                    }
                // this is your adapter that will be filtered
                return true;
            }


        };

        searchviewfaq.setOnQueryTextListener(queryTextListener);



        if (NDC.isConnected(context)) {
            //getdata();

            adapter = new FaqAdapter(FaqCategroyLIstActivity.faqList);
            recyclerView.setAdapter(adapter);
            //btn.setVisibility(View.VISIBLE);

        } else {

            adapter = new FaqAdapter(FaqCategroyLIstActivity.faqList);
            recyclerView.setAdapter(adapter);
           // btn.setVisibility(View.VISIBLE);


            /*listDatas = new Faqlistdatas(db.getAllContacts().get(0).getFaq_qus(),db.getAllContacts().get(0).getFaq_ans());
            list.add(listDatas);

            adapter = new FaqAdapter(list);
            recyclerView.setAdapter(adapter);

            Toast.makeText(context, "You are using offline data"+db.getAllContacts().get(0).getFaq_qus(), Toast.LENGTH_LONG).show();*/
        }


       /* if (isNetworkAvailable(context)){


        }else
        {
            adapter = new FaqAdapter(db.getAllContacts());
            recyclerView.setAdapter(adapter);

            Toast.makeText(context, "You are using offline data", Toast.LENGTH_SHORT).show();



        }*/


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Onresume", "Onresume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.finpedia:
               /* startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";*/
                finish();
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

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;

            case R.id.finstart_c:

                String couponcode = sharedpreferences.getString("couponvalidation", "");

                if(couponcode.equalsIgnoreCase("fst104")){
                    Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    startActivity(i);

                }



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
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
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





    private void getdata() {

            String modID = sharedpreferences.getString("Module_id", "");
        Log.d("faqmod id", modID);
        //Toast.makeText(context, "fd"+sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""), Toast.LENGTH_SHORT).show();

        RxClient.get(context).userFaq(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new faqreq(sharedpreferences.getString(SharedPrefUtils.SpEmail,""),
                        sharedpreferences.getString("Module_id", "")),

                new Callback<faqresp>() {
                    @Override
                    public void success(faqresp faqresp, Response response) {

                   // btn.setVisibility(View.VISIBLE);

                        //Toast.makeText(FaqActivity.this,"Successfully updated"+faqresp,Toast.LENGTH_LONG).show();

                        if (faqresp.getStatus().equals("200")) {

                      /*      pb.setVisibility(View.VISIBLE);*/

                            for (int i = 0; i < faqresp.getResult().getInfo().getFaq().length; i++) {
                                String nn = faqresp.getResult().getInfo().getFaq()[i].getFaq_qus();
                                listDatas = new Faqlistdatas(faqresp.getResult().getInfo().getFaq()[i].getFaq_qus(),
                                        faqresp.getResult().getInfo().getFaq()[i].getFaq_ans());
                                list.add(listDatas);
                            }

                            adapter = new FaqAdapter(list);
                            recyclerView.setAdapter(adapter);
                          ///  btn.setVisibility(View.VISIBLE);
                            db.OnDelete();
                            db.addContact(list);

                           // Toast.makeText(context, "Successfully added in the database", Toast.LENGTH_SHORT).show();

                      /*      adapter.notifyDataSetChanged();*/

                          /*  if(1==0) {
                                adapter = new FaqAdapter(list);
                                recyclerView.setAdapter(adapter);
                                db.OnDelete();
                                db.addContact(list);
                            }else
                            {
                            }*/
                        }else if(faqresp.getStatus().equals("402")){


                            isSearchtoakenExpired = true;

                          mtd_refresh_token();

                        }
                      /*  else {
                            editor.putString(SharedPrefUtils.SpRememberToken,"");
                            //mainactivity
                            mainActivity.getlogindata();




                            if(SharedPrefUtils.SpRememberToken!="") {
                            getdata();
                            }
                        }
*/
                       /* data = new ArrayList<faqresp>();
                        data.add(1,"test");
                        adapter = new FaqAdapter(data);
                        recyclerView.setAdapter(adapter);*/


                        /*for (int i=0; i < faqresp.getResult().getInfo().getFaq()[0].getFaq_qus().length(); i++){



                        }


*/

                    }

                    @Override
                    public void failure(RetrofitError error) {

                       /*Toast.makeText(context, "Retrofit Failure", Toast.LENGTH_LONG).show();*/

                     isSearchtoakenExpired = false;

                        mtd_refresh_token();
                    }
                });

/*
        RxClient.get(ProfileSetting.this).Updateprofile(remember_token, new profileupdatereq(sharedpreferences.getString(SharedPrefUtils.SpId,""),
                sharedpreferences.getString(SharedPrefUtils.SpUserId,""),et_username.getText().toString().trim(),
                et_fname.getText().toString().trim(), et_lname.getText().toString().trim(), et_emailid.getText().toString().trim(),
                et_companyname.getText().toString().trim(), sharedpreferences.getString(SharedPrefUtils.SpExpDate,""),*/


    }

    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(FaqActivity.this).Login(new loginreq(sharedpreferences.
                getString(SharedPrefUtils.SpEmail, ""),sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {




                if (loginresp.getStatus().equals("200")){
                 //   btn.setVisibility(View.VISIBLE);

                    //Toast.makeText(getApplicationContext(),"sucesss"+loginresp.getToken().toString(),Toast.LENGTH_LONG).show();

                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    editor.commit();
                    /*adapter.notifyDataSetChanged();*/
                   if(isSearchtoakenExpired) {
                       getSearchData();
                   }else {
                       getdata();
                   }
                }







            }

            @Override
            public void failure(RetrofitError error) {

                //Toast.makeText(getApplicationContext(),"Wrong Username And Password",Toast.LENGTH_LONG).show();

            }
        });

    }


    private void getSearchData() {
        list.clear();
        //StaticConfig.faqModule = "2";
      //  Toast.makeText(FaqActivity.this,"excut",Toast.LENGTH_LONG).show();
        //sharedpreferences.getString("Module_id", "")
        RxClient.get(context).Searchview(sharedpreferences.getString(SharedPrefUtils.SpRememberToken, ""),
                new searchreq(sharedpreferences.getString("courseID", ""),Search_query),
                new Callback<faqresp>() {
                    @Override
                    public void success(faqresp faqresp, Response response) {

                     //   btn.setVisibility(View.VISIBLE);
                       // Toast.makeText(FaqActivity.this,"exsp"+faqresp.getError(),Toast.LENGTH_LONG).show();

                        if (faqresp.getStatus().equals("200")) {

                            for (int i = 0; i < faqresp.getResult().getInfo().getFaq().length; i++) {

                                listDatas = new Faqlistdatas(faqresp.getResult().getInfo().getFaq()[i].getFaq_qus(),
                                        faqresp.getResult().getInfo().getFaq()[i].getFaq_ans());
                                list.add(listDatas);
                            }

                            adapter = new FaqAdapter(list);
                            recyclerView.setAdapter(adapter);
                         //   btn.setVisibility(View.VISIBLE);
                         /* adapter.notifyDataSetChanged();*/
                            /*db.OnDelete();
                            db.addContact(list);*/
                           // Toast.makeText(context, "Successfully added in the database", Toast.LENGTH_SHORT).show();

                          /*  if(1==0) {
                                adapter = new FaqAdapter(list);
                                recyclerView.setAdapter(adapter);
                                db.OnDelete();
                                db.addContact(list);
                            }else
                            {
                            }*/

                        }else {
                            isSearchtoakenExpired = false;
                           // Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();
                            mtd_refresh_token();
                        }

                       /* data = new ArrayList<faqresp>();
                        data.add(1,"test");
                        adapter = new FaqAdapter(data);
                        recyclerView.setAdapter(adapter);*/


                        /*for (int i=0; i < faqresp.getResult().getInfo().getFaq()[0].getFaq_qus().length(); i++){



                        }


*/

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        isSearchtoakenExpired = false;

                        mtd_refresh_token();
                    }
                });

/*
        RxClient.get(ProfileSetting.this).Updateprofile(remember_token, new profileupdatereq(sharedpreferences.getString(SharedPrefUtils.SpId,""),
                sharedpreferences.getString(SharedPrefUtils.SpUserId,""),et_username.getText().toString().trim(),
                et_fname.getText().toString().trim(), et_lname.getText().toString().trim(), et_emailid.getText().toString().trim(),
                et_companyname.getText().toString().trim(), sharedpreferences.getString(SharedPrefUtils.SpExpDate,""),*/


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

        Log.d("on start", "onstart");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("on stop", "onstop");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
