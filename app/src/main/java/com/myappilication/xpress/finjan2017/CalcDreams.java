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
import com.myappilication.xpress.finjan2017.menulist.*;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Double.isNaN;

public class CalcDreams extends AppCompatActivity {

    EditText et_amtdreams,et_yeardrems,et_fundcar,et_yearcar,
            et_fundhome,et_yearhome,
            et_fundedu,et_yearedu,
            et_fundret,et_yearret,
            et_fundotr,et_yearotr,
           et_fundcarnew;

    TextView tv_amtdreams,tv_yeardrems,tv_fundcar,tv_yearcar,tv_futcar,tv_invcar,
            tv_fundhome,tv_yearhome,tv_futhome,tv_invhome,
            tv_fundedu,tv_yearedu,tv_futedu,tv_invedu,
            tv_fundret,tv_yearret,tv_futret,tv_invret,
            tv_fundotr,tv_yearotr,tv_futotr,tv_invotr,
            tv_totalinvdr,tv_amtreqdr, et_futcar,et_invcar,et_futhome,et_invhome,et_futedu,et_invedu,et_futret,et_invret,
            et_futotr,et_invotr, et_totalinvdr,et_amtreqdr, btn_drmcontine;
    Button btn_nextdream,btn_callcualtedream;

    Toolbar toolbar;
    ImageButton imageButton;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    public static ArrayList<Integer> calcDreams_validation = new ArrayList<>();
    LinearLayout viewnew, calculation_view;

    NetConnectionDetector NDC;
    Context context;

    public static ArrayList<Activity> calcDreams_act = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcdreams);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        viewnew = (LinearLayout) findViewById(R.id.visiblitychk);
        calculation_view = (LinearLayout) findViewById(R.id.calculation_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        context = CalcDreams.this;
        calcDreams_act.add(CalcDreams.this);

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

        et_amtdreams = (EditText) findViewById(R.id.edit_amtdream);
        et_yeardrems = (EditText) findViewById(R.id.edit_yearsdr);


        et_fundcarnew = (EditText) findViewById(R.id.edit_fundscarnew);
        et_yearcar = (EditText) findViewById(R.id.edit_yearscar);
        et_futcar = (TextView) findViewById(R.id.edit_futcar);
        et_invcar = (TextView) findViewById(R.id.edit_intvercar);

        et_fundhome = (EditText) findViewById(R.id.edit_fundshome);
        et_yearhome = (EditText) findViewById(R.id.edit_yearshome);
        et_futhome = (TextView) findViewById(R.id.edit_futhome);
        et_invhome = (TextView) findViewById(R.id.edit_intverhome);

        et_fundedu = (EditText) findViewById(R.id.edit_fundsedu);
        et_yearedu = (EditText) findViewById(R.id.edit_yearsedu);
        et_futedu = (TextView) findViewById(R.id.edit_futedu);
        et_invedu = (TextView) findViewById(R.id.edit_intveredu);

        et_fundret = (EditText) findViewById(R.id.edit_fundsret);
        et_yearret = (EditText) findViewById(R.id.edit_yearsret);
        et_futret = (TextView) findViewById(R.id.edit_futret);
        et_invret = (TextView) findViewById(R.id.edit_intverret);

        et_fundotr = (EditText) findViewById(R.id.edit_fundsother);
        et_yearotr = (EditText) findViewById(R.id.edit_yearsother);
        et_futotr = (TextView) findViewById(R.id.edit_futother);
        et_invotr = (TextView) findViewById(R.id.edit_intverother);


        et_totalinvdr = (TextView) findViewById(R.id.edit_totalinv);
        et_amtreqdr = (TextView) findViewById(R.id.edit_amtreq);

        et_futcar.setEnabled(false);
        et_invcar.setEnabled(false);
        et_futhome.setEnabled(false);
        et_invhome.setEnabled(false);
        et_futedu.setEnabled(false);
        et_invedu.setEnabled(false);
        et_futret.setEnabled(false);
        et_invret.setEnabled(false);
        et_futotr.setEnabled(false);
        et_invotr.setEnabled(false);
        et_totalinvdr.setEnabled(false);
        et_amtreqdr.setEnabled(false);

        calculation_view.setVisibility(View.GONE);







     btn_callcualtedream = (Button) findViewById(R.id.button_calculatedream);
        
        btn_callcualtedream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd_dream_calculation();
                calculation_view.setVisibility(View.VISIBLE);
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
                    calcDreams_validation.clear();
                    ArrayList<Integer> allList = new ArrayList<Integer>();

                    calcDreams_validation.add(1);

                    allList.addAll(calcDreams_validation);

                    allList.addAll(CalcPPF.calcPPF_validation);
                    allList.addAll(CalcSip.calcSip_validation);
                    allList.addAll(CalcSS.calcSS_validation);
                    allList.addAll(CalcExpense.calcExpense_validation);

                    if(CalcModuleActivity.wordList.size() == allList.size()){
                        if(DashBoard.mDashBoard != null){
                            DashBoard.mDashBoard.finish();
                        }
                        if(CalcModuleActivity.calcModuleActivity != null){
                            CalcModuleActivity.calcModuleActivity.finish();
                        }
                        Intent i = new Intent(CalcDreams.this, DashBoard.class);
                        i.putExtra("list_of_module_id", getIntent().getStringExtra("list_of_module_id"));
                        startActivity(i);
                        finish();
                    }else{
                        finish();
                    }

               /* Intent in = new Intent(CalcDreams.this,CalcExpense.class);
                startActivity(in);*/
                }


            }
        });






    }



    public Double fv(Double my_nper,Double my_pv){
        Double dreams_rate=0.08;
        Double dreams_inflation=0.07;
        Double temp=Math.pow((1 + dreams_inflation),my_nper);
        Double fv_val=temp*my_pv;
        if (fv_val == null) {
            return null;
            
        }else {
            return fv_val;
        }

    }

    public  Double pmt(Double my_fv, Double my_nper){
        Double dreams_rate=0.08;
        Double dreams_inflation=0.07;
        Double pmt_rate=dreams_rate/12;
        Double pmt_time=my_nper*12;
        Double pmt_temp=Math.pow((1 + pmt_rate),pmt_time);
        Double pmt_x=pmt_rate*my_fv/(pmt_temp-1);
        Double pmt_y= pmt_x/(1+pmt_rate);
        if (pmt_y == null) {
            return null;
        }else {
            return pmt_y;
        }
    }

    private void mtd_dream_calculation() {

    if (et_fundcarnew.length()==0){
            et_fundcarnew.setError("Enter Funds Needed");
        }else if (et_yearcar.length()==0){
            et_yearcar.setError("Enter Years");
        }else if (et_fundhome.length()==0){
            et_fundhome.setError("Enter Funds Needed");
        }else if (et_yearhome.length()==0){
            et_yearhome.setError("Enter Years");
        }
        else if (et_fundedu.length()==0){
            et_fundedu.setError("Enter Funds Needed");
        }else if (et_yearedu.length()==0){
            et_yearedu.setError("Enter Years");
        }
        else if (et_fundret.length()==0){
            et_fundret.setError("Enter Funds Needed");
        }else if (et_yearret.length()==0){
            et_yearret.setError("Enter Years");
        }
        else if (et_fundotr.length()==0){
            et_fundotr.setError("Enter Funds Needed");
        }else if (et_yearotr.length()==0){
            et_yearotr.setError("Enter Years");
        }
         else {


            Double fundcar = Double.parseDouble(et_fundcarnew.getText().toString());
            Double yearcar = Double.parseDouble(et_yearcar.getText().toString());

            Double fundhome = Double.parseDouble(et_fundhome.getText().toString());
            Double yearhome = Double.parseDouble(et_yearhome.getText().toString());

            Double fundedu = Double.parseDouble(et_fundedu.getText().toString());
            Double yearedu = Double.parseDouble(et_yearedu.getText().toString());

            Double fundret = Double.parseDouble(et_fundret.getText().toString());
            Double yearret = Double.parseDouble(et_yearret.getText().toString());

            Double fundotr = Double.parseDouble(et_fundotr.getText().toString());
            Double yearotr = Double.parseDouble(et_yearotr.getText().toString());

           /* Double amtdreams = Double.parseDouble(et_amtdreams.getText().toString());
            Double yeardremas = Double.parseDouble(et_yeardrems.getText().toString());*/


            Double dreams_pv1 = fundcar;
            Double dreams_pv2 = fundhome;
            Double dreams_pv3 = fundedu;
            Double dreams_pv4 = fundret;
            Double dreams_pv5 = fundotr;

            Double dreams_nper1 = yearcar;
            Double dreams_nper2 = yearhome;
            Double dreams_nper3 = yearedu;
            Double dreams_nper4 = yearret;
            Double dreams_nper5 = yearotr;
/*
            Double dreams_neti = amtdreams;
            Double dreams_exp = yeardremas;*/


        /*    Double amt_left = dreams_neti - dreams_exp;*/
            //optional to set the value

            Double fv_vehicle = fv(dreams_nper1, dreams_pv1);
            Double pmt_vehicle = pmt(fv_vehicle, dreams_nper1);

            Double fv_vacation = fv(dreams_nper2, dreams_pv2);
            Double pmt_vacation = pmt(fv_vacation, dreams_nper2);

            Double fv_education = fv(dreams_nper3, dreams_pv3);
            Double pmt_education = pmt(fv_education, dreams_nper3);

            Double fv_marriage = fv(dreams_nper5, dreams_pv5);
            Double pmt_marriage = pmt(fv_marriage, dreams_nper5);

            Double fv_retirement = fv(dreams_nper4, dreams_pv4);
            Double pmt_retirement = pmt(fv_retirement, dreams_nper4);

            Double dreams_fv_amt = fv_vehicle + fv_vacation + fv_education + fv_marriage + fv_retirement;
            Double dreams_pmt_amt = pmt_vehicle + pmt_vacation + pmt_education + pmt_marriage + pmt_retirement;

        if(isNaN(fv_vehicle))
        {
            fv_vehicle=0.00;
        }
        if(isNaN(pmt_vehicle))
        {
            pmt_vehicle=0.00;
        }

        if(isNaN(fv_vacation))
        {
            fv_vacation=0.00;
        }

        if(isNaN(pmt_vacation))
        {
            pmt_vacation=0.00;
        }

        if(isNaN(fv_education))
        {
            fv_education=0.00;
        }

        if(isNaN(pmt_education))
        {
            pmt_education=0.00;
        }

        if(isNaN(fv_marriage))
        {
            fv_marriage=0.00;
        }
        if(isNaN(pmt_retirement))
        {
            pmt_retirement=0.00;
        }

        if(isNaN(pmt_marriage))
        {
            pmt_marriage=0.00;
        }



          et_futcar.setText(new DecimalFormat("##.##").format(fv_vehicle));
            et_invcar.setText(new DecimalFormat("##.##").format(pmt_vehicle));

            et_futhome.setText(new DecimalFormat("##.##").format(fv_vacation));
            et_invhome.setText(new DecimalFormat("##.##").format(pmt_vacation));

            et_futedu.setText(new DecimalFormat("##.##").format(fv_education));
            et_invedu.setText(new DecimalFormat("##.##").format(pmt_education));

            et_futret.setText(new DecimalFormat("##.##").format(fv_retirement));
            et_invret.setText(new DecimalFormat("##.##").format(pmt_retirement));

            et_futotr.setText(new DecimalFormat("##.##").format(fv_marriage));
            et_invotr.setText(new DecimalFormat("##.##").format(pmt_marriage));


          /*  et_totalinvdr.setText(new DecimalFormat("##.##").format(amt_left));
            et_amtreqdr.setText(new DecimalFormat("##.##").format(dreams_pmt_amt));*/
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
                startActivity(new Intent(getApplicationContext(), com.myappilication.xpress.finjan2017.menulist.Learning_centre.class));
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
