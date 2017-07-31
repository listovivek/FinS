package com.myappilication.xpress.finjan2017.newfaqcategroylist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.ChangePassword;
import com.myappilication.xpress.finjan2017.DatabaseHandler;
import com.myappilication.xpress.finjan2017.FaqActivity;
import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.MainActivity;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.ProfileSetting;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.TryFinStart;
import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.feedback.OfflineFeedbackDB;
import com.myappilication.xpress.finjan2017.feedback.UserFeedbackList;
import com.myappilication.xpress.finjan2017.feedpost.FB_Posts;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.models.login.newfaqcategorylist.NewFaqCategoryReq;
import com.myappilication.xpress.finjan2017.models.login.newfaqcategorylist.NewFaqCategoryResponse;
import com.myappilication.xpress.finjan2017.models.login.newfaqmoduleweb.NewFaqRequest;
import com.myappilication.xpress.finjan2017.models.login.newfaqmoduleweb.NewFaqResponse;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.OfflineDatabaseHelper;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 15/6/17.
 */
public class FaqCategroyLIstActivity extends AppCompatActivity {

    LinearLayout lLayout;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    Context con;

    public static List<Faqlistdatas> faqList = new ArrayList<>();
    Faqlistdatas listDatas;

    DatabaseHandler dbHd = new DatabaseHandler(this);
    ProgressBar progressBar;

    Toolbar toolbar;

    NetConnectionDetector NDC;
    public static FaqCategroyLIstActivity mFaqList;

    public static ArrayList<Activity> faq_activity_list = new ArrayList<>();

    FaqOfflineDatabase offlineDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_category_list);

        con = FaqCategroyLIstActivity.this;
        mFaqList = FaqCategroyLIstActivity.this;

        faq_activity_list.add(FaqCategroyLIstActivity.this);

        offlineDatabase = new FaqOfflineDatabase(con);

        lLayout = (LinearLayout) findViewById(R.id.faq_category_layout);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FaqOfflineDatabase.offlineCname.clear();
        FaqOfflineDatabase.offlineCID.clear();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        offlineDatabase.getFaqList();
        if(NDC.isConnected(con)) {
            faqCatListFmWB();
        }else if(FaqOfflineDatabase.offlineCname.size()>0 && FaqOfflineDatabase.offlineCname!=null){
            offline();
        }else{
           Toast.makeText(con, "Kindly check your network connection", Toast.LENGTH_LONG).show();
            finish();
        }


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

                    if(faq_activity_list.size()>1) {
                        Log.d("profile act", "more 1");
                        for(int t=0; t<faq_activity_list.size(); t++){
                            int n = faq_activity_list.size();
                            if(n==1){
                                break;
                            }else{
                                faq_activity_list.remove(t);
                                faq_activity_list.get(t).finish();
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
    }

    private void offline() {
        progressBar.setVisibility(View.INVISIBLE);
        for(int n = 0; n< FaqOfflineDatabase.offlineCname.size(); n++){
            final Button btn = new Button(con);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                    LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            btn.setText(FaqOfflineDatabase.offlineCname.get(n));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.
                    LayoutParams.MATCH_PARENT);

            params.setMargins(15, 10, 15, 2);
            btn.setTextColor(Color.WHITE);
            btn.setTypeface(null, Typeface.BOLD);
            btn.setBackgroundResource(R.drawable.btn_grey_color);

            btn.setLayoutParams(params);
            btn.setTag(n);
            lLayout.addView(btn);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("value", v.getTag().toString());
                    progressBar.setVisibility(View.INVISIBLE);
                    String cID = FaqOfflineDatabase.offlineCID.get(Integer.valueOf(v.getTag().toString()));
                    faqList.clear();
                   offlineDatabase.getQuesAns(cID);
                   // faqList.clear();
                    if(NDC.isConnected(con)){
                        newFaqModuleWBService(cID, FaqOfflineDatabase.offlineCname.
                                get(Integer.valueOf(v.getTag().toString())));
                    }else if(faqList.size()>0 && faqList != null){
                        //faqList.add(list);
                        Intent i = new Intent(FaqCategroyLIstActivity.this, FaqActivity.class);
                        i.putExtra("faqname", FaqOfflineDatabase.offlineCname.get(Integer.valueOf(v.getTag().toString())));
                        startActivity(i);
                    }else{
                        Toast.makeText(con, "Kindly check your network connection", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    private void faqCatListFmWB() {
        String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");

        RxClient.get(getApplicationContext()).getNewFaqCategory(token,
                new NewFaqCategoryReq(ModuleFinJan.courseID),
                new Callback<NewFaqCategoryResponse>() {
                    @Override
                    public void success(NewFaqCategoryResponse res, Response response) {
                        final ArrayList<String> categoryName = new ArrayList<String>();
                        final ArrayList<String> categoryID = new ArrayList<String>();

                        int cN = res.getResult().getInfo().getCategories().length;

                        for(int n=0; n<cN; n++){
                            categoryName.add(res.getResult().getInfo().getCategories()[n].getCategories_name());
                            categoryID.add(res.getResult().getInfo().getCategories()[n].getCategories());
                        }

                        offlineDatabase.addCategoryName(categoryID, categoryName);

                        for(int n=0; n<categoryName.size(); n++){
                            final Button btn = new Button(con);
                            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                                    LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            btn.setText(categoryName.get(n));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.
                                    LayoutParams.MATCH_PARENT);

                            params.setMargins(15, 10, 15, 2);
                            btn.setTextColor(Color.WHITE);
                            btn.setTypeface(null, Typeface.BOLD);
                            btn.setBackgroundResource(R.drawable.btn_grey_color);

                            btn.setLayoutParams(params);
                            btn.setTag(n);
                            lLayout.addView(btn);


                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("value", v.getTag().toString());

                                    String cID = categoryID.get(Integer.valueOf(v.getTag().toString()));
                                    faqList.clear();
                                    offlineDatabase.getQuesAns(cID);

                                    if(NDC.isConnected(con)){
                                        progressBar.setVisibility(View.VISIBLE);
                                        newFaqModuleWBService(cID, categoryName.get(Integer.valueOf(v.getTag().toString())));
                                    }else if(faqList.size()>0 && faqList != null){
                                        //faqList.add(list);
                                        Intent i = new Intent(FaqCategroyLIstActivity.this, FaqActivity.class);
                                        i.putExtra("faqname", categoryName.get(Integer.valueOf(v.getTag().toString())));
                                        startActivity(i);
                                    }else{
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(con, "Kindly check your network connection", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try{
                            progressBar.setVisibility(View.INVISIBLE);
                        final AlertDialog.Builder dialogBuilder = new
                                AlertDialog.Builder(FaqCategroyLIstActivity.this);
                        View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
                        dialogBuilder.setView(bView);
                        Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);
                        NewFaqCategoryResponse usere = (NewFaqCategoryResponse)
                                error.getBodyAs(NewFaqCategoryResponse.class);
                        TextView t = (TextView) bView.findViewById(R.id.dialog_text);
                        t.setText(usere.getError());

                        final AlertDialog al = dialogBuilder.create();
                        al.show();

                        send_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                al.dismiss();
                                finish();
                            }
                        });

                        }catch (Exception e){
                          /*  Toast.makeText(FaqCategroyLIstActivity.this, "Service not response",
                                    Toast.LENGTH_LONG).show();
                            finish();*/

                            progressBar.setVisibility(View.INVISIBLE);
                            final AlertDialog.Builder dialogBuilder = new
                                    AlertDialog.Builder(FaqCategroyLIstActivity.this);
                            View bView = getLayoutInflater().inflate(R.layout.custom_feedback_alert, null);
                            dialogBuilder.setView(bView);
                            Button send_btn = (Button) bView.findViewById(R.id.feedback_okbtn);
                            /*NewFaqCategoryResponse usere = (NewFaqCategoryResponse)
                                    error.getBodyAs(NewFaqCategoryResponse.class);*/
                            TextView t = (TextView) bView.findViewById(R.id.dialog_text);
                            t.setText("No Categories For this Course");

                            final AlertDialog al = dialogBuilder.create();
                            al.show();

                            send_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    al.dismiss();
                                    finish();
                                }
                            });

                        }
                    }
                });
    }

    private void newFaqModuleWBService(final String catid, final String name) {
        String token = sharedpreferences.getString(SharedPrefUtils.SpRememberToken, "");

        RxClient.get(getApplicationContext()).
                getNewFaqModuleQs(token,
                        new NewFaqRequest(ModuleFinJan.courseID, catid),
                        new Callback<NewFaqResponse>() {
                            @Override
                            public void success(NewFaqResponse faqresp, Response response) {
                                faqList.clear();
                                for (int i = 0; i < faqresp.getResult().getInfo().getFaq().length; i++) {
                                    String nn = faqresp.getResult().getInfo().getFaq()[i].getFaq_qus();
                                    listDatas = new Faqlistdatas(faqresp.getResult().getInfo().getFaq()[i].getFaq_qus(),
                                    faqresp.getResult().getInfo().getFaq()[i].getFaq_ans());
                                    faqList.add(listDatas);
                                }

                                offlineDatabase.addQuesAns(faqList, catid);

                                editor.putString("courseID", catid);
                                editor.commit();

                                /*dbHd.OnDelete();
                                dbHd.addContact(faqList);*/

                                Log.d("", "");

                                Intent i = new Intent(FaqCategroyLIstActivity.this, FaqActivity.class);
                                i.putExtra("faqname", name);
                                startActivity(i);
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                try{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    NewFaqResponse usere = (NewFaqResponse) error.getBodyAs(NewFaqResponse.class);
                                    Toast.makeText(FaqCategroyLIstActivity.this, usere.getError(),
                                            Toast.LENGTH_LONG).show();
                                }catch (Exception e){
                                    Toast.makeText(FaqCategroyLIstActivity.this, "Service not response",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        });

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

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
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



               /* Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                i.putExtra("moduleID", "5");
                ModuleFinJan.courseID = "5";
                ModuleFinJan.courseName = "Finstart";
                startActivity(i);*/

                return true;

            case R.id.profile_menu:
                startActivity(new Intent(getApplicationContext(), ProfileSetting.class));
                return true;
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/

            case R.id.changepassword:
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
                return true;

            case R.id.feedback:
                if (NDC.isConnected(con)) {
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

       // MenuItem item=menu.findItem(R.id.finpedia);
      //  MenuItem item1=menu.findItem(R.id.finstaffcources);

        // item.setVisible(false);
       // item1.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_all, menu);

        return true;
    }

}
