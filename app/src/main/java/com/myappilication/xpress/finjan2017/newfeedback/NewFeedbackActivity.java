package com.myappilication.xpress.finjan2017.newfeedback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.TypedArrayUtils;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McQData;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesAnsReq;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesResponse;
import com.myappilication.xpress.finjan2017.models.login.feedbackquestion.FeedbackResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.newfaqcategorylist.NewFaqCategoryResponse;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.progressstyle.ProgressBarStyle;
import com.myappilication.xpress.finjan2017.termscondition.Support;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 28/8/17.
 */
public class NewFeedbackActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    private RadioButton answer1 = null;
    private RadioButton answer2 = null;
    private RadioButton answer3 = null;
    private RadioButton answer4 = null;
    private RadioButton answer5 = null;
    private RadioGroup answers = null;


    ArrayList<String> ans1 = new ArrayList<>();
    ArrayList<String> ans2 = new ArrayList<>();
    ArrayList<String> ans3 = new ArrayList<>();
    ArrayList<String> ans4 = new ArrayList<>();
    ArrayList<String> ans5 = new ArrayList<>();

    ArrayList<String> ques = new ArrayList<>();

    ArrayList<String> id = new ArrayList<>();


    TextView question, commentstxt;
    NetConnectionDetector NDC;

    public static int selected[] = null;
    private int correctAns[] = null;
    private int quesIndex = 0;

    EditText comments;

    Toolbar toolbar;

    Button next;

    public static Dialog mprProgressDialog;

    OfflineDatabaseHelper offlineDB;


    public static ArrayList<Activity> feed_act = new ArrayList<>();

    //ProgressBar progressBar;

    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_feedback);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        mprProgressDialog = ProgressBarStyle.getInstance().createProgressDialog(this);

        if (NDC.isConnected(NewFeedbackActivity.this)) {
            callWebService();
        }else {
            Toast.makeText(getApplicationContext(), "Kindly check your network connection",
                    Toast.LENGTH_LONG).show();
        }

        feed_act.add(NewFeedbackActivity.this);

        if (0 == 1) {
            ((Button) findViewById(R.id.previousBtn)).setEnabled(false);
        } else {
            ((Button) findViewById(R.id.previousBtn)).setEnabled(true);
        }

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next = ((Button) findViewById(R.id.nextBtn));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(v);
            }
        });

        question = (TextView) findViewById(R.id.qDescription);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //queslist = (ArrayList<DashboardCourses>) database.getdata();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       // progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        answer1 = (RadioButton) findViewById(R.id.opt0);
        answer2 = (RadioButton) findViewById(R.id.opt1);
        answer3 = (RadioButton) findViewById(R.id.opt2);
        answer4 = (RadioButton) findViewById(R.id.opt3);
        answer5 = (RadioButton) findViewById(R.id.opt4);
        answers = (RadioGroup) findViewById(R.id.options);

        offlineDB = new OfflineDatabaseHelper(NewFeedbackActivity.this);

        scrollView = (ScrollView) findViewById(R.id.scrollView2);

        commentstxt = (TextView) findViewById(R.id.feed_txtcomment);
        comments = (EditText) findViewById(R.id.edit_comments);

        answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.opt0:
                        answer1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
                        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
                        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
                        answer5.setBackgroundColor(getResources().getColor(R.color.radiobutton5));
                        break;
                    case R.id.opt1:
                        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
                        answer2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
                        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
                        answer5.setBackgroundColor(getResources().getColor(R.color.radiobutton5));
                        break;
                    case R.id.opt2:
                        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
                        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
                        answer3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
                        answer5.setBackgroundColor(getResources().getColor(R.color.radiobutton5));
                        break;
                    case R.id.opt3:
                        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
                        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
                        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
                        answer4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        answer5.setBackgroundColor(getResources().getColor(R.color.radiobutton5));
                        break;

                    case R.id.opt4:
                        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
                        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
                        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
                        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
                        answer5.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        break;

                }
            }
        });
    }


    private void callWebService() {
        //progressBar.setVisibility(View.VISIBLE);
        mprProgressDialog.show();
        RxClient.get(getApplicationContext()).getfeedbkQuestions(sharedpreferences.getString
                (SharedPrefUtils.SpRememberToken, ""), new Callback<FeedbackResponse>() {
            @Override
            public void success(FeedbackResponse feedres, Response response) {
                Log.d("", "");
                for(int n=0; n<feedres.getResult().getInfo().length; n++){
                    ans1.add(feedres.getResult().getInfo()[n].getAns1());
                    ans2.add(feedres.getResult().getInfo()[n].getAns2());
                    ans3.add(feedres.getResult().getInfo()[n].getAns3());
                    ans4.add(feedres.getResult().getInfo()[n].getAns4().trim());
                    ans5.add(feedres.getResult().getInfo()[n].getAns5());

                    ques.add(feedres.getResult().getInfo()[n].getQuestion());

                    id.add(feedres.getResult().getInfo()[n].getId());
                }


                selected = new int[ques.size()];
                Arrays.fill(selected, -1);

                showQus(0);
                //progressBar.setVisibility(View.INVISIBLE);
                mprProgressDialog.dismiss();
                scrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("", "");
                try{
                    FeedbackResponse usere = (FeedbackResponse)
                            error.getBodyAs(FeedbackResponse.class);

                    if(usere.getStatus().equalsIgnoreCase("402")){
                        mtd_refresh_token();
                    }else{
                        mprProgressDialog.dismiss();
                        Toast.makeText(NewFeedbackActivity.this, error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    mprProgressDialog.dismiss();
                    Toast.makeText(NewFeedbackActivity.this, e.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void mtd_refresh_token() {
       /* Toast.makeText(context, "expired", Toast.LENGTH_SHORT).show();*/
        RxClient.get(NewFeedbackActivity.this).Login(new loginreq(sharedpreferences.
                getString(SharedPrefUtils.SpEmail, ""),
                sharedpreferences.getString(SharedPrefUtils.SpPassword, "")), new Callback<loginresp>() {
            @Override
            public void success(loginresp loginresp, Response response) {



                if (loginresp.getStatus().equals("200")){
                    editor.putString(SharedPrefUtils.SpRememberToken,loginresp.getToken().toString());
                    editor.commit();

                    final Handler handler = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            callWebService();

                        }
                    };
                    handler.postDelayed(runnable, 500);


                }
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("refresh token", "refresh token error");
                Toast.makeText(NewFeedbackActivity.this, "Service not response",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }


    public void next(View view) {
        // checkCondititon = false;
        if(answer1.isChecked() || answer2.isChecked() ||
                answer3.isChecked() || answer4.isChecked() || answer5.isChecked()) {

            if(comments.getVisibility() == View.VISIBLE){
                if(comments.getText().toString().length()>0 &&
                        comments.getText().toString().trim() != null){
                    if(comments.getText().toString().trim().length()>40) {

                        setAnswer();
                        quesIndex++;

                        next.setText("Next");

                        comments.setVisibility(View.GONE);
                        commentstxt.setVisibility(View.GONE);

                        if (quesIndex == ques.size()) {
                            quesIndex = ques.size() - 1;

                            alertt();

                        } else {
                            showQus(quesIndex);
                        }

                    }else{
                        Toast.makeText(NewFeedbackActivity.this, "Your comments should be above 40 letters",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(NewFeedbackActivity.this, "Kindly fill your comments",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                setAnswer();
                quesIndex++;

                next.setText("Next");

                comments.setVisibility(View.GONE);
                commentstxt.setVisibility(View.GONE);

                if (quesIndex == ques.size()) {
                    quesIndex = ques.size() - 1;

                    alertt();

                } else {
                    showQus(quesIndex);
                }
            }



        }else{
            Toast.makeText(NewFeedbackActivity.this,
                    "Kindly click any one option", Toast.LENGTH_SHORT).show();
        }
    }

    private void alertt() {

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NewFeedbackActivity.this);
            View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
            dialogBuilder.setView(bView);
        dialogBuilder.setCancelable(false);
            Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);

            TextView t = (TextView) bView.findViewById(R.id.dialog_text);
            t.setText("Your feedback successfully sent");

            final AlertDialog al = dialogBuilder.create();
            al.show();

            send_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    wbService(selected);


//                rGroup4.clearCheck();

                    al.dismiss();

               /* feedback_name.setText("");
                feedback_email.setText("");
                feedback_number.setText("");*/
                }
            });



    }

    private void wbService(int[] selected) {
        //String comments = feedback_comments.getText().toString();

        ArrayList<String> finalans = new ArrayList<>();

        ArrayList<String> temp = new ArrayList<>();


        for(int t=0; t<ques.size(); t++){

            temp.add(ans1.get(t));
            temp.add(ans2.get(t));
            temp.add(ans3.get(t));
            temp.add(ans4.get(t));
            temp.add(ans5.get(t));



            String g = temp.get(selected[t]);
            finalans.add(g);
            temp.clear();

        }


        String com = comments.getText().toString();

        String name = "nasds";
        String email = "emalds";
        String number = "fkdjf";

        mprProgressDialog.show();

        RxClient.get(getApplicationContext()).getfeedbkQuesAnswer(sharedpreferences.
                getString(SharedPrefUtils.SpRememberToken, ""), new FBQuesAnsReq(ques,
                finalans, name,
                email, number, com, id), new Callback<FBQuesResponse>() {

            @Override
            public void success(FBQuesResponse fbQuesResponse, Response response) {
                Toast.makeText(NewFeedbackActivity.this, fbQuesResponse.getResult(),
                        Toast.LENGTH_LONG).show();
                finish();
                mprProgressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(NewFeedbackActivity.this, "Service not response", Toast.LENGTH_LONG).show();
                mprProgressDialog.dismiss();
            }
        });


        //selected = new int[0];
      // selected = new int[ques.size()];



        //Arrays.fill(selected, -1);

        showQus(0);
        quesIndex=0;

        answers.clearCheck();



        Log.d("", "");


        /*RxClient.get(getApplicationContext()).getfeedbkQuesAnswer(sharedpreferences.
                getString(SharedPrefUtils.SpRememberToken, ""), new FBQuesAnsReq(ques,
                finalans, "null",
                "null", "null", comments, id), new Callback<FBQuesResponse>() {

            @Override
            public void success(FBQuesResponse fbQuesResponse, Response response) {
                Toast.makeText(NewFeedbackActivity.this, fbQuesResponse.getResult(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(NewFeedbackActivity.this, "Service not response", Toast.LENGTH_LONG).show();
            }
        });*/
    }


    private void setAnswer() {
        if (answer1.isChecked())
            selected[quesIndex] = 0;
        if (answer2.isChecked())
            selected[quesIndex] = 1;
        if (answer3.isChecked())
            selected[quesIndex] = 2;
        if (answer4.isChecked())
            selected[quesIndex] = 3;
        if (answer5.isChecked())
            selected[quesIndex] = 4;

        Log.d("", Arrays.toString(selected));
        Log.d("",Arrays.toString(correctAns));

    }


    public void previous(View view) {
        // checkCondititon = true;
        setAnswer();
        quesIndex--;

        next.setText("Next");

        comments.setVisibility(View.GONE);
        commentstxt.setVisibility(View.GONE);


        if (quesIndex < 0){
            quesIndex = 0;
           // view.setEnabled(false);
        }else{
            showQus(quesIndex);
            //view.setEnabled(true);
        }
    }


    private void showQus(int i) {
        answers.check(-1);

        question.setText(ques.get(i));
        answer1.setText(ans1.get(i));
        answer2.setText(ans2.get(i));
        answer3.setText(ans3.get(i));
        answer4.setText(ans4.get(i));
        answer5.setText(ans5.get(i));

        Log.d("",selected[i]+ "");

        if (selected[i] == 0)
            answers.check(R.id.opt0);

        if (selected[i] == 1)
            answers.check(R.id.opt1);

        if (selected[i] == 2)
            answers.check(R.id.opt2);

        if (selected[i] == 3)
            answers.check(R.id.opt3);

        String f = answer1.getText().toString();
        Log.d("finla", f);


        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
        answer5.setBackgroundColor(getResources().getColor(R.color.radiobutton5));


        try{
            int temp = i;
            temp++;

            String n = ques.get(temp);

            if(n == null){

            }

        }catch (Exception e){
            comments.setVisibility(View.VISIBLE);
            commentstxt.setVisibility(View.VISIBLE);
            next.setText("Submit");
        }
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
