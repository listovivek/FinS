package com.myappilication.xpress.finjan2017.mcqevalutiontest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.ActivityEvaluation;
import com.myappilication.xpress.finjan2017.DbHelper;
import com.myappilication.xpress.finjan2017.ModuleFinJan;
import com.myappilication.xpress.finjan2017.R;
import com.myappilication.xpress.finjan2017.models.login.helpers.SharedPrefUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by suresh on 3/5/17.
 */
public class QuestionActivity extends Activity {


    private RadioButton answer1 = null;
    private RadioButton answer2 = null;
    private RadioButton answer3 = null;
    private RadioButton answer4 = null;
    private RadioGroup answers = null;

    public static int selected[] = null;
    private int correctAns[] = null;
    private int quesIndex = 0;

    ArrayList<String> temp = new ArrayList<>();
    TextView questionNum, question, moduleName;
    Intent in;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    boolean checkCondititon=false;

    DbHelper mDbHelper;
    ArrayList<String> userSelectedans;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcq_test);

        sharedpreferences = getSharedPreferences(SharedPrefUtils.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        moduleName = (TextView) findViewById(R.id.dashboard);
        questionNum = (TextView) findViewById(R.id.questionNumber);
        question = (TextView) findViewById(R.id.qDescription);

        in = getIntent();
        String modName = sharedpreferences.getString("Module_name", "");;
        moduleName.setText(""+ modName);

        mDbHelper = new DbHelper(QuestionActivity.this);

        /*String name = intent.getStringExtra("Module_name");
        t3.setText(" " + name);
*/

        TextView textView = (TextView) findViewById(R.id.tv_dashboard);
        textView.setText( ModuleFinJan.courseName);

        answer1 = (RadioButton) findViewById(R.id.opt0);
        answer2 = (RadioButton) findViewById(R.id.opt1);
        answer3 = (RadioButton) findViewById(R.id.opt2);
        answer4 = (RadioButton) findViewById(R.id.opt3);
        answers = (RadioGroup) findViewById(R.id.options);

        userSelectedans = mDbHelper.getUserSelectedans(getIntent().
                getStringExtra("list_of_mod_id"));

        selected = new int[McQData.getMcQData().getMCQQuestion().size()];
        Arrays.fill(selected, -1);
        correctAns = new int[McQData.getInstance().getMCQcorrectans().size()];
        Arrays.fill(correctAns, -1);

        this.showQus(0);

        Button next = ((Button) findViewById(R.id.nextBtn));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               next(v);
            }
        });

        //  next.setText(getString(R.string.nextButton));
        // }
        // disable previous question button if on first question
        if (0 == 1) {
            ((Button) findViewById(R.id.previousBtn)).setEnabled(false);
        } else {
            ((Button) findViewById(R.id.previousBtn)).setEnabled(true);
        }

        ImageButton imageButton = (ImageButton) findViewById(R.id.tb_normal_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.opt0:
                       answer1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
                        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
                        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
                        break;
                    case R.id.opt1:
                        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
                        answer2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
                        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
                        break;
                    case R.id.opt2:
                        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
                        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
                        answer3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
                        break;
                    case R.id.opt3:
                        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
                        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
                        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
                        answer4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        break;
                }
            }
        });
    }

    private void showQus(int index) {
        answers.check(-1);

        String e1 = McQData.getInstance().getMCQanswer3().get(index);
        String e2 = McQData.getInstance().getMCQanswer4().get(index);
        String e3 = McQData.getInstance().getMCQanswer2().get(index);
        String e4 = McQData.getInstance().getMCQanswer1().get(index);

        question.setText(McQData.getInstance().getMCQQuestion().get(index));
        answer1.setText(McQData.getInstance().getMCQanswer1().get(index));
        answer2.setText(McQData.getInstance().getMCQanswer2().get(index));

        if(e1!=null && e1.length()>0){
            answer3.setText(McQData.getInstance().getMCQanswer3().get(index));
            answer3.setVisibility(View.VISIBLE);
        }else{
            answer3.setText("");
            answer3.setVisibility(View.GONE);
        }
        if(e2!=null && e1.length()>0){
            answer4.setText(McQData.getInstance().getMCQanswer4().get(index));
            answer4.setVisibility(View.VISIBLE);
        }else{
            answer4.setText("");
            answer4.setVisibility(View.GONE);
        }



        Log.d("",selected[index]+ "");

        if (selected[index] == 0)
            answers.check(R.id.opt0);
       /* answer1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));*/
        if (selected[index] == 1)
            answers.check(R.id.opt1);
        /*answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
        answer2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));*/
        if (selected[index] == 2)
            answers.check(R.id.opt2);
       /* answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
        answer3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));*/
        if (selected[index] == 3)
            answers.check(R.id.opt3);
        /*answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
        answer4.setBackgroundColor(getResources().getColor(R.color.colorAccent));*/

        String f = answer1.getText().toString();
        Log.d("finla", f);



        if(userSelectedans.size() > 0){

            if(userSelectedans.size() == index){

            }
            if(answer1.getText().toString().equalsIgnoreCase(userSelectedans.get(index))){
                answers.check(R.id.opt0);
            }else if(answer2.getText().toString().equalsIgnoreCase(userSelectedans.get(index))){
                answers.check(R.id.opt1);
            }else if(answer3.getText().toString().equalsIgnoreCase(userSelectedans.get(index))){
                answers.check(R.id.opt2);
            }else if(answer4.getText().toString().equalsIgnoreCase(userSelectedans.get(index))){
                answers.check(R.id.opt3);
            }
            String ff = userSelectedans.get(index);
            Log.d("finla", ff);
        }



        setScoreTitle();
        answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
        answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
        answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
        answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));

       /* if(checkCondititon == false){
            answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
            answer2.setBackgroundColor(getResources().getColor(R.color.radiobutton2));
            answer3.setBackgroundColor(getResources().getColor(R.color.radiobutton3));
            answer4.setBackgroundColor(getResources().getColor(R.color.radiobutton4));
        }else{
           // answer1.setBackgroundColor(getResources().getColor(R.color.radiobutton1));
        }*/

    }

    private void setScoreTitle() {
        questionNum.setText("Questions  "+(quesIndex+1)+ "/" + McQData.getInstance().getMCQQuestion().size());

    }

    private void setAnswer() {
        if (answer1.isChecked())
            selected[quesIndex] = 0;
        if (answer2.isChecked())
            selected[quesIndex] = 1;
        if (answer3.isChecked())
            selected[quesIndex] = 2;
        if (answer4.isChecked())
            selected[quesIndex] = 3;

        Log.d("", Arrays.toString(selected));
        Log.d("",Arrays.toString(correctAns));

    }


    public void previous(View view) {
        checkCondititon = true;
        setAnswer();
        quesIndex--;
        if (quesIndex < 0){
            quesIndex = 0;
            view.setEnabled(false);
        }else{
            showQus(quesIndex);
        }
    }

    public void next(View view) {
        checkCondititon = false;
        if(answer1.isChecked() || answer2.isChecked() ||
                answer3.isChecked() || answer4.isChecked()) {

            setAnswer();
            quesIndex++;

            if(quesIndex >= McQData.getInstance().getMCQQuestion().size()){
                quesIndex = McQData.getInstance().getMCQQuestion().size() - 1;
               // Toast.makeText(QuestionActivity.this, "finished", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(QuestionActivity.this, ActivityEvaluation.class);
                i.putExtra("list_of_mod_id", getIntent().getStringExtra("list_of_mod_id"));
                startActivity(i);
                this.finish();

                ArrayList<String> cc = new ArrayList<>();
                ArrayList<String> corr = McQData.getInstance().getMCQcorrectans();
                ArrayList<String> finalAns = new ArrayList<String>();
                for(int t = 0; t< McQData.getInstance().getMCQQuestion().size(); t++) {

                    cc.add(McQData.getInstance().getMCQanswer1().get(t));
                    cc.add(McQData.getInstance().getMCQanswer2().get(t));
                    cc.add(McQData.getInstance().getMCQanswer3().get(t));
                    cc.add(McQData.getInstance().getMCQanswer4().get(t));

                    String userSelectAns = cc.get(selected[t]);
                    finalAns.add(userSelectAns);
                    cc.clear();

                }
                McQData.getInstance().setUserSelectedData(finalAns);
            }else{
                showQus(quesIndex);
            }

        }else{
            Toast.makeText(QuestionActivity.this, "Kindly click any one option", Toast.LENGTH_SHORT).show();
        }


    }
}
