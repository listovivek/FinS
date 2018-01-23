package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.termscondition.Support;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Double.isNaN;

public class CalcExpense extends AppCompatActivity {

/*    EditText et_smartamt,et_smartsave,et_restamt,et_restsave,et_movieamt,et_moviedave,et_personalamt,
    et_personalsave,et_shopamt,et_shopsave,et_otheramt,et_othersave,et_creditamt,et_creditsave,et_totalexp,
    et_totalyears,et_rateannum,et_totalsave,et_savings,et_futvalue;*/

    Double total_savexp = 0.00;
    Double eatingoutsav = 0.00;

    Double moviesav = 0.00;

    Double shoppingsav = 0.00;
    Double creditcardsav =0.00;
    Double anyothersav =0.00;

    Double eatingout;
    Double movie;
    Double shopping;
    Double creditcard;
    Double anyother;


    TextView tv_totalsave,tv_totalexpense,tv_totyears,tv_totalrate,tv_savings,tv_futurevalue;

    EditText et_eatingamt,et_movieamt,et_shoppingamt,et_creditcard,et_anyother,et_totalexpense,et_eatingsave,
            et_moviesav,et_shoppingsave,et_creditcardsav,et_anysave,et_totlasav;

    TextView btn_drmcontine, tv_savingtitle,tv_expensetitle;
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
    String calcppf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcexpensenew);

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

        et_eatingamt = (EditText) findViewById(R.id.edit_eatamt);
        et_movieamt = (EditText) findViewById(R.id.edit_movieamt);
        et_shoppingamt = (EditText) findViewById(R.id.edit_shoppingamt);
        et_creditcard = (EditText) findViewById(R.id.edit_creditcardamt);
        et_anyother = (EditText) findViewById(R.id.edit_anyamt);
        et_totalexpense = (EditText) findViewById(R.id.edit_totalexpense);

        et_eatingsave = (EditText) findViewById(R.id.edit_eatsave);
        et_moviesav = (EditText) findViewById(R.id.edit_moviesave);
        et_shoppingsave = (EditText) findViewById(R.id.edit_shoppingsave);
        et_creditcardsav = (EditText) findViewById(R.id.edit_creditcardsave);
        et_anysave = (EditText) findViewById(R.id.edit_anysave);
        et_totlasav = (EditText) findViewById(R.id.edit_totalsave);


        tv_savingtitle = (TextView) findViewById(R.id.text_save_title);
        tv_expensetitle = (TextView) findViewById(R.id.expense_title);

       // calcppf = sharedpreferences.getString(SharedPrefUtils.SpCalcExpense,"");

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

        tv_totalexpense.setText("10");
        tv_totalrate.setText("10%");








        expense_calculation.setVisibility(View.GONE);


        btn_callcualteexpense = (Button) findViewById(R.id.button_calculateexpense);

        btn_callcualteexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd_expense_calculation();

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

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





    private void mtd_expense_calculation() {
        Double finalValueamt= 0.00;


        if (et_eatingamt.length()==0){

            eatingout = finalValueamt;

        }else{
            eatingout = Double.parseDouble(et_eatingamt.getText().toString());
        }


       if (et_movieamt.length()==0){
           movie = finalValueamt;
        }
       else{
           movie = Double.parseDouble(et_movieamt.getText().toString());
       }


         if (et_shoppingamt.length()==0){
             shopping = finalValueamt;
        }
        else{
             shopping = Double.parseDouble(et_shoppingamt.getText().toString());
         }

         if (et_creditcard.length()==0){

             creditcard = finalValueamt;
        }
        else {

             creditcard = Double.parseDouble(et_creditcard.getText().toString());
         }

        if (et_anyother.length()==0){
            anyother = finalValueamt;
        }else {
            anyother = Double.parseDouble(et_anyother.getText().toString());
        }





            tv_savingtitle.setVisibility(View.VISIBLE);
            et_eatingsave.setVisibility(View.VISIBLE);
            et_moviesav.setVisibility(View.VISIBLE);
            et_shoppingsave.setVisibility(View.VISIBLE);
            et_creditcardsav.setVisibility(View.VISIBLE);
            et_anysave.setVisibility(View.VISIBLE);
            et_totlasav.setVisibility(View.VISIBLE);

           /* eatingout = Double.parseDouble(et_eatingamt.getText().toString());
            movie = Double.parseDouble(et_movieamt.getText().toString());*/
            //shopping = Double.parseDouble(et_shoppingamt.getText().toString());
            //creditcard = Double.parseDouble(et_creditcard.getText().toString());
            //anyother = Double.parseDouble(et_anyother.getText().toString());


            Double total_amtexp=(eatingout) +(movie) + (shopping)+(creditcard)+(anyother);
            et_totalexpense.setText(new DecimalFormat("##.##").format(total_amtexp));

          //  editor.putString(SharedPrefUtils.SpCalcExpense,et_totalexpense.getText().toString());

          //  editor.commit();













            if (et_totalexpense.length()==0){
                tv_savingtitle.setVisibility(View.GONE);
                et_eatingsave.setVisibility(View.GONE);
                et_moviesav.setVisibility(View.GONE);
                et_shoppingsave.setVisibility(View.GONE);
                et_creditcardsav.setVisibility(View.GONE);
                et_anysave.setVisibility(View.GONE);
                et_totlasav.setVisibility(View.GONE);
                tv_expensetitle.setText("How much can you save per month?");
            }else {
                Double finalValue= 0.00;





                if (et_eatingsave.length()==0){
                    eatingoutsav = finalValue;
                }else {
                    eatingoutsav = Double.parseDouble(et_eatingsave.getText().toString());
                    expense_calculation.setVisibility(View.VISIBLE);
                }



                if (et_moviesav.length()==0){
                    moviesav = finalValue;
                }else {
                    moviesav = Double.parseDouble(et_moviesav.getText().toString());
                    expense_calculation.setVisibility(View.VISIBLE);
                }


                if (et_shoppingsave.length()==0){
                    shoppingsav = finalValue;
                }else {
                    shoppingsav = Double.parseDouble(et_shoppingsave.getText().toString());
                    expense_calculation.setVisibility(View.VISIBLE);
                }

                if (et_creditcardsav.length()==0){
                    creditcardsav = finalValue;
                }else {
                    creditcardsav = Double.parseDouble(et_creditcardsav.getText().toString());
                    expense_calculation.setVisibility(View.VISIBLE);
                }

                if (et_anysave.length()==0){
                    anyothersav = finalValue;
                }else {
                    anyothersav = Double.parseDouble(et_anysave.getText().toString());
                    expense_calculation.setVisibility(View.VISIBLE);
                }







                total_savexp=(eatingoutsav)+(moviesav) + (shoppingsav)+(creditcardsav)+(anyothersav);
                et_totlasav.setText(new DecimalFormat("##.##").format(total_savexp));
                tv_savings.setText(new DecimalFormat("##.##").format(total_savexp));



                Integer percentage = 10;


                Double percentage_rate =Double.valueOf(percentage/100.00);

                Integer years =10;

                Double sav = total_savexp;




                Double r_tenure=(percentage_rate/12.00);
                Double tenure=(years*12.00);

                Integer type=1;
                Integer pv=0;
                Double pownew=Math.pow(r_tenure+1,tenure);




                Double fvnew = (-total_savexp* (1 + r_tenure * type) * (1 - pownew) / r_tenure) - pv * pownew;

                if(isNaN(fvnew))
                {
                    fvnew=0.00;
                }
                if(isNaN(total_amtexp))
                {
                    total_amtexp=0.00;
                }

                if(isNaN(sav))
                {
                    sav=0.00;
                }


                tv_futurevalue.setText(new DecimalFormat("##.##").format(fvnew));




            }



































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
