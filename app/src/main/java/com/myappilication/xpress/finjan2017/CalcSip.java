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

public class CalcSip extends AppCompatActivity {

    EditText et_calcloan,et_calcinterest,et_calcterm,et_calcinvest,et_calcmaturity;
    Button btn_calculate;
    Toolbar toolbar;
    ImageButton imageButton;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static ArrayList<Integer> calcSip_validation = new ArrayList<>();
    TextView btn_next_sip;

    Context context;
    NetConnectionDetector NDC;

    public static ArrayList<Activity> calcSip_act = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_sip);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        et_calcloan = (EditText) findViewById(R.id.calc_loan);

        et_calcterm = (EditText) findViewById(R.id.calc_term);
        et_calcinterest = (EditText) findViewById(R.id.calc_interest);
        et_calcinvest = (EditText) findViewById(R.id.calc_total);
        et_calcmaturity = (EditText) findViewById(R.id.calc_maturity);
        btn_calculate = (Button) findViewById(R.id.calc_calculate);
      //  btn_next_sip = (Button) findViewById(R.id.calc_nextsip);

        btn_next_sip = (TextView) findViewById(R.id.calc_nextsip);

        btn_next_sip.setVisibility(View.GONE);

        et_calcinvest.setEnabled(false);
        et_calcmaturity.setEnabled(false);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        calcSip_act.add(CalcSip.this);


        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     /*  if (sharedpreferences.getString(SharedPrefUtils.SpCalcSicalcmaturity,"").equalsIgnoreCase(null)){
           Toast.makeText(this, "Finish the first One", Toast.LENGTH_SHORT).show();
        }
        else
       {
           CalcAdapter.CaltoProcess++;
       }*/

        /*et_calcloan.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_calcloan,""));
        et_calcterm.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_calcterm,""));
        et_calcinterest.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_calcinterest,""));
        et_calcinvest.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_calcinvest,""));
        et_calcmaturity.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcSicalcmaturity,""));*/






        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mtd_sip_calculation();
                if (et_calcmaturity.length()!=0){

                    btn_next_sip.setVisibility(View.VISIBLE);

                }
                else{
                    btn_next_sip.setVisibility(View.INVISIBLE);
                }




            }
        });

        btn_next_sip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String validataion = getIntent().getStringExtra("allCalcList");
                if(validataion!=null){
                    finish();
                }else{
                    calcSip_validation.clear();
                    ArrayList<Integer> allList = new ArrayList<Integer>();

                    calcSip_validation.add(1);

                    allList.addAll(calcSip_validation);
                    allList.addAll(CalcPPF.calcPPF_validation);
                    allList.addAll(CalcSS.calcSS_validation);

                    if(CalcModuleActivity.wordList.size() == allList.size()){
                        if(DashBoard.mDashBoard != null){
                            DashBoard.mDashBoard.finish();
                        }
                        if(CalcModuleActivity.calcModuleActivity != null){
                            CalcModuleActivity.calcModuleActivity.finish();
                        }
                        Intent i = new Intent(CalcSip.this, DashBoard.class);
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

    private void mtd_sip_calculation() {

        if (et_calcloan.length() == 0) {

            et_calcloan.setError("Enter Loan Amount");
            /*Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/

        }  else if (et_calcterm.length() == 0) {

            et_calcterm.setError("Enter Term");
            /*Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/

        }else if (et_calcinterest.length() == 0) {

            et_calcinterest.setError("Enter Interest");
            /*Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/

        }

        else {

            Double amount = Double.parseDouble(et_calcloan.getText().toString());
            Double Interest = Double.parseDouble(et_calcinterest.getText().toString());
            Double years = Double.parseDouble(et_calcterm.getText().toString());


            Double yearcount = (years * 12);
            Double inv = (amount) * yearcount;

            Double r = (Interest / 1200);
            Double temp = Math.pow((r + 1), yearcount);
            Integer pv = 0;
            Integer type = 1;

            Double fv = (-amount * (1 + r * type) * (1 - temp) / r) - pv * temp;

            if(isNaN(inv))
            {
                inv=0.00;
            }
            if(isNaN(fv))
            {
                fv=0.00;

            }


                /*double si = (amount * Interest * yearcount) / 100;*/
            et_calcinvest.setText(new DecimalFormat("##.##").format(inv));
            et_calcmaturity.setText(new DecimalFormat("##.##").format(fv));



        }

        editor.putString(SharedPrefUtils.SpCalcSicalcmaturity, et_calcmaturity.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_calcloan, et_calcloan.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_calcterm, et_calcterm.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_calcinterest, et_calcinterest.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_calcinvest, et_calcinvest.getText().toString());
        editor.commit();
        CalcAdapter.CaltoProcess++;
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


            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                finish();
                // finish();
                return true;

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
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
                    Toast.makeText(CalcSip.this, "Kindly check your network connection",
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

     //   getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* if (et_calcmaturity.getText().toString()!=null){
            CalcAdapter.CaltoProcess++;
        }*/
    }

}
