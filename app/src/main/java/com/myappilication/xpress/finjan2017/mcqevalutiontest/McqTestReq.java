package com.myappilication.xpress.finjan2017.mcqevalutiontest;

import java.util.ArrayList;

/**
 * Created by suresh on 24/4/17.
 */
public class McqTestReq {

    String email;
    ArrayList<String> mcq_id;
    ArrayList<String> user_ans;
    String modular;
    String score;

    public McqTestReq(String email, ArrayList<String> mcqID_list, ArrayList<String> userSelectedData,
                      String modularid, String scorec) {

        this.email = email;
        this.mcq_id = mcqID_list;
        this.user_ans = userSelectedData;
        this.modular = modularid;
        this.score = scorec;
    }
}
