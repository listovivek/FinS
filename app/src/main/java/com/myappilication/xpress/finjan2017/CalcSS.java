package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.termscondition.Support;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Double.isNaN;

public class CalcSS extends AppCompatActivity {

    EditText et_sspmt, et_ssyears, et_ssrate, et_14years, et_21years ;
    Button btn_calculate;
    Toolbar toolbar;
    ImageButton imageButton;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    TextView btn_next_ss;
    NetConnectionDetector NDC;

    Context context;

    public static ArrayList<Integer> calcSS_validation = new ArrayList<>();

    public static ArrayList<Activity> calcss_act = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_ss);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        et_sspmt = (EditText) findViewById(R.id.calc_sspmt);
        et_ssyears = (EditText) findViewById(R.id.calc_ssyears);
        et_ssrate = (EditText) findViewById(R.id.calc_ssrate);
        et_14years = (EditText) findViewById(R.id.calc_ss14years);

        et_21years = (EditText) findViewById(R.id.calc_ss21years);

            context = CalcSS.this;

        calcss_act.add(CalcSS.this);

        et_14years.setEnabled(false);
        et_21years.setEnabled(false);

        btn_calculate = (Button) findViewById(R.id.calc_calculate);
       // btn_next_ss = (Button) findViewById(R.id.calc_nextss);

        btn_next_ss = (TextView) findViewById(R.id.calc_nextss);
        btn_next_ss.setVisibility(View.GONE);


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

    /*  if (sharedpreferences.getString(SharedPrefUtils.SpCalcSS21,"").equalsIgnoreCase(null)){
          Toast.makeText(this, "Firsit", Toast.LENGTH_SHORT).show();

        }else
      {
          CalcAdapter.CaltoProcess++;
      }*/

      /*  et_sspmt.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_sspmt,""));
        et_ssyears.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_ssyears,""));
        et_ssrate.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_ssrate,""));
        et_14years.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcSS14,""));
        et_21years.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcSS21,""));*/







        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mtd_ss_calculation();

                if (et_21years.length()!=0){

                    btn_next_ss.setVisibility(View.VISIBLE);

                }
                else{
                    btn_next_ss.setVisibility(View.INVISIBLE);
                }


                            }


        });

        btn_next_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String validataion = getIntent().getStringExtra("allCalcList");
                if(validataion!=null){
                    finish();
                }else{
                    calcSS_validation.clear();
                    ArrayList<Integer> allList = new ArrayList<Integer>();

                    calcSS_validation.add(1);

                    allList.addAll(calcSS_validation);
                    allList.addAll(CalcPPF.calcPPF_validation);
                    allList.addAll(CalcSip.calcSip_validation);

                    if(CalcModuleActivity.wordList.size() == allList.size()){
                        if(DashBoard.mDashBoard != null){
                            DashBoard.mDashBoard.finish();
                        }
                        if(CalcModuleActivity.calcModuleActivity != null){
                            CalcModuleActivity.calcModuleActivity.finish();
                        }
                        Intent i = new Intent(CalcSS.this, DashBoard.class);
                        i.putExtra("list_of_module_id", getIntent().getStringExtra("list_of_module_id"));
                        startActivity(i);
                        finish();
                    }else{
                        finish();
                    }

                }

            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
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

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                 finish();
                return true;

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                finish();
                return true;

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                finish();
                // finish();
                return true;

            case R.id.ss_selection:
                startActivity(new Intent(getApplicationContext(), Scheme.class));
                finish();
                // finish();
                return true;

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                finish();
                // finish();
                return true;*/

            /*case R.id.fb_post:
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
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    finish();
                    return true;
                }else{
                    Toast.makeText(CalcSS.this, "Kindly check your network connection",
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
                    Toast.makeText(DashBoard.this, "No records", Toast.LENGTH_SHORT).show();
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

       // getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }

    private void mtd_ss_calculation() {
        Double ss_pmt,ss_nper,ss_int;


        if (et_sspmt.length() == 0 ){

            et_sspmt.setError("Enter Amount");
            /*Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/

        }
        else if (et_ssyears.length() == 0){
            et_ssyears.setError("Enter Years");
        }
        else if  (et_ssrate.length() == 0){
            et_ssrate.setError("Enter Interest");
        } else {
            ss_pmt = Double.parseDouble(et_sspmt.getText().toString());
            ss_nper = Double.parseDouble(et_ssyears.getText().toString());


            ss_int = Double.parseDouble(et_ssrate.getText().toString());

            Double ss_per = ss_int/100;
            Integer type =1;
            Integer pv=0;
            Double temp=Math.pow((ss_per+1),ss_nper);
            Double fv = (-ss_pmt*(1+ss_per*type)*(1-temp)/ss_per)-pv*temp;
            Double ss_temp2=Math.pow((ss_per+1),7);
            Double secondres=ss_temp2*fv;
            if(isNaN(fv))
            {
                fv=0.00;
            }
            if(isNaN(secondres))
            {
                secondres=0.00;
            }


                /*double si = (amount * Interest * yearcount) / 100;*/
            et_14years.setText(new DecimalFormat("##.##").format(fv));
            et_21years.setText(new DecimalFormat("##.##").format(secondres));


        }

        editor.putString(SharedPrefUtils.SpCalcSS14, et_14years.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcSS21, et_21years.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_sspmt, et_sspmt.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_ssyears, et_ssyears.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_ssrate, et_ssrate.getText().toString());


        editor.commit();
        CalcAdapter.CaltoProcess++;






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (et_21years.getText()!=null){
            CalcAdapter.CaltoProcess++;
        }*/
    }
}
