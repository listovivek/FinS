package com.myappilication.xpress.finjan2017.feedback;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.ChangePassword;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.ProfileSetting;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.TryFinStart;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesAnsReq;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesResponse;
import com.myappilication.xpress.finjan2017.models.login.feedbackquestion.FeedbackResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 24/8/17.
 */
public class FeedbackNew extends AppCompatActivity {

    String countryName[] = { "India", "Pakistan", "China", "Nepal",
            "Bangladesh" };

    RadioGroup rg;

    ArrayList<String> ans1 = new ArrayList<>();
    ArrayList<String> ans2 = new ArrayList<>();
    ArrayList<String> ans3 = new ArrayList<>();
    ArrayList<String> ans4 = new ArrayList<>();
    ArrayList<String> ans5 = new ArrayList<>();

    public static ArrayList<Activity> feed_act = new ArrayList<>();
    ProgressBar progressBar;
    ArrayList<String> ques = new ArrayList<>();

    ArrayList<String> id = new ArrayList<>();

    EditText feedback_name, feedback_email, feedback_number, feedback_comments;

    OfflineFeedbackDB feedbackDB;

    Context context;
    String strName, strEmail, strNum;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    NetConnectionDetector NDC;
    LinearLayout mLinearLayout;

    ArrayList<String> colorcode = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_new);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mLinearLayout = (LinearLayout) findViewById(R.id.feed_linear);

        colorcode.add("#E2F2EF");
        colorcode.add("#C5E5E0");
        colorcode.add("#AAD9D1");
        colorcode.add("#8DCCC1");
        colorcode.add("#8DCCC1");


        context = FeedbackNew.this;

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        feedbackDB = new OfflineFeedbackDB(FeedbackNew.this);
        feed_act.add(FeedbackNew.this);

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (NDC.isConnected(context)) {
           callWebService();
        }else {
            Toast.makeText(getApplicationContext(), "Kindly check your network connection",
                    Toast.LENGTH_LONG).show();
        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        /*Button btn = (Button) findViewById(R.id.feed_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName = "hello";
                strEmail = "hello@";
                strNum = "65498465236";

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

                int g1 = rGroup1.getCheckedRadioButtonId();
                int g2 = rGroup2.getCheckedRadioButtonId();
                int g3 = rGroup3.getCheckedRadioButtonId();
                // int g4 = rGroup4.getCheckedRadioButtonId();

                if(strName.trim().length()>0 && strName.trim() != null){
                    if(strEmail.trim().length()>0 && strEmail.trim() != null){
                        if(strNum.trim().length()>0 && strNum.trim() != null && strNum.trim().length()>9){
                            if(feedback_comments.getText().toString().length()>5 &&
                                    feedback_comments.getText().toString().trim() != null){


                                if(g1!=-1){
                                    if(g2!=-1){
                                        if(g3!=-1){
                                            // if(g4!=-1){
                                            Log.d("final", "suss");
                                            ArrayList<String> temp = new ArrayList<String>();
                                            temp.add(answer1);
                                            temp.add(answer2);
                                            temp.add(answer3);
                                            temp.add(answer4);

                                            feedbackDB.setfeedbackValue(strName.trim(), strEmail, strNum, temp, ques,
                                                    currentDateTimeString, em);

                                            alertt(temp);



                                       *//* }else{
                                            Toast.makeText(FeedActivity.this, "click any one option in ques 4",
                                                    Toast.LENGTH_SHORT).show();
                                        }*//*
                                        }else{
                                            Toast.makeText(FeedActivity.this, "click any one option in ques 3",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(FeedActivity.this, "click any one option in ques 2",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(FeedActivity.this, "click any one option in ques 1", Toast.LENGTH_SHORT).show();

                                }
                            }else{
                                Toast.makeText(FeedActivity.this, "Kindly fill your comments", Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            Toast.makeText(FeedActivity.this, "Kindly fill your number", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(FeedActivity.this, "Kindly fill your emailID", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(FeedActivity.this, "Kindly fill your name", Toast.LENGTH_SHORT).show();
                }

            }
        });*/
    }

   /* private void alertt(final ArrayList<String> ans) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FeedActivity.this);
        View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
        dialogBuilder.setView(bView);
        Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);

        TextView t = (TextView) bView.findViewById(R.id.dialog_text);
        t.setText("Your feedback successfully sent");

        final AlertDialog al = dialogBuilder.create();
        al.show();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wbService(ans);
                rGroup1.clearCheck();
                rGroup2.clearCheck();
                rGroup3.clearCheck();
                feedback_comments.setText("");
//                rGroup4.clearCheck();

                al.dismiss();

               *//* feedback_name.setText("");
                feedback_email.setText("");
                feedback_number.setText("");*//*
            }
        });


    }*/

   /* private void wbService(ArrayList<String> ans) {

        ArrayList<String> feedbackQuestion = new ArrayList<>();
        ArrayList<String> feedbackAns = new ArrayList<>();
        feedbackQuestion.add("The training met my personal learning objectives");
        feedbackQuestion.add("The content was organized and easy to follow");
        feedbackQuestion.add("The trainer was knowledgeable");
        //feedbackQuestion.add("How do you rate the training overall?");

        feedbackAns.add(ans.get(0));
        feedbackAns.add(ans.get(1));
        feedbackAns.add(ans.get(2));
        // feedbackAns.add(ans.get(3));

        String comments = feedback_comments.getText().toString();


        RxClient.get(getApplicationContext()).getfeedbkQuesAnswer(sharedpreferences.
                getString(SharedPrefUtils.SpRememberToken, ""), new FBQuesAnsReq(feedbackQuestion,
                feedbackAns, strName,
                strEmail, strNum, comments, id), new Callback<FBQuesResponse>() {

            @Override
            public void success(FBQuesResponse fbQuesResponse, Response response) {
                Toast.makeText(FeedActivity.this, fbQuesResponse.getResult(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(FeedActivity.this, "Service not response", Toast.LENGTH_LONG).show();
            }
        });
    }*/




    private void callWebService() {
        progressBar.setVisibility(View.VISIBLE);
        RxClient.get(getApplicationContext()).getfeedbkQuestions(sharedpreferences.getString
                (SharedPrefUtils.SpRememberToken, ""), new Callback<FeedbackResponse>() {
            @Override
            public void success(FeedbackResponse feedres, Response response) {
                Log.d("", "");
                for(int n=0; n<feedres.getResult().getInfo().length; n++){

                    ans1.add(feedres.getResult().getInfo()[n].getAns1());
                    ans1.add(feedres.getResult().getInfo()[n].getAns2());
                    ans1.add(feedres.getResult().getInfo()[n].getAns3());
                    ans1.add(feedres.getResult().getInfo()[n].getAns4());
                    ans1.add(feedres.getResult().getInfo()[n].getAns5());

                    ques.add(feedres.getResult().getInfo()[n].getQuestion());

                    id.add(feedres.getResult().getInfo()[n].getId());
                }

                int count=0;
                for (int k = 0; k < ques.size(); k++) {
                    //create text button

                    TextView title = new TextView(FeedbackNew.this);

                    LinearLayout.LayoutParams params = new LinearLayout.
                            LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,10,0,10);
                    title.setLayoutParams(params);
                    title.setText(ques.get(k));
                   title.setTextSize(18);

                    title.setTextColor(Color.parseColor("#50c2b4"));
                    mLinearLayout.addView(title);
                    // create radio button
                    final RadioButton[] rb = new RadioButton[5];
                    rg = new RadioGroup(FeedbackNew.this);
                    rg.setOrientation(RadioGroup.VERTICAL);


                    for (int i = 0; i < 5; i++) {
                        rb[i] = new RadioButton(FeedbackNew.this);
                        rg.addView(rb[i]);
                        int textColor = Color.parseColor(colorcode.get(i));
                        rb[i].setBackgroundColor(textColor);
                        rb[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                /*RadioGroup.LayoutParams params
                        = new RadioGroup.LayoutParams(getApplicationContext(), null);
                params.setMargins(10, 0, 0, 0);
                rb[i].setLayoutParams(params);*/
                        rb[i].setId(i);

                        rb[i].setText(ans1.get(count).trim());
                        rg.setOnCheckedChangeListener(mCheckedListner);
                        count++;
                    }


                    mLinearLayout.addView(rg);
                }


                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("", "");

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(FeedbackNew.this,
                        "service not response", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


    private RadioGroup.OnCheckedChangeListener mCheckedListner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            String text = null;
            for (int i = 0; i < rg.getChildCount(); i++) {
                RadioButton btn = (RadioButton) rg.getChildAt(i);
                if (btn.getId() == checkedId) {

                    int ii = btn.getId();

                    text = (String) btn.getText();
                    // do something with text
                    Toast.makeText(FeedbackNew.this,
                            String.valueOf(ii), Toast.LENGTH_SHORT).show();
                    return;
                }

            }

        }

};

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

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
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

            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                if(ModuleFinJan.modFinjan != null){
                    ModuleFinJan.modFinjan.finish();
                }
               // finish();
                return true;*/


            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;

            /*case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

           /* case R.id.faq:
                startActivity(new Intent(getApplicationContext(), FaqActivity.class));
                return true;
            case R.id.calculator:
                startActivity(new Intent(getApplicationContext(), FinjanCalcModule.class));
                return true;*/
            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;

            // case R.id.feedback:
                /*if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    return true;
                }else{
                    Toast.makeText(FeedActivity.this, "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    return false;
                }*/



            /*case R.id.feedback_list:
                OfflineFeedbackDB feedbackDB = new OfflineFeedbackDB(this);
                String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
                ArrayList<String> list = feedbackDB.getdata(em);
                if(list.size() > 0){
                    startActivity(new Intent(getApplicationContext(), UserFeedbackList.class));
                    return true;

                }else{
                    Toast.makeText(FeedActivity.this, "No records", Toast.LENGTH_SHORT).show();
                    return false;
                }*/

           /* case R.id.dashboard_menu:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/
            case R.id.logout:
                sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES,
                        Context.MODE_PRIVATE);

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

    }
