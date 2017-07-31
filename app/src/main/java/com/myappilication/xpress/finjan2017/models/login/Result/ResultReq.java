package com.myappilication.xpress.finjan2017.models.login.Result;

/**
 * Created by balasri on 28/3/17.
 */
public class ResultReq {
    String email;
    String mcq_id;
    String user_ans;
    String modular;
    String score;
    public ResultReq(String email, String mcq_id, String modular, String user_ans, String score){
        this.email = email;
        this.mcq_id = mcq_id;
        this.user_ans = user_ans;
        this.modular = modular;
        this.score = score;
    }
}
