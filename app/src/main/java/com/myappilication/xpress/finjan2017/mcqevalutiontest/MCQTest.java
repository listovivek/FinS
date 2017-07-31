package com.myappilication.xpress.finjan2017.mcqevalutiontest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.R;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by suresh on 26/4/17.
 */
public class MCQTest extends Activity {

    public RadioButton mcqRadiobutton1, mcqRadiobutton2, mcqRadiobutton3, mcqRadiobutton4;
    TextView mcqQues;
    RadioGroup rg;
    String rItem;

    int count=0,count1=0;

    int questionNumber;

    ArrayList<String> mcqans1 = new ArrayList<>();
    ArrayList<String> mcqans2 = new ArrayList<>();
    ArrayList<String> mcqans3 = new ArrayList<>();
    ArrayList<String> mcqans4 = new ArrayList<>();

    ArrayList<String> mcqQus = new ArrayList<>();


    ArrayList<String> selectans = new ArrayList<>();

    ArrayList<String> count_list = new ArrayList<>();

    Stack<Integer> previousQuestions = new Stack<Integer>();
    public static final String PREFS_NAME = "data";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.mcq_test);

       // mcqQues = (TextView) findViewById(R.id.mcq_test_ques);

        rg = (RadioGroup) findViewById(R.id.radioGroup1);

        mcqRadiobutton1 = (RadioButton) findViewById(R.id.ra);
        mcqRadiobutton2 = (RadioButton) findViewById(R.id.r1);
        mcqRadiobutton3 = (RadioButton) findViewById(R.id.r2);
        mcqRadiobutton4 = (RadioButton) findViewById(R.id.r3);


        mcqQus = McQData.getInstance().getMCQQuestion();


        mcqans1 = McQData.getInstance().getMCQanswer1();
        mcqans2 = McQData.getInstance().getMCQanswer2();
        mcqans3 = McQData.getInstance().getMCQanswer3();
        mcqans4 = McQData.getInstance().getMCQanswer4();


        mcqQues.setText(mcqQus.get(count));
        mcqRadiobutton1.setText(mcqans1.get(count));
        mcqRadiobutton2.setText(mcqans2.get(count));
        mcqRadiobutton3.setText(mcqans3.get(count));
        mcqRadiobutton4.setText(mcqans4.get(count));



       // displayQuestion(1);



        Button next = (Button) findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mcqRadiobutton1.isChecked() || mcqRadiobutton2.isChecked() ||
                        mcqRadiobutton3.isChecked() || mcqRadiobutton4.isChecked()){
                    selectans.add(rItem);
                    Log.d("selected answer", selectans.toString());
                    count1++;

                    Log.d("count increase", ""+count);

                    if(count1 == mcqQus.size()){
                        Toast.makeText(MCQTest.this, "finish", Toast.LENGTH_SHORT).show();
                    }else{
                        count++;

                        count_list.add(String.valueOf(count));

                        mcqQues.setText(mcqQus.get(count));
                        mcqRadiobutton1.setText(mcqans1.get(count));
                        mcqRadiobutton2.setText(mcqans2.get(count));
                        mcqRadiobutton3.setText(mcqans3.get(count));
                        mcqRadiobutton4.setText(mcqans4.get(count));
                    }

                    mcqRadiobutton1.setChecked(false);
                    mcqRadiobutton2.setChecked(false);
                    mcqRadiobutton3.setChecked(false);
                    mcqRadiobutton4.setChecked(false);



                }else{
                    Toast.makeText(MCQTest.this, "kindly click anyone answer", Toast.LENGTH_SHORT).show();
                }



            }
        });

        Button pre = (Button) findViewById(R.id.pre_button);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                count1--;
                Log.d("count decrease", ""+count);



                if(count1 == -1){
                    Toast.makeText(MCQTest.this, "finish", Toast.LENGTH_SHORT).show();
                }else{
                    count--;

                    count_list.remove(count);

                    mcqQues.setText(mcqQus.get(count));

                    mcqRadiobutton1.setText(mcqans1.get(count));
                    mcqRadiobutton2.setText(mcqans2.get(count));
                    mcqRadiobutton3.setText(mcqans3.get(count));
                    mcqRadiobutton4.setText(mcqans4.get(count));

                }

                String sans = selectans.get(count);
                Log.d("selected answer", sans);

                /*for(int i=0; i<radioButtonlist.size(); i++){
                    if(radioButtonlist.get(i).getText().toString().equalsIgnoreCase(sans)){
                        radioButtonlist.get(i).isChecked();

                    }
                }*/

               // selectans.set(count, rItem);
                //Log.d("replace", selectans.toString());

               // selectans.add(rItem);

            }
        });



        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.ra:
                        rItem = mcqRadiobutton1.getText().toString();
                        Log.d("name", rItem);

                        break;
                    case R.id.r1:
                        rItem = mcqRadiobutton2.getText().toString();
                        Log.d("name", rItem);

                        break;
                    case R.id.r2:
                        rItem = mcqRadiobutton3.getText().toString();
                        Log.d("name", rItem);

                        break;
                    case R.id.r3:
                        rItem = mcqRadiobutton4.getText().toString();
                        Log.d("name", rItem);

                        break;
                }
            }
        });


    }

    private void displayQuestion(int n) {
        questionNumber = n;
        mcqQues.setText(mcqQus.get(n));
        mcqRadiobutton1.setText(mcqans1.get(n));
        mcqRadiobutton2.setText(mcqans2.get(n));
        mcqRadiobutton3.setText(mcqans3.get(n));
        mcqRadiobutton4.setText(mcqans4.get(n));

        if(n == mcqQus.size()){

        }else{
            previousQuestions.push(questionNumber);
            saveAnswer(questionNumber);

        }



    }

    private void saveAnswer(int n) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        RadioGroup r = (RadioGroup) findViewById(R.id.radioGroup1);
        editor.putString(String.format("q%d",n), getResources().getResourceEntryName(r.getCheckedRadioButtonId()));
        editor.apply();
    }
}
