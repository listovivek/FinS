package com.myappilication.xpress.finjan2017;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionModularQues;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;

public class EvalutionMcq extends AppCompatActivity {

    String remember_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cLzE4My44Mi4zMy4yMzI6ODA5NFwvYXBpXC9sb2dpbiIsImlhdCI6MTQ5MDI2NzAwMiwiZXhwIjoxNDkwMjcwNjAyLCJuYmYiOjE0OTAyNjcwMDIsImp0aSI6Ijg2NDNkMzZmNmE0ZTdjNmU3NTY0ODhmYWE2OGEzMjEzIn0.Z9X3oYp6FG9f1igBjmj7UQ5ldusCkyuLovKxGyoBFEE";
    List<EvalutionModularQues> quesList=new ArrayList<>();
    int score=0;
    int qid=0;
    EvalutionModularQues currentQ;
    TextView txtQuestion;
    RadioButton rda, rdb, rdc,RadioButton,rdd;
    Button butNext;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ArrayList<String>user_ans=new ArrayList<>();
    ArrayList<String>Ques=new ArrayList<>();

    TextView t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalution_mcq);
        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final RelativeLayout ll=(RelativeLayout) findViewById(R.id.relativeLayout1);
        DbHelper db=new DbHelper(this);
      //  db.onDelete();
        //quesList = db.getAllQuestions();
        txtQuestion = (TextView) findViewById(R.id.textView1);
        rda = (RadioButton) findViewById(R.id.radio0);
        rdb = (RadioButton) findViewById(R.id.radio1);
        rdc = (RadioButton) findViewById(R.id.radio2);
        rdd = (RadioButton) findViewById(R.id.radio3);
        butNext = (Button) findViewById(R.id.button1);
        setQuestionView();

        final Intent intent = getIntent();

     /*   t3 = (TextView) findViewById(R.id.dashboard);
        String name = intent.getStringExtra("Module_name");
        t3.setText(" " + name);*/
            butNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* RxClient.get(EvalutionMcq.this).Evalution(sharedpreferences.getString(SharedUtils.SpRememberToken, ""), new EvalutionReq(sharedpreferences.getString(SharedUtils.SpEmail, "")), new Callback<EvalutionResponse>() {
                        @Override
                        public void success(EvalutionResponse evalutionResponse, Response response) {*/
                            RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup1);

                            //RadioButton answer=(RadioButton)findViewById(R.id.radio0);
                            int selected = grp.getCheckedRadioButtonId();
                            RadioButton = (RadioButton) findViewById(selected);
                            String selection = (String) RadioButton.getText().toString();

                            Log.d("yourans", currentQ.getCorrect_ans() + " " + selection);
                            user_ans.add(currentQ.getCorrect_ans());
                            Ques.add(currentQ.getMcq_qus());
                            String user= String.valueOf(user_ans);
                            editor.putString("keyChannel", user_ans.toString());
                            editor.commit();
                            qid++;
                           String channel = (sharedpreferences.getString("keyChannel", ""));
                             Log.d("Answer",channel);
                            if (currentQ.getCorrect_ans().equals(selection)) {
                                score++;


                                editor.putString("key", String.valueOf(score).toString());
                                editor.commit();
                                String value=(sharedpreferences.getString("key", ""));
                                Log.d("sgsdgsd",value);
                                Log.d("score", "Your score" + score);
                            }

                          //  if (DashBoard.mcq_id.size() == qid) {
                               // currentQ = quesList.get(qid);
                                Intent intent = new Intent(EvalutionMcq.this, ActivityEvaluation.class);
                                Bundle b = new Bundle();
                                b.putInt("score", score);
                                b.putString("", selection);
                                b.putString("Questions",Ques.toString());
                                Log.d("Questions",Ques.toString());
                                b.putString("user_ans",user_ans.toString() );
                                Log.d("Questions",user_ans.toString());
                                //Your score
                                intent.putExtras(b); //Put your score to your next Intent
                                startActivity(intent);
                                finish();

                            }/* else {
                                setQuestionView();
                            }
                     //   }*/
/*
                        @Override
                        public void failure(RetrofitError error) {

                        }


                    });
                }*/

/*

    private int getSelectedAnswer(int radioSelected) {
        int answerSelected = 0;

        if(radioSelected == R.id.radio0){

            answerSelected = 1;

        }

        if(radioSelected == R.id.radio1){

            answerSelected = 2;

        }ArrayList

        if(radioSelected == R.id.radio2){

            answerSelected = 3;

        }

        if(radioSelected == R.id.radio3){

            answerSelected = 4;

        }

        return answerSelected;

    }
*/

            });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }
    private void setQuestionView()
    {
        //currentQ= DashBoard.quesList.get(qid);

        Log.d("mcq ques", currentQ.getMcq_qus());
        Log.d("mcq ans1", currentQ.getMcq_ans1());
        Log.d("mcq ans1", currentQ.getMcq_ans2());
        Log.d("mcq ans1", currentQ.getMcq_ans3());
        Log.d("mcq ans1", currentQ.getMcq_ans4());

       // Toast.makeText(EvalutionMcq.this, ""+ DashBoard.quesList.get(qid), Toast.LENGTH_SHORT).show();

        txtQuestion.setText(currentQ.getMcq_qus());
        rda.setText(currentQ.getMcq_ans1());
        rdb.setText(currentQ.getMcq_ans2());
        rdc.setText(currentQ.getMcq_ans3());
        rdd.setText(currentQ.getMcq_ans4());
        rda.setChecked(false);
        rdb.setChecked(false);
        rdc.setChecked(false);
        rdd.setChecked(false);

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