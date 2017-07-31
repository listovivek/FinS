package com.myappilication.xpress.finjan2017.mcqevalutiontest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by suresh on 27/4/17.
 */
public class MCQResult extends Activity {

    public static final String PREFS_NAME = "data";

    ArrayList<String> finalAnsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        ArrayList<String> temp = new ArrayList<>();
        finalAnsList = new ArrayList<>();

        for (int i=0; i<McQData.getInstance().getMCQQuestion().size(); ++i) {
            String answered = settings.getString(String.format("q%d", i+1),"opt0");

            int index = Integer.parseInt(answered.substring(3));
            Log.d("index", ""+ index);

            temp.add(McQData.getInstance().getMCQanswer1().get(i));
            temp.add(McQData.getInstance().getMCQanswer2().get(i));
            temp.add(McQData.getInstance().getMCQanswer3().get(i));
            temp.add(McQData.getInstance().getMCQanswer4().get(i));

            String f = temp.get(index);
            finalAnsList.add(f);

            Log.d("final answer", finalAnsList.toString());
            temp.clear();

        }
        McQData.getInstance().setUserSelectedData(finalAnsList);

    }
}
