package com.myappilication.xpress.finjan2017.models.login.feedbackQAreq;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by suresh on 9/6/17.
 */
public class FBQuesAnsReq {

    ArrayList<String> question;
    ArrayList<String> correct_ans;
    String username;
    String email;
    String phone_number;


    public FBQuesAnsReq(ArrayList<String> feedbackQuestion,
                        ArrayList<String> feedbackAns, String strName,
                        String strEmail, String strNum) {

        email= strEmail;
        question = feedbackQuestion;
        correct_ans = feedbackAns;
        username = strName;
        phone_number = strNum;



    }
}
