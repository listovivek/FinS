package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McQData;
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McqTestReq;
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McqTestResp;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.Result.ResultResponse;
import com.myappilication.xpress.finjan2017.models.login.evalution.Evaluationdatas;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ActivityEvaluation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ResultResponse> data;
    private EvelouationAdapter adapter;
    Boolean isSearchtoakenExpired = false;
    Evaluationdatas listDatas;
    Context context;
    List<Evaluationdatas> list = new ArrayList<>();

    public static final String PREFS_NAME = "data";
    ArrayList<String> finalAnsList;
    Intent Go;

    DatabaseHandler db;
    int Index = 0;
    String Search_query = "";

    TextView tv_profile, tv_finjancourses, tv_calculator, tv_faq, tv_finjan, tv_signout, t;
    public static TextView bar;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    NetConnectionDetector NDC;

    DbHelper mDBHelper;
    OfflineDatabaseHelper mOfflineDatabaseHelper;

    String current_score;

    public static ArrayList<Activity> actEval_act = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Toolbar toolbar;
    ImageButton imageButton, btn_nb_nav;
    Button Ok;

    public static Dialog mprProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluvationactivity);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);


        mDBHelper = new DbHelper(ActivityEvaluation.this);
        mOfflineDatabaseHelper = new OfflineDatabaseHelper(ActivityEvaluation.this);

        actEval_act.add(ActivityEvaluation.this);

        //db = new DatabaseHandler(this);
        Ok=(Button)findViewById(R.id.take_test);
        Ok.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      if (NDC.isConnected(context)) {

                                          if(DashBoard.mDashBoard != null){
                                              DashBoard.mDashBoard.finish();
                                          }

                                          /*if(DashBoard.mDashBoard != null){
                                              DashBoard.mDashBoard.finish();
                                          }

                                          String listOfmoduleid = getIntent().getStringExtra("list_of_mod_id");

                                          mDBHelper.setUserSelectedAnswer(listOfmoduleid,
                                                  McQData.getInstance().getUserSelectedData(),
                                                  McQData.getInstance().getMcqID_list());

                                          mOfflineDatabaseHelper.setMCQcompleted(listOfmoduleid, "true");

                                          Toast.makeText(ActivityEvaluation.this, "Success", Toast.LENGTH_LONG).show();

                                          Go = new Intent(ActivityEvaluation.this, DashBoard.class);
                                          Go.putExtra("list_of_module_id", listOfmoduleid);
                                          startActivity(Go);
                                         finish();*/
                                         callWebService();
                                      }else{
                                          Toast.makeText(ActivityEvaluation.this, "Kindly check your network connection",
                                                  Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              });

            context = getApplicationContext();

        NDC = new NetConnectionDetector();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);





        recyclerView = (RecyclerView) findViewById(R.id.eve_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        btn_nb_nav = (ImageButton) findViewById(R.id.toolbar_normal_menu);

      toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tv_profile = (TextView) findViewById(R.id.nb_profile);
        tv_calculator = (TextView) findViewById(R.id.nb_calculator);
        tv_faq = (TextView) findViewById(R.id.nb_faq);
        tv_finjan = (TextView) findViewById(R.id.nb_finjan);
        tv_finjancourses = (TextView) findViewById(R.id.nb_finjancourses);
        tv_signout = (TextView) findViewById(R.id.nb_signout);

        bar = (TextView) findViewById(R.id.ratingBar);
        t = (TextView) findViewById(R.id.textResult);


        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (NDC.isConnected(context)) {
            getdata();


        } else {
            EvelouationAdapter.score=0;
            adapter = new EvelouationAdapter(ActivityEvaluation.this, bar);
            recyclerView.setAdapter(adapter);
            // db.OnDelete();
            // db.addContact(list);

            final Handler handler = new Handler();
            final Runnable r = new Runnable()
            {
                public void run()
                {
                    String score = String.valueOf(EvelouationAdapter.score++);
                    current_score = score;
                    bar.setText("Score " +score+"/"+ McQData.getInstance().getMCQQuestion().size());
                    /*switch (McqTestMainActivity.scrore) {
                        case 1:
                        case 2:
                            t.setText("Oops! Better Luck Next Time!");
                            break;
                        case 3:
                        case 4:
                            t.setText("Hmmmm.. Someone's been reading a lot of trivia");
                            break;
                        case 5:
                            t.setText("Who are you? A trivia wizard???");
                            break;
                        default:
                            t.setText("Who are you? A trivia wizard???");
                            break;
                    }*/
                }
            };
            handler.postDelayed(r, 1000);

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    // Call smooth scroll
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                }
            });

        }


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void callWebService() {



        ArrayList<String> mcqID = new ArrayList<>();
        ArrayList<String> mcqans = new ArrayList<>();

        ArrayList<String> mcqID1 = new ArrayList<>();
        ArrayList<String> mcqans1 = new ArrayList<>();

       ArrayList<String> ans = McQData.getInstance().getUserSelectedData();
        ArrayList<String> qu = McQData.getInstance().getMcqID_list();

        for(int t=0; t<ans.size(); t++){
            mcqans1.add(ans.get(t));
        }

        for(int t=0; t<qu.size(); t++){
            mcqID1.add(qu.get(t));
        }


      /*  mcqans.add("ans1");
        mcqans.add("ans2");
        mcqans.add("ans3");
        mcqans.add("ans4");
        mcqans.add("ans5");
        mcqans.add("ans6");

        mcqID.add("3");
        mcqID.add("8");
        mcqID.add("7");
        mcqID.add("2");
        mcqID.add("4");
        mcqID.add("1");*/

        //String id = sharedpreferences.getString("Module_id", "");
       // String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
        mprProgressDialog.show();

        RxClient.get(ActivityEvaluation.this).submit(sharedpreferences.
                getString(SharedPrefUtils.SpRememberToken, ""), new McqTestReq(sharedpreferences.
                getString(SharedPrefUtils.SpEmail, ""), mcqID1
                , mcqans1,
                        sharedpreferences.getString("Module_id", ""),
                        ModuleFinJan.courseID,current_score),
                new Callback<McqTestResp>() {
                    @Override
                    public void success(McqTestResp mcqTestResp, Response response) {

                        if(mcqTestResp.getStatus().equals("200")){
                            /* Go = new Intent(ActivityEvaluation.this, DashBoard.class);
                                      Go.putExtra("go_to_calc", true);
                                      startActivity(Go);*/
                            String listOfmoduleid = getIntent().getStringExtra("list_of_mod_id");

                            mDBHelper.setUserSelectedAnswer(listOfmoduleid,
                                    McQData.getInstance().getUserSelectedData(),
                                    McQData.getInstance().getMcqID_list());

                            mOfflineDatabaseHelper.setMCQcompleted(listOfmoduleid, "true");

                            Toast.makeText(ActivityEvaluation.this, mcqTestResp.getResult(),
                                    Toast.LENGTH_LONG).show();

                            mprProgressDialog.dismiss();

                            Go = new Intent(ActivityEvaluation.this, DashBoard.class);
                            Go.putExtra("list_of_module_id", listOfmoduleid);
                            startActivity(Go);
                            finish();


                        }


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(ActivityEvaluation.this, error.toString(), Toast.LENGTH_LONG).show();
                        mprProgressDialog.dismiss();
                        finish();
                    }
                });



       /* RxClient.get(ActivityEvaluation.this).SubmitTest(sharedpreferences.
                getString(SharedPrefUtils.SpRememberToken, ""), new McqTestReq(sharedpreferences.
                        getString(SharedPrefUtils.SpEmail, ""), mcqID, mcqans,
                        sharedpreferences.getString(SharedPrefUtils.SpModuleId, ""), "7"),
                new Callback<McqTestResp>() {
                    @Override
                    public void success(McqTestResp mcqTestResp, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });*/

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

            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                finish();
                return true;

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                // finish();
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

            case R.id.fin_support:
                startActivity(new Intent(getApplicationContext(), Support.class));
                return true;

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;

            /*case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                return true;
           /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
            case R.id.feedback:
                if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    return true;
                }else{
                    Toast.makeText(ActivityEvaluation.this, "Kindly check your network connection",
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
                    Toast.makeText(ActivityEvaluation.this, "No records", Toast.LENGTH_SHORT).show();
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

                mOfflineDatabaseHelper.deleteAll();

                finish();
                return true;
            /*case R.id.faq:
                startActivity(new Intent(getApplicationContext(), FaqActivity.class));
                return true;
            case R.id.calculator:
                startActivity(new Intent(getApplicationContext(), FinjanCalcModule.class));
                return true;*/

        }
        return false;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);
        return true;
    }


    private void getdata() {

       /* RxClient.get(context).Result(new ResultReq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),
                sharedpreferences.getString(SharedPrefUtils.SpMcq_id, ""),
                sharedpreferences.getString(SharedPrefUtils.user_ans, ""),sharedpreferences.getString(SharedPrefUtils.SpModuleId, ""),
                sharedpreferences.getString(SharedPrefUtils.score, "")), new Callback<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse, Response response) {

                        if (resultResponse.getStatus().equals(200)){*/

       /* Bundle b = getIntent().getExtras();
        int score = b.getInt("score");

        String user = b.getString("user_ans");
        Log.d("Answer",user);
        String ques = b.getString("Questions");
        Log.d("Quesmcq",ques);

                           *//* listDatas = new Faqlistdatas(faqresp.getResult().getInfo().getFaq()[i].getFaq_qus(),
                                    faqresp.getResult().getInfo().getFaq()[i].getFaq_ans());
                            list.add(listDatas);*//*

                          *//*  listDatas = new Evaluationdatas();*//*


        ques = ques.replace("[","");
        user = user.replace("[","");

        String[] qout = ques.split(",");
        String[] aout = user.split(",");
        for (int i = 0; i <qout.length ; i++) {
            String q,a;


            q=qout[i];
            a=aout[i];

            listDatas = new Evaluationdatas("anklskd","aldfkjdkd");
            list.add(listDatas);
        }*/
      //  Toast.makeText(context, "hh---"+out[0], Toast.LENGTH_SHORT).show();







//get score

//display score


        //a1.setText("score",s);
        // a1.getText();




                           /* user_ans1.add(user);
                            Log.d("user_ans", String.valueOf(user_ans1));

                            question1.add(ques);
                            Log.d("Questions", String.valueOf(ques));*/

                            adapter = new EvelouationAdapter(ActivityEvaluation.this, bar);
                            recyclerView.setAdapter(adapter);
                           // db.OnDelete();
                           // db.addContact(list);


        final Handler handler = new Handler();
        final Runnable r = new Runnable()
        {
            public void run()
            {
                String score = String.valueOf(EvelouationAdapter.score++);
                current_score = score;
                bar.setText("Score " +score+"/"+ McQData.getInstance().getMCQQuestion().size());
                /*switch (McqTestMainActivity.scrore) {
                    case 1:
                    case 2:
                        t.setText("Oopsie! Better Luck Next Time!");
                        break;
                    case 3:
                    case 4:
                        t.setText("Hmmmm.. Someone's been reading a lot of trivia");
                        break;
                    case 5:
                        t.setText("Who are you? A trivia wizard???");
                        break;
                    default:
                        t.setText("Who are you? A trivia wizard???");
                        break;
                }*/
            }
        };
        handler.postDelayed(r, 1000);

                      /*      adapter.notifyDataSetChanged();*/

                          /*  if(1==0) {
                                adapter = new FaqAdapter(list);
                                recyclerView.setAdapter(adapter);
                                db.OnDelete();
                                db.addContact(list);
                            }else
                            {
                            }*/

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });


                    }



                 /*   @Override
                    public void failure(RetrofitError error) {

                       *//*Toast.makeText(context, "Retrofit Failure", Toast.LENGTH_LONG).show();*//*

                     isSearchtoakenExpired = false;

                        mtd_refresh_token();
                    }
                });*/

/*
        RxClient.get(ProfileSetting.this).Updateprofile(remember_token, new profileupdatereq(sharedpreferences.getString(SharedPrefUtils.SpId,""),
                sharedpreferences.getString(SharedPrefUtils.SpUserId,""),et_username.getText().toString().trim(),
                et_fname.getText().toString().trim(), et_lname.getText().toString().trim(), et_emailid.getText().toString().trim(),
                et_companyname.getText().toString().trim(), sharedpreferences.getString(SharedPrefUtils.SpExpDate,""),*/




    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(ActivityEvaluation.this).Login(new loginreq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),
                sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {




                if (loginresp.getStatus().equals("200")){


                    Toast.makeText(getApplicationContext(),"sucesss"+loginresp.getToken().toString(),Toast.LENGTH_LONG).show();

                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());

                    editor.commit();
                    /*adapter.notifyDataSetChanged();*/
                   if(isSearchtoakenExpired) {
                      // getdata();
                       Intent i = new Intent(ActivityEvaluation.this, MainActivity.class);
                       startActivity(i);
                   }
                }

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(getApplicationContext(),"Wrong Username And Password",Toast.LENGTH_LONG).show();

            }
        });

    }



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
