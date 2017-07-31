package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Double.isNaN;

public class CalcExpense extends AppCompatActivity {

    EditText et_smartamt,et_smartsave,et_restamt,et_restsave,et_movieamt,et_moviedave,et_personalamt,
    et_personalsave,et_shopamt,et_shopsave,et_otheramt,et_othersave,et_creditamt,et_creditsave,et_totalexp,
    et_totalyears,et_rateannum,et_totalsave,et_savings,et_futvalue;

    TextView tv_totalsave,tv_totalexpense,tv_totyears,tv_totalrate,tv_savings,tv_futurevalue, btn_drmcontine;


    Button btn_callcualteexpense;

    Toolbar toolbar;
    ImageButton imageButton;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    public static ArrayList<Integer> calcExpense_validation = new ArrayList<>();
    LinearLayout viewnew,expense_calculation;

    NetConnectionDetector NDC;

    public static ArrayList<Activity> calc_expense_act = new ArrayList<>();

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcexpense);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        viewnew = (LinearLayout) findViewById(R.id.visiblitychk);

        expense_calculation = (LinearLayout) findViewById(R.id.expense_calculation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        context = CalcExpense.this;

        calc_expense_act.add(CalcExpense.this);

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


        et_smartamt = (EditText) findViewById(R.id.edit_smartamt);
        et_smartsave = (EditText) findViewById(R.id.edit_smartsave);

        et_restamt = (EditText) findViewById(R.id.edit_restamt);
        et_restsave = (EditText) findViewById(R.id.edit_restsave);

        et_movieamt = (EditText) findViewById(R.id.edit_movamt);
        et_moviedave = (EditText) findViewById(R.id.edit_movsave);

        et_personalamt = (EditText) findViewById(R.id.edit_personalamt);
        et_personalsave = (EditText) findViewById(R.id.edit_personalsave);

        et_shopamt = (EditText) findViewById(R.id.edit_shopamt);
        et_shopsave = (EditText) findViewById(R.id.edit_shoptsave);

        et_creditamt = (EditText) findViewById(R.id.edit_creditamt);
        et_creditsave = (EditText) findViewById(R.id.edit_creditsave);

        et_otheramt = (EditText) findViewById(R.id.edit_otheramt);
        et_othersave = (EditText) findViewById(R.id.edit_othersave);

        tv_totalexpense = (TextView) findViewById(R.id.edit_totexpense);
        tv_totalsave = (TextView) findViewById(R.id.edit_totsave);
        tv_totyears = (TextView) findViewById(R.id.edit_totyears);
        tv_totalrate = (TextView) findViewById(R.id.edit_rateannum);

        tv_savings = (TextView) findViewById(R.id.edit_savings);
        tv_futurevalue = (TextView) findViewById(R.id.edit_futvalue);


        tv_totalexpense.setEnabled(false);
        tv_totalsave.setEnabled(false);
        tv_totyears.setEnabled(false);
        tv_totalrate.setEnabled(false);

        tv_savings.setEnabled(false);
        tv_futurevalue.setEnabled(false);

        tv_totyears.setText("10");
        tv_totalrate.setText("10%");




     expense_calculation.setVisibility(View.GONE);



        btn_callcualteexpense = (Button) findViewById(R.id.button_calculateexpense);

        btn_callcualteexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd_expense_calculation();
                expense_calculation.setVisibility(View.VISIBLE);
            }
        });


        btn_drmcontine = (TextView) findViewById(R.id.button_dremcontinue);

        btn_drmcontine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validataion = getIntent().getStringExtra("allCalcList");
                if(validataion!=null){
                    finish();
                }else{

                    calcExpense_validation.clear();
                    ArrayList<Integer> allList = new ArrayList<Integer>();

                    calcExpense_validation.add(1);

                    allList.addAll(calcExpense_validation);

                    allList.addAll(CalcPPF.calcPPF_validation);
                    allList.addAll(CalcSip.calcSip_validation);
                    allList.addAll(CalcSS.calcSS_validation);
                    allList.addAll(CalcDreams.calcDreams_validation);

                    if(CalcModuleActivity.wordList.size() == allList.size()){
                        if(DashBoard.mDashBoard != null){
                            DashBoard.mDashBoard.finish();
                        }
                        if(CalcModuleActivity.calcModuleActivity != null){
                            CalcModuleActivity.calcModuleActivity.finish();
                        }
                        Intent i = new Intent(CalcExpense.this, DashBoard.class);
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
/*
    public String is_nan(String my_val){
        if(isNaN(my_val)){
            Toast.makeText(context, "Enter Only Numbers", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return my_val;
        }
    }*/




    private void mtd_expense_calculation() {

        if (et_smartamt.length()==0){
            et_smartamt.setError("Enter Amount");
        }else if (et_smartsave.length()==0){
            et_smartsave.setText("Enter Saving Amount");
        }
        else if (et_restamt.length()==0){
            et_restamt.setText("Enter Amount");
        }
        else if (et_restsave.length()==0){
            et_restsave.setText("Enter Saving Amount");
        }
        else if (et_movieamt.length()==0){
            et_movieamt.setText("Enter Amount");
        }
        else if (et_moviedave.length()==0){
            et_moviedave.setText("Enter Saving Amount");
        }
        else if (et_personalamt.length()==0){
            et_personalamt.setText("Enter Amount");
        }
        else if (et_personalsave.length()==0){
            et_personalsave.setText("Enter Saving Amount");
        }

        else if (et_shopamt.length()==0){
            et_shopamt.setText("Enter Amount");
        }
        else if (et_shopsave.length()==0){
            et_shopsave.setText("Enter Saving Amount");
        }

        else if (et_creditamt.length()==0){
            et_creditamt.setText("Enter Amount");
        }
        else if (et_creditsave.length()==0){
            et_creditsave.setText("Enter Saving Amount");
        }
        else if (et_otheramt.length()==0){
            et_otheramt.setText("Enter Amount");
        }
        else if (et_othersave.length()==0){
            et_othersave.setText("Enter Saving Amount");
        }
        else {


            Double smartphone = Double.parseDouble(et_smartamt.getText().toString());
            Double restaurant = Double.parseDouble(et_restamt.getText().toString());
            Double movies = Double.parseDouble(et_movieamt.getText().toString());
            Double shopping = Double.parseDouble(et_shopamt.getText().toString());
            Double personalcare = Double.parseDouble(et_personalamt.getText().toString());
            Double creditcard = Double.parseDouble(et_creditamt.getText().toString());
            Double otherexpense = Double.parseDouble(et_otheramt.getText().toString());


            Double smartphonesave = Double.parseDouble(et_smartsave.getText().toString());
            Double restaurantsave = Double.parseDouble(et_restsave.getText().toString());
            Double moviessave = Double.parseDouble(et_moviedave.getText().toString());
            Double shoppingsave = Double.parseDouble(et_shopsave.getText().toString());
            Double personalsave = Double.parseDouble(et_personalsave.getText().toString());
            Double creditsave = Double.parseDouble(et_creditsave.getText().toString());
            Double othersave = Double.parseDouble(et_othersave.getText().toString());

            //Double ratee = Double.parseDouble(et_totalyears.getText().toString());



            Double total_expx=(creditcard) +(restaurant) + (movies)+(otherexpense)+(personalcare) + (shopping) + (smartphone);
            Double sav=(creditsave) + (restaurantsave) + (moviessave) + (othersave) + (personalsave) + (shoppingsave) + (smartphonesave);




            Integer r = 10;


            Double rate =Double.valueOf(r/100.00);
            Integer years =10;

            double tenure=(years*12.00);
            Double r_tenure=(rate/12.00);
            Integer type=1;
            Integer pv=0;
            Double pownew=Math.pow(r_tenure+1,tenure);


            Double fvnew = (-sav* (1 + r_tenure * type) * (1 - pownew) / r_tenure) - pv * pownew;

            if(isNaN(fvnew))
            {
                fvnew=0.00;
            }
            if(isNaN(total_expx))
            {
                total_expx=0.00;
            }

            if(isNaN(sav))
            {
                sav=0.00;
            }



            tv_futurevalue.setText(new DecimalFormat("##.##").format(fvnew));

            tv_totalexpense.setText(new DecimalFormat("##.##").format(total_expx));


            tv_totalsave.setText(new DecimalFormat("##.##").format(sav));


            tv_savings.setText(new DecimalFormat("##.##").format(sav));








        }

      /*  et_totalinvdr = (EditText) findViewById(R.id.edit_amtdream);
        et_amtreqdr = (EditText) findViewById(R.id.edit_amtreq);
*/














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

            case R.id.finstart_c:
                String couponcode = sharedpreferences.getString("couponvalidation", "");

                if(couponcode.equalsIgnoreCase("fst104")){
                    Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                    i.putExtra("moduleID", "5");
                    finish();

                    ModuleFinJan.courseID = "5";
                    ModuleFinJan.courseName = "Finstart";
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), TryFinStart.class);
                    finish();
                    startActivity(i);

                }
                return true;

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                finish();
                ModuleFinJan.courseID = "5";
                // finish();
                return true;

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                finish();
                return true;

            case R.id.learning_center:
                startActivity(new Intent(getApplicationContext(), Learning_centre.class));
                finish();
                // finish();
                return true;

            case R.id.articles:
                startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                finish();
                return true;

            case R.id.ss_selection:
                /*startActivity(new Intent(getApplicationContext(), Scheme.class));
                finish();*/
                return true;

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                finish();
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

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

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
