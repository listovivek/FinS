package com.myappilication.xpress.finjan2017;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

public class TryFinStart extends AppCompatActivity {

    WebView web;
    ProgressBar progressBar;

    NetConnectionDetector NDC;
    Context context;
    Toolbar toolbar;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    Button buy_fin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_finstart);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = TryFinStart.this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        buy_fin = (Button) findViewById(R.id.buy_finstart);



        buy_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NDC.isConnected(context)){
                    dialogst();
                }else{
                    Toast.makeText(TryFinStart.this, "Kindly check your network connection", Toast.LENGTH_SHORT).
                            show();
                }

            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        web = (WebView) findViewById(R.id.commentsView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://183.82.33.232:8094/finstart.html");
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            //progressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
           // buy_fin.setVisibility(View.VISIBLE);

            try {
                String couponcode = sharedpreferences.getString("couponvalidation", "");
                if(couponcode.equalsIgnoreCase("fst104")){
                    buy_fin.setVisibility(View.GONE);
                }else{
                    buy_fin.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){

            }
        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private void dialogst() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TryFinStart.this);
        View bView = getLayoutInflater().inflate(R.layout.custom_couponcode, null);
        dialogBuilder.setView(bView);

        TextView title = (TextView) bView.findViewById(R.id.buy);
        String temp_courseName = "Buy Now";
        SpannableString spanString = new SpannableString(temp_courseName);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        title.setText(spanString);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TryFinStart.this,
                        "Purchase developing process going on", Toast.LENGTH_LONG).show();
            }
        });

        //title.setText(avCourseList.get(Integer.valueOf(pp)));

        Button discard_btn = (Button) bView.findViewById(R.id.discard_button);
        Button send_btn = (Button) bView.findViewById(R.id.send_button);

        final EditText coupon = (EditText) bView.findViewById(R.id.edit1);

        final AlertDialog al = dialogBuilder.create();
        al.show();

        discard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                al.dismiss();
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(coupon.getText().toString().length()>0){
                    //pushCoupon(coupon, al, mBottomSheetDialog);
                    if(coupon.getText().toString().equalsIgnoreCase("fst104")){
                        editor.putString("couponvalidation", "fst104");
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), ListofModuleFinjan.class);
                        i.putExtra("moduleID", "5");
                        ModuleFinJan.courseID = "5";
                        ModuleFinJan.courseName = "Finstart";
                        al.dismiss();
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(TryFinStart.this,
                                "Kindly give valid coupon code", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(TryFinStart.this,
                            "Kindly give your coupon code", Toast.LENGTH_LONG).show();
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

            case R.id.learning_center:
                startActivity(new Intent(getApplicationContext(), Learning_centre.class));
                // finish();
                return true;


            /*case R.id.li_invest:
                startActivity(new Intent(getApplicationContext(), Link_To_Interest.class));
                // finish();
                return true;*/

            case R.id.calc:
                startActivity(new Intent(getApplicationContext(), AllCalcListActivity.class));
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
}
