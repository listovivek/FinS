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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalcPPF extends AppCompatActivity {

    EditText et_ppf,et_years,et_rate,et_amtinvest ;
    Button btn_calculate;
    TextView btn_ppf_next;

    Toolbar toolbar;
    ImageButton imageButton;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    public static ArrayList<Integer> calcPPF_validation = new ArrayList<>();

    public static ArrayList<Activity> calc_ppf_act = new ArrayList<>();

    NetConnectionDetector NDC;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_newppf);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        et_ppf = (EditText) findViewById(R.id.calc_amt_ppf);
        et_years = (EditText) findViewById(R.id.calc_years);
        et_rate = (EditText) findViewById(R.id.calc_rate);
        et_amtinvest = (EditText) findViewById(R.id.calc_amtinvest);

        et_amtinvest.setEnabled(false);
        context = CalcPPF.this;

        calc_ppf_act.add(CalcPPF.this);

        btn_calculate = (Button) findViewById(R.id.calc_calculate);

        btn_ppf_next = (TextView) findViewById(R.id.calc_nextppf);
        // btn_ppf_next = (Button) findViewById(R.id.calc_nextppf);

        btn_ppf_next.setVisibility(View.GONE);

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



      /*  if (sharedpreferences.getString(SharedPrefUtils.SpCalcPPF,"").equalsIgnoreCase(null)){
            Toast.makeText(this, "Finish the first One", Toast.LENGTH_SHORT).show();
        }else
        {
            CalcAdapter.CaltoProcess++;
        }*/

        /*et_ppf.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_ppf,""));

        et_years.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_years,""));
        et_rate.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcet_rate,""));
        et_amtinvest.setText(sharedpreferences.getString(SharedPrefUtils.SpCalcPPF,""));*/











        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mtd_ppf_calculation();

                if (et_amtinvest.length()!=0){

                    btn_ppf_next.setVisibility(View.VISIBLE);

                }
                else{
                    btn_ppf_next.setVisibility(View.INVISIBLE);
                }




            }
        });

        btn_ppf_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String validataion = getIntent().getStringExtra("allCalcList");
                if(validataion!=null){
                    finish();
                }else{
                    calcPPF_validation.clear();
                    ArrayList<Integer> allList = new ArrayList<Integer>();

                    calcPPF_validation.add(1);

                    allList.addAll(calcPPF_validation);
                    allList.addAll(CalcSip.calcSip_validation);
                    allList.addAll(CalcSS.calcSS_validation);

                    if(CalcModuleActivity.wordList.size() == allList.size()){

                        if(DashBoard.mDashBoard != null){
                            DashBoard.mDashBoard.finish();
                        }

                        if(CalcModuleActivity.calcModuleActivity != null){
                            CalcModuleActivity.calcModuleActivity.finish();
                        }

                        Intent i = new Intent(CalcPPF.this, DashBoard.class);
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

    private void mtd_ppf_calculation() {

        if (et_ppf.length() == 0) {

            et_ppf.setError("Enter Amount");
            /*Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/

        } else if (et_years.length() == 0) {

            et_years.setError("Enter Year");
            /*Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/

        } else if (et_rate.length() == 0) {

            et_rate.setError("Enter Rate");
            /*Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/

        } else {

            Double ppf = Double.parseDouble(et_ppf.getText().toString());
            Double year = Double.parseDouble(et_years.getText().toString());
            Double percentage = Double.parseDouble(et_rate.getText().toString());

            Double pmt = ppf;
            Double rate = percentage;


            Double r = (rate / 100);
            Integer type = 1;
            Double nper = year;
            Double n = nper;
            Integer pv = 0;

            Double temp = Math.pow((r + 1), n);
            Double fv = (-pmt * (1 + r * type) * (1 - temp) / r) - pv * temp;


            et_amtinvest.setText(new DecimalFormat("##.##").format(fv));







        }

        editor.putString(SharedPrefUtils.SpCalcPPF,et_amtinvest.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_ppf,et_ppf.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_years,et_years.getText().toString());
        editor.putString(SharedPrefUtils.SpCalcet_rate,et_rate.getText().toString());
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
                String couponcode = sharedpreferences.getString("couponvalidation", "");

                if(couponcode.equalsIgnoreCase("fst104")){
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
                    Toast.makeText(CalcPPF.this, "Kindly check your network connection",
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* if (et_amtinvest.getText()!=null){
            CalcAdapter.CaltoProcess++;
        }*/
    }
}
