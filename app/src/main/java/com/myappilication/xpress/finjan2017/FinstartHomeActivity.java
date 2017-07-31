package com.myappilication.xpress.finjan2017;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.MyCamsActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.squareup.picasso.Picasso;

public class FinstartHomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton imageButton;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    WebView web;
    ProgressBar progressBar;

    NetConnectionDetector NDC;
    Context context;
    SearchView searchviewfaqlist;
    ImageView iv_searchiconall;
    String Search_query = "";
    int Index = 0;
    EditText et_searchcurhide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finstart_home_activity);
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(MainActivity.login != null){
                        MainActivity.login.finish();
                    }
                }catch (Exception e){

                }

                finish();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = FinstartHomeActivity.this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imageView = (ImageView) findViewById(R.id.imageView1);
        String img="http://183.82.33.232:8094/Dashboard_Back.jpg";


        et_searchcurhide = (EditText) findViewById(R.id.edit_hidecur);

        et_searchcurhide.setFocusable(true);

        // load image as Drawable

        searchviewfaqlist = (SearchView) findViewById(R.id.faq_searchall);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchviewfaqlist.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchviewfaqlist.setIconifiedByDefault(false);
        searchviewfaqlist.setFocusable(false);
        searchviewfaqlist.requestFocusFromTouch();


        searchviewfaqlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchviewfaqlist.setFocusableInTouchMode(true);
                return false;
            }
        });



       Picasso.with(getApplicationContext()).load(img).resize(2800, 1600).centerCrop().into(imageView);
        searchviewfaqlist.setIconified(false);


        ImageView searchViewIcon =
                (ImageView)searchviewfaqlist.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchViewIcon.setVisibility(View.GONE);

        ViewGroup linearLayoutSearchView =
                (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        linearLayoutSearchView.addView(searchViewIcon);
        searchViewIcon.setAdjustViewBounds(true);
        searchViewIcon.setMaxWidth(0);
        searchViewIcon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,(Gravity.RIGHT)));
        searchViewIcon.setImageDrawable(null);

        searchviewfaqlist.setFocusable(false);
        searchviewfaqlist.setFocusableInTouchMode(true);

        iv_searchiconall = (ImageView) findViewById(R.id.serchviewall_icon);



        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Search_query = "";
                if (!Search_query.equals(query))  {


                    Search_query = query;

                    Index = 0;
                    if (NDC.isConnected(context)) {
                        if (Search_query.length() >= 3) {

                            searchviewfaqlist.clearFocus();

                            Intent intent = new Intent(FinstartHomeActivity.this, FaqListActivity.class);

                            intent.putExtra("Search_query", Search_query);
                            editor.putString("Search_query",Search_query);
                            editor.commit();
                            startActivity(intent);

                        }else {

                            Toast.makeText(context, "Minimum 3 character length Required", Toast.LENGTH_LONG).show();
                        }

                    } else {

                        Toast.makeText(context, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }



                return false;
            }



            public boolean onQueryTextChange(String newText) {
                iv_searchiconall.setVisibility(View.GONE);



                if(newText.isEmpty()){
                    iv_searchiconall.setVisibility(View.VISIBLE);






                }
                // this is your adapter that will be filtered
                return true;
            }


        };



        searchviewfaqlist.setOnQueryTextListener(queryTextListener);


        final ImageButton view = (ImageButton) findViewById(R.id.Try_finjan);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent i=new Intent(getApplicationContext(), TryFinStart.class);
            startActivity(i);
            }

        });



        final TextView view1 = (TextView) findViewById(R.id.What_is_finstart);
        view1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(getApplicationContext(),Have_a_query.class);
                startActivity(i);*/

                Intent i=new Intent(getApplicationContext(), TryFinStart.class);
                startActivity(i);
            }

        });


        String temp_courseName = "Have a query - Ask Finpedia";
        SpannableString spanString = new SpannableString(temp_courseName);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);


        final TextView haveAQuery = (TextView) findViewById(R.id.have_a_Query);
        haveAQuery.setText(spanString);
        haveAQuery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(getApplicationContext(),Have_a_query.class);
                startActivity(i);*/

                Intent i=new Intent(getApplicationContext(), FaqCategroyLIstActivity.class);
                ModuleFinJan.courseID = "5";
                startActivity(i);
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

            case R.id.my_cams:
                startActivity(new Intent(getApplicationContext(), MyCamsActivity.class));
                // finish();
                return true;

            /*case R.id.li_invest:
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
    protected void onResume() {
        if (searchviewfaqlist != null) {

            searchviewfaqlist.setQuery("", false);
            searchviewfaqlist.clearFocus();
            //searchviewfaqlist.onActionViewCollapsed();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);}

        /*if (searchviewfaqlist != null) {
            searchviewfaqlist.setQuery("", false);
            searchviewfaqlist.clearFocus();

        }
*/
        super.onResume();
    }
}

