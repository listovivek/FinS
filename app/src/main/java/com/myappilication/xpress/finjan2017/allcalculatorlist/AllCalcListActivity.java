package com.myappilication.xpress.finjan2017.allcalculatorlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.Articles;
import com.myappilication.xpress.finjan2017.CalcDreams;
import com.myappilication.xpress.finjan2017.CalcExpense;
import com.myappilication.xpress.finjan2017.CalcPPF;
import com.myappilication.xpress.finjan2017.CalcSS;
import com.myappilication.xpress.finjan2017.CalcSip;
import com.myappilication.xpress.finjan2017.ChangePassword;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.ProfileSetting;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.TryFinStart;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;

import java.util.ArrayList;

/**
 * Created by suresh on 16/6/17.
 */
public class AllCalcListActivity extends AppCompatActivity {


    Toolbar toolbar;
    NetConnectionDetector NDC;
    Context context;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static ArrayList<Activity> calc_act_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.all_calc_list);

        final ArrayList<String> calcList = new ArrayList<String>();
        calcList.add("Dreams Calculator");
        calcList.add("Expense Calculator");
        //calcList.add("Financial Calculator");
        calcList.add("PPF Calculator");
        calcList.add("SIP Calculator");
        calcList.add("Sukanya Samriddhi Calculator");

        calc_act_list.add(AllCalcListActivity.this);

        context = AllCalcListActivity.this;

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.calc_layout);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if(ProfileSetting.profile_Act_list.size()>1) {
                        Log.d("profile act", "more 1");
                        for(int t=0; t<ProfileSetting.profile_Act_list.size(); t++){
                            int n = ProfileSetting.profile_Act_list.size();
                            if(n==1){
                                break;
                            }else{
                                ProfileSetting.profile_Act_list.remove(t);
                                ProfileSetting.profile_Act_list.get(t).finish();
                            }
                        }
                    }

                    if(FaqCategroyLIstActivity.faq_activity_list.size()>1) {
                        Log.d("profile act", "more 1");
                        for(int t=0; t<FaqCategroyLIstActivity.faq_activity_list.size(); t++){
                            int n = FaqCategroyLIstActivity.faq_activity_list.size();
                            if(n==1){
                                break;
                            }else{
                                FaqCategroyLIstActivity.faq_activity_list.remove(t);
                                FaqCategroyLIstActivity.faq_activity_list.get(t).finish();
                            }
                        }
                    }

                    if(AllCalcListActivity.calc_act_list.size()>1) {
                        Log.d("profile act", "more 1");
                        for(int t=0; t<AllCalcListActivity.calc_act_list.size(); t++){
                            int n = AllCalcListActivity.calc_act_list.size();
                            if(n==1){
                                break;
                            }else{
                                AllCalcListActivity.calc_act_list.remove(t);
                                AllCalcListActivity.calc_act_list.get(t).finish();
                            }
                        }
                    }

                }catch (Exception e){
                }
                finish();
            }
        });


        for(int t=0; t<calcList.size(); t++) {
            Button btnTag = new Button(AllCalcListActivity.this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                    LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            btnTag.setText(calcList.get(t));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            params.setMargins(15, 10, 15, 2);
            btnTag.setTextColor(Color.WHITE);

            btnTag.setLayoutParams(params);

            btnTag.setBackgroundResource(R.drawable.btn_grey_color);

            btnTag.setTag(t);
            linearLayout.addView(btnTag);

            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String btn = calcList.get(Integer.valueOf(v.getTag().toString()));

                    if(btn.equalsIgnoreCase("Dreams Calculator")){
                        Intent intent = new Intent(AllCalcListActivity.this, CalcDreams.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("allCalcList", "1");
                        startActivity(intent);
                    }else if(btn.equalsIgnoreCase("Expense Calculator")){
                        Intent intent = new Intent(AllCalcListActivity.this, CalcExpense.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("allCalcList", "1");
                        startActivity(intent);
                    }/*else if(btn.equalsIgnoreCase("Financial Calculator")){
                        Intent intent = new Intent(AllCalcListActivity.this, CalcPPF.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("allCalcList", "1");
                        startActivity(intent);
                    }*/else if(btn.equalsIgnoreCase("PPF Calculator")){
                        Intent intent = new Intent(AllCalcListActivity.this, CalcPPF.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("allCalcList", "1");
                        startActivity(intent);
                    }else if(btn.equalsIgnoreCase("SIP Calculator")){
                        Intent intent = new Intent(AllCalcListActivity.this, CalcSip.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("allCalcList", "1");
                        startActivity(intent);
                    }else if(btn.equalsIgnoreCase("Sukanya Samriddhi Calculator")){
                        Intent intent = new Intent(AllCalcListActivity.this, CalcSS.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("allCalcList", "1");
                        startActivity(intent);
                    }
                }
            });
        }
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

           /* case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;*/


            /*case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
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

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                // finish();
                return true;

            case R.id.ss_selection:
                startActivity(new Intent(getApplicationContext(), Scheme.class));
                // finish();
                return true;

           /* case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

           /* case R.id.finstaffcources:
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

       /* MenuItem item=menu.findItem(R.id.calc);
        item.setVisible(false);*/
       /* MenuItem item=menu.findItem(R.id.finpedia);
        MenuItem item1=menu.findItem(R.id.finstart_c);
        item.setVisible(false);
        item1.setVisible(false);*/

        return super.onPrepareOptionsMenu(menu);
    }
}
