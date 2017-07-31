package com.myappilication.xpress.finjan2017.mcqevalutiontest;

import java.util.ArrayList;

/**
 * Created by suresh on 20/4/17.
 */
public class McQData {


    private static McQData mcQData = null;
    private ArrayList<String> mcqID_list;
    private ArrayList<String> MCQQuestion;
    private ArrayList<String> MCQanswer1;
    private ArrayList<String> MCQanswer2;
    private ArrayList<String> MCQanswer3;
    private ArrayList<String> MCQanswer4;


    private ArrayList<String> userSelectedData;

    private ArrayList<String> MCQcorrectans;

    public static McQData getInstance(){

        if(mcQData == null){
            mcQData = new McQData();
        }

        return mcQData;
    }

    public void setMCQid(ArrayList<String> MCQid) {

        this.mcqID_list = MCQid;
    }

    public void setMCQQuestion(ArrayList<String> MCQQuestion) {
        this.MCQQuestion = MCQQuestion;
    }

    public void setMCQanswer1(ArrayList<String> MCQanswer1) {
        this.MCQanswer1 = MCQanswer1;
    }

    public void setMCQanswer2(ArrayList<String> MCQanswer2) {
        this.MCQanswer2 = MCQanswer2;
    }

    public void setMCQanswer3(ArrayList<String> MCQanswer3) {
        this.MCQanswer3 = MCQanswer3;
    }

    public void setMCQanswer4(ArrayList<String> MCQanswer4) {
        this.MCQanswer4 = MCQanswer4;
    }

    public void setMCQcorrectans(ArrayList<String> MCQcorrectans) {
        this.MCQcorrectans = MCQcorrectans;
    }

    public static McQData getMcQData() {
        return mcQData;
    }

    public void setUserSelectedData(ArrayList<String> userSelectedData) {
        this.userSelectedData = userSelectedData;
    }




    public ArrayList<String> getUserSelectedData() {
        return userSelectedData;
    }

    public ArrayList<String> getMCQanswer4() {
        return MCQanswer4;
    }

    public ArrayList<String> getMCQanswer3() {
        return MCQanswer3;
    }

    public ArrayList<String> getMCQanswer2() {
        return MCQanswer2;
    }

    public ArrayList<String> getMCQanswer1() {
        return MCQanswer1;
    }

    public ArrayList<String> getMcqID_list() {
        return mcqID_list;
    }

    public ArrayList<String> getMCQQuestion() {
        return MCQQuestion;
    }

    public ArrayList<String> getMCQcorrectans() {
        return MCQcorrectans;
    }


}
