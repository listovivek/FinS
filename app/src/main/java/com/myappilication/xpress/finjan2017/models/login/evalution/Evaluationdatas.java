package com.myappilication.xpress.finjan2017.models.login.evalution;

/**
 * Created by sureshmano on 4/18/2017.
 */



/**
 * Created by sureshmano on 3/20/2017.
 */

public class Evaluationdatas {

    private String mcq_qus;
    private String correct_ans;


    public  Evaluationdatas(String mcq_qus,String correct_ans){

        this.mcq_qus = mcq_qus;
        this.correct_ans = correct_ans;

    }

    public String getMcq_qus() {
        return mcq_qus;
    }

    public void setMcq_qus(String mcq_qus) {
        this.mcq_qus = mcq_qus;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }
}
