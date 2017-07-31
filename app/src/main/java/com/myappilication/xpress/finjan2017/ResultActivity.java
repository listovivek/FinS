package com.myappilication.xpress.finjan2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.RatingBar;
import android.widget.TextView;

/*import com.myappilication.xpress.finjan2017.api.RxClient;
import com.myappilication.xpress.finjan2017.model.login.Result.ResultReq;
import com.myappilication.xpress.finjan2017.model.login.Result.ResultResponse;
import com.myappilication.xpress.finjan2017.utils.helpers.SharedUtils;*/

import com.myappilication.xpress.finjan2017.models.login.Result.ResultReq;
import com.myappilication.xpress.finjan2017.models.login.Result.ResultResponse;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;
import com.myappilication.xpress.finjan2017.webservice.RxClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ResultActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    TextView a1;
    TextView tot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final Intent intent = getIntent();

        final String mcq_id = intent.getStringExtra("mcq_id");

        final String modul=intent.getStringExtra("modular");

        RxClient.get(ResultActivity.this).Result(new ResultReq(sharedpreferences.getString(SharedPrefUtils.SpEmail, ""),
                sharedpreferences.getString(SharedPrefUtils.SpMcq_id, ""),sharedpreferences.getString(SharedPrefUtils.user_ans, ""),
                sharedpreferences.getString(SharedPrefUtils.SpModuleId, ""),
                sharedpreferences.getString(SharedPrefUtils.score, "")), new Callback<ResultResponse>() {


            @Override
            public void success(ResultResponse resultResponse, Response response) {
             a1=(TextView)findViewById(R.id.a1);

                RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
//get text view
                TextView t = (TextView) findViewById(R.id.textResult);
//get score
                Bundle b = getIntent().getExtras();
                int score = b.getInt("score");
//display score
                 String s=b.getString("yourans");
                 a1.setText(s);

        //a1.setText("score",s);
               // a1.getText();
                bar.setRating(score);
                switch (score) {
                    case 1:
                    case 2:
                        t.setText("Oopsie! Better Luck Next Time!");
                        break;
                    case 3:
                    case 4:
                        t.setText("Hmmmm.. Someone's been reading a lot of trivia");
                        break;
                    case 5:
                        t.setText("Who are you? A trivia wizard???");
                        break;
                    default:
                        t.setText("Who are you? A trivia wizard???");
                        break;
                }
            }
   /* public void onBackPressed()
    {
        super.onBackPressed(); // this can go before or after your stuff below
        // do your stuff when the back button is pressed
        Intent intent = new Intent(getApplicationContext(), DashBoard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // super.onBackPressed(); calls finish(); for you

        // clear your SharedPreferences
        getSharedPreferences("preferenceName",0).edit().clear().commit();
    }
*/
            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_result, menu);
        return true;
    }
    public void onBackPressed()
    {
        super.onBackPressed(); // this can go before or after your stuff below
        // do your stuff when the back button is pressed

        finish();
        // super.onBackPressed(); calls finish(); for you

        // clear your SharedPreferences

    }
        }

