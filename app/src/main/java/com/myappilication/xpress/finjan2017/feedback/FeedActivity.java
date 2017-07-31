package com.myappilication.xpress.finjan2017.feedback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.Articles;
import com.myappilication.xpress.finjan2017.ChangePassword;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.ProfileSetting;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.TryFinStart;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesAnsReq;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesResponse;
import com.myappilication.xpress.finjan2017.models.login.feedbackquestion.FeedbackResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 5/6/17.
 */
public class FeedActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    TextView question1, question2, question3, question4;

    RadioGroup rGroup1, rGroup2, rGroup3, rGroup4;

    Toolbar toolbar;

    NetConnectionDetector NDC;

    RadioButton r1Button1, r1Button2, r1Button3, r1Button4, r1Button5;
    RadioButton r2Button1, r2Button2, r2Button3, r2Button4, r2Button5;
    RadioButton r3Button1, r3Button2, r3Button3, r3Button4, r3Button5;
    RadioButton r4Button1, r4Button2, r4Button3, r4Button4, r4Button5;

    String answer1, answer2, answer3, answer4;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    ArrayList<String> ans1 = new ArrayList<>();
    ArrayList<String> ans2 = new ArrayList<>();
    ArrayList<String> ans3 = new ArrayList<>();
    ArrayList<String> ans4 = new ArrayList<>();
    ArrayList<String> ans5 = new ArrayList<>();

    ArrayList<String> ques = new ArrayList<>();

    EditText feedback_name, feedback_email, feedback_number;

    OfflineFeedbackDB feedbackDB;

    Context context;
    String strName, strEmail, strNum;

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static ArrayList<Activity> feed_act = new ArrayList<>();

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_screen);

            context = FeedActivity.this;

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        feedbackDB = new OfflineFeedbackDB(FeedActivity.this);

        feed_act.add(FeedActivity.this);

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callWebService();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        question1 = (TextView) findViewById(R.id.question1);
        question2 = (TextView) findViewById(R.id.question2);
        question3 = (TextView) findViewById(R.id.question3);
        question4 = (TextView) findViewById(R.id.question4);

        rGroup1 = (RadioGroup) findViewById(R.id.radiobutton_grp1);
        rGroup2 = (RadioGroup) findViewById(R.id.radiobutton_grp2);
        rGroup3 = (RadioGroup) findViewById(R.id.radiobutton_grp3);
        rGroup4 = (RadioGroup) findViewById(R.id.radiobutton_grp4);

        r1Button1 = (RadioButton) findViewById(R.id.q1_radio1);
        r1Button2 = (RadioButton) findViewById(R.id.q1_radio2);
        r1Button3 = (RadioButton) findViewById(R.id.q1_radio3);
        r1Button4 = (RadioButton) findViewById(R.id.q1_radio4);
        r1Button5 = (RadioButton) findViewById(R.id.q1_radio5);

        r2Button1 = (RadioButton) findViewById(R.id.q2_radio1);
        r2Button2 = (RadioButton) findViewById(R.id.q2_radio2);
        r2Button3 = (RadioButton) findViewById(R.id.q2_radio3);
        r2Button4 = (RadioButton) findViewById(R.id.q2_radio4);
        r2Button5 = (RadioButton) findViewById(R.id.q2_radio5);

        r3Button1 = (RadioButton) findViewById(R.id.q3_radio1);
        r3Button2 = (RadioButton) findViewById(R.id.q3_radio2);
        r3Button3 = (RadioButton) findViewById(R.id.q3_radio3);
        r3Button4 = (RadioButton) findViewById(R.id.q3_radio4);
        r3Button5 = (RadioButton) findViewById(R.id.q3_radio5);

        r4Button1 = (RadioButton) findViewById(R.id.q4_radio1);
        r4Button2 = (RadioButton) findViewById(R.id.q4_radio2);
        r4Button3 = (RadioButton) findViewById(R.id.q4_radio3);
        r4Button4 = (RadioButton) findViewById(R.id.q4_radio4);
        r4Button5 = (RadioButton) findViewById(R.id.q4_radio5);



        rGroup1.setOnCheckedChangeListener(this);
        rGroup2.setOnCheckedChangeListener(this);
        rGroup3.setOnCheckedChangeListener(this);
        rGroup4.setOnCheckedChangeListener(this);

        feedback_name = (EditText) findViewById(R.id.feed_editname);
        feedback_email = (EditText) findViewById(R.id.feed_editemail);
        feedback_number = (EditText) findViewById(R.id.feed_editmun);

        ScrollView scrollView = (ScrollView) findViewById(R.id.feedback_scroll);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //queslist = (ArrayList<DashboardCourses>) database.getdata();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Button btn = (Button) findViewById(R.id.feed_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName = feedback_name.getText().toString();
                strEmail = feedback_email.getText().toString();
                strNum = feedback_number.getText().toString();

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

                int g1 = rGroup1.getCheckedRadioButtonId();
                int g2 = rGroup2.getCheckedRadioButtonId();
                int g3 = rGroup3.getCheckedRadioButtonId();
                int g4 = rGroup4.getCheckedRadioButtonId();

                if(strName.trim().length()>0 && strName.trim() != null){
                    if(strEmail.trim().length()>0 && strEmail.trim() != null && Pattern.matches(EMAIL_REGEX, strEmail)){
                        if(strNum.trim().length()>0 && strNum.trim() != null && strNum.trim().length()>9){
                            if(g1!=-1){
                                if(g2!=-1){
                                    if(g3!=-1){
                                        if(g4!=-1){
                                            Log.d("final", "suss");
                                            ArrayList<String> temp = new ArrayList<String>();
                                            temp.add(answer1);
                                            temp.add(answer2);
                                            temp.add(answer3);
                                            temp.add(answer4);

                                            feedbackDB.setfeedbackValue(strName.trim(), strEmail, strNum, temp, ques,
                                                    currentDateTimeString, em);

                                            alertt();



                                        }else{
                                            Toast.makeText(FeedActivity.this, "click any one option in ques 4",
                                                    Toast.LENGTH_SHORT).show();
                                        }
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
                            Toast.makeText(FeedActivity.this, "Kindly fill your number", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(FeedActivity.this, "Kindly fill your emailID", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(FeedActivity.this, "Kindly fill your name", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void alertt() {
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

                wbService();
                rGroup1.clearCheck();
                rGroup2.clearCheck();
                rGroup3.clearCheck();
                rGroup4.clearCheck();

                al.dismiss();

                feedback_name.setText("");
                feedback_email.setText("");
                feedback_number.setText("");
            }
        });


    }

    private void wbService() {


        ArrayList<String> feedbackQuestion = new ArrayList<>();
        ArrayList<String> feedbackAns = new ArrayList<>();
        feedbackQuestion.add("The training met my personal learning objectives");
        feedbackQuestion.add("The content was organized and easy to follow");
        feedbackQuestion.add("The trainer was knowledgeable");
        feedbackQuestion.add("How do you rate the training overall?");

        feedbackAns.add("Strongly Agree");
        feedbackAns.add("Strongly Agree");
        feedbackAns.add("Neutral");
        feedbackAns.add("Good");


            RxClient.get(getApplicationContext()).getfeedbkQuesAnswer(sharedpreferences.
                    getString(SharedPrefUtils.SpRememberToken, ""), new FBQuesAnsReq(feedbackQuestion,
                    feedbackAns, strName,
            strEmail, strNum), new Callback<FBQuesResponse>() {

                @Override
                public void success(FBQuesResponse fbQuesResponse, Response response) {
                   Toast.makeText(FeedActivity.this, fbQuesResponse.getResult(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                   // Toast.makeText(FeedActivity.this, "Service not response", Toast.LENGTH_LONG).show();
                }
            });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.q1_radio1) {
            answer1 = r1Button1.getText().toString();
        } else if (checkedId == R.id.q1_radio2) {
            answer1 = r1Button2.getText().toString();
        } else if (checkedId == R.id.q1_radio3) {
            answer1 = r1Button3.getText().toString();
        } else if (checkedId == R.id.q1_radio4) {
            answer1 = r1Button4.getText().toString();
        }else if(checkedId == R.id.q1_radio5) {
            answer1 = r1Button5.getText().toString();
        }

        else if (checkedId == R.id.q2_radio1) {
            answer2 = r2Button1.getText().toString();
        } else if (checkedId == R.id.q2_radio2) {
            answer2 = r2Button2.getText().toString();
        } else if (checkedId == R.id.q2_radio3) {
            answer2 = r2Button3.getText().toString();
        } else if (checkedId == R.id.q2_radio4) {
            answer2 = r2Button4.getText().toString();
        }else if (checkedId == R.id.q2_radio5) {
            answer2 = r2Button5.getText().toString();
        }

        else if (checkedId == R.id.q3_radio1) {
            answer3 = r3Button1.getText().toString();
        } else if (checkedId == R.id.q3_radio2) {
            answer3 = r3Button2.getText().toString();
        } else if (checkedId == R.id.q3_radio3) {
            answer3 = r3Button3.getText().toString();
        } else if (checkedId == R.id.q3_radio4) {
            answer3 = r3Button4.getText().toString();
        }else if (checkedId == R.id.q3_radio5) {
            answer3 = r3Button5.getText().toString();
        }

        else if (checkedId == R.id.q4_radio1) {
            answer4 = r4Button1.getText().toString();
        } else if (checkedId == R.id.q4_radio2) {
            answer4 = r4Button2.getText().toString();
        } else if (checkedId == R.id.q4_radio3) {
            answer4 = r4Button3.getText().toString();
        } else if (checkedId == R.id.q4_radio4) {
            answer4 = r4Button4.getText().toString();
        }else if (checkedId == R.id.q4_radio5) {
            answer4 = r4Button5.getText().toString();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                finish();
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

    private void callWebService() {
        progressBar.setVisibility(View.VISIBLE);
        RxClient.get(getApplicationContext()).getfeedbkQuestions(sharedpreferences.getString
                (SharedPrefUtils.SpRememberToken, ""), new Callback<FeedbackResponse>() {
            @Override
            public void success(FeedbackResponse feedres, Response response) {
                Log.d("", "");
                for(int n=0; n<feedres.getResult().getInfo().length; n++){
                    ans1.add(feedres.getResult().getInfo()[n].getAns1());
                    ans2.add(feedres.getResult().getInfo()[n].getAns2());
                    ans3.add(feedres.getResult().getInfo()[n].getAns3());
                    ans4.add(feedres.getResult().getInfo()[n].getAns4());
                    ans5.add(feedres.getResult().getInfo()[n].getAns5());

                    ques.add(feedres.getResult().getInfo()[n].getQuestion());
                }
                question1.setText(ques.get(0));
                question2.setText(ques.get(1));
                question3.setText(ques.get(2));
                question4.setText(ques.get(3));

                r1Button1.setText(ans1.get(0));
                r1Button2.setText(ans2.get(0));
                r1Button3.setText(ans3.get(0));
                r1Button4.setText(ans4.get(0));
                r1Button5.setText(ans5.get(0));

                r2Button1.setText(ans1.get(1));
                r2Button2.setText(ans2.get(1));
                r2Button3.setText(ans3.get(1));
                r2Button4.setText(ans4.get(1));
                r2Button5.setText(ans5.get(1));

                r3Button1.setText(ans1.get(2));
                r3Button2.setText(ans2.get(2));
                r3Button3.setText(ans3.get(2));
                r3Button4.setText(ans4.get(2));
                r3Button5.setText(ans5.get(2));

                r4Button1.setText(ans1.get(3));
                r4Button2.setText(ans2.get(3));
                r4Button3.setText(ans3.get(3));
                r4Button4.setText(ans4.get(3));
                r4Button5.setText(ans5.get(3));

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("", "");

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(FeedActivity.this, "service not response", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}