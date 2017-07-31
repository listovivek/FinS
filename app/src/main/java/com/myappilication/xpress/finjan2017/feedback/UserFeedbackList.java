package com.myappilication.xpress.finjan2017.feedback;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by suresh on 7/6/17.
 */
public class UserFeedbackList extends AppCompatActivity {

    OfflineFeedbackDB feedbackDB;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

   RecyclerView mRecyclerView;

    static View.OnClickListener myOnClickListener;

    private RecyclerView.LayoutManager layoutManager;
   ArrayList<String> list, date;

    NetConnectionDetector NDC;
    Context context;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackrecycler);

        feedbackDB = new OfflineFeedbackDB(this);
        myOnClickListener = new MyOnClickListener(this);

        context = UserFeedbackList.this;

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        date = new ArrayList<>();

       list = feedbackDB.getdata(em);

        //ArrayList<String> values=new ArrayList<String>();
        if(OfflineFeedbackDB.nameLi.size() > 0){
            HashSet<String> hashSet = new HashSet<String>();
            HashSet<String> hashSet1 = new HashSet<String>();
            hashSet.addAll(OfflineFeedbackDB.nameLi);
            hashSet1.addAll(OfflineFeedbackDB.dateList);
            OfflineFeedbackDB.dateList.clear();
            OfflineFeedbackDB.nameLi.clear();
            OfflineFeedbackDB.dateList.addAll(hashSet1);
            OfflineFeedbackDB.nameLi.addAll(hashSet);
           // Collections.reverse(OfflineFeedbackDB.dateList);
        }

        try{
            mRecyclerView = (RecyclerView) findViewById(R.id.feed_recycler_view);
            mRecyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            FeedbackCustomAdapter adapter = new FeedbackCustomAdapter(list);
            mRecyclerView.setAdapter(adapter);
        }catch (Exception e){
            Log.d("user selected ans", "");
        }

    }

    private class MyOnClickListener implements View.OnClickListener{

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }


        @Override
        public void onClick(View v) {
            int selectedItemPosition = mRecyclerView.getChildPosition(v);
           List<FeedbackUserListItem> itm = feedbackDB.getparticularData(list.get(selectedItemPosition));

            sheetDialogg(itm);
        }

        private void sheetDialogg(List<FeedbackUserListItem> itm) {

            final Dialog mBottomSheetDialog = new Dialog(UserFeedbackList.this,
                    R.style.MaterialDialogSheet);

            View Bottom_view = getLayoutInflater().inflate(R.layout.feedback_submit_details, null);
            mBottomSheetDialog.setContentView(Bottom_view);

            TextView txtusername = (TextView) Bottom_view.findViewById(R.id.txtname);
            TextView txtemail = (TextView) Bottom_view.findViewById(R.id.txtemail);
            TextView txtnumber = (TextView) Bottom_view.findViewById(R.id.number);

            TextView txtqus1 = (TextView) Bottom_view.findViewById(R.id.txt_ques1);
            TextView txtans1 = (TextView) Bottom_view.findViewById(R.id.txt_ans1);

            TextView txtqus2 = (TextView) Bottom_view.findViewById(R.id.txt_ques2);
            TextView txtans2 = (TextView) Bottom_view.findViewById(R.id.txt_ans2);

            TextView txtqus3 = (TextView) Bottom_view.findViewById(R.id.txt_ques3);
            TextView txtans3 = (TextView) Bottom_view.findViewById(R.id.txt_ans3);

            TextView txtqus4 = (TextView) Bottom_view.findViewById(R.id.txt_ques4);
            TextView txtans4 = (TextView) Bottom_view.findViewById(R.id.txt_ans4);

            Button finishBtn = (Button) Bottom_view.findViewById(R.id.sheet_button);
            finishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });

            for(int n=0; n<itm.size(); n++){
                String name = itm.get(n).getUsername();
                ArrayList<String> qq = itm.get(n).getQuestion();
                // Log.d("position", ""+ list.get(selectedItemPosition));

                txtusername.setText(itm.get(n).getUsername());
                txtemail.setText(itm.get(n).getUseremail());
                txtnumber.setText(itm.get(n).getPhonenumber());

                txtqus1.setText("1. "+itm.get(n).getQuestion().get(0));
                txtqus2.setText("2. "+itm.get(n).getQuestion().get(1));
                txtqus3.setText("3. "+itm.get(n).getQuestion().get(2));
                txtqus4.setText("4. "+itm.get(n).getQuestion().get(3));

                txtans1.setText("Ans: "+itm.get(n).getCorrectAns().get(0));
                txtans2.setText("Ans: "+itm.get(n).getCorrectAns().get(1));
                txtans3.setText("Ans: "+itm.get(n).getCorrectAns().get(2));
                txtans4.setText("Ans: "+itm.get(n).getCorrectAns().get(3));
            }



            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);








            mBottomSheetDialog.show();
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
            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                if(ModuleFinJan.modFinjan != null){
                    ModuleFinJan.modFinjan.finish();
                }
                // finish();
                return true;*/

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

            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                // finish();
                return true;

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
                return true;

           /* case R.id.fb_post:
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

            case R.id.feedback:
                if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    return true;
                }else{
                    Toast.makeText(UserFeedbackList.this, "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    return false;
                }



           // case R.id.feedback_list:
              /*  OfflineFeedbackDB feedbackDB = new OfflineFeedbackDB(this);
                String em = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
                ArrayList<String> list = feedbackDB.getdata(em);
                if(list.size() > 0){
                    startActivity(new Intent(getApplicationContext(), UserFeedbackList.class));
                    return true;
                }else{
                    Toast.makeText(UserFeedbackList.this, "No records", Toast.LENGTH_SHORT).show();
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


}
