package com.myappilication.xpress.finjan2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.allcalculatorlist.AllCalcListActivity;
import com.myappilication.xpress.finjan2017.feedback.FeedActivity;
import com.myappilication.xpress.finjan2017.feedpost.FB_Posts;
import com.myappilication.xpress.finjan2017.menulist.Learning_centre;
import com.myappilication.xpress.finjan2017.menulist.Link_To_Interest;
import com.myappilication.xpress.finjan2017.menulist.MediaActivity;
import com.myappilication.xpress.finjan2017.menulist.Scheme;
import com.myappilication.xpress.finjan2017.models.login.changepassword.ChangePasswordreq;
import com.myappilication.xpress.finjan2017.models.login.changepassword.Changepasswordresp;
import com.myappilication.xpress.finjan2017.models.login.helpers.NetConnectionDetector;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.newfaqcategroylist.FaqCategroyLIstActivity;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by suresh on 8/5/17.
 */
public class ChangePassword extends AppCompatActivity {

    EditText newPassword, oldPassword, confirmPassword;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    ImageButton imageButton;

    NetConnectionDetector NDC;
    Context context;

    ProgressBar progressBar;

    public static ArrayList<Activity> changep_act = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        newPassword = (EditText) findViewById(R.id.new_password);
        oldPassword = (EditText) findViewById(R.id.old_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        context = ChangePassword.this;
        changep_act.add(ChangePassword.this);

        imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        Button confirmBtn = (Button) findViewById(R.id.chag_pass_confim);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPassword.getText().toString().length() > 0){
                    if(oldPassword.getText().toString().length() > 0){
                        if(confirmPassword.getText().toString().length() > 0){
                            calWebService();
                        }else{
                            Toast.makeText(ChangePassword.this, "Please enter confirm password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ChangePassword.this, "Please enter old password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ChangePassword.this, "Please enter new password", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    private void calWebService() {
        progressBar.setVisibility(View.VISIBLE);
        String email = sharedpreferences.getString(SharedPrefUtils.SpEmail, "");
        RxClient.get(ChangePassword.this).Changepassword(sharedpreferences.
                        getString(SharedPrefUtils.SpRememberToken, ""),
                new ChangePasswordreq(
                        email,
                        oldPassword.getText().toString(),
                        newPassword.getText().toString(),
                        confirmPassword.getText().toString()

                ), new Callback<Changepasswordresp>() {
                    @Override
                    public void success(Changepasswordresp changepasswordresp, Response response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ChangePassword.this,
                                "change password success", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(ChangePassword.this,
                                    "password incorrect, Kindly check your password", Toast.LENGTH_SHORT).show();
                        oldPassword.setText("");
                        newPassword.setText("");
                        confirmPassword.setText("");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                return true;

            /*case R.id.finstaffcources:
                startActivity(new Intent(getApplicationContext(), ModuleFinJan.class));
                return true;*/

            case R.id.finpedia:
                startActivity(new Intent(getApplicationContext(), FaqCategroyLIstActivity.class));
                ModuleFinJan.courseID = "5";
                // finish();
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

            /*case R.id.fb_post:
                startActivity(new Intent(getApplicationContext(), FB_Posts.class));
                return true;*/

            case R.id.feedback:
                if (NDC.isConnected(context)) {
                    startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                    return true;
                }else{
                    Toast.makeText(getApplicationContext(), "Kindly check your network connection",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

            //case R.id.changepassword:
               /* startActivity(new Intent(getApplicationContext(), ChangePassword.class));
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
