package com.myappilication.xpress.finjan2017.feedback;

import java.util.ArrayList;

/**
 * Created by suresh on 7/6/17.
 */
public class FeedbackUserListItem {


    private String username;
    private String email;
    private String useremail;
    private String phonenumber;
    private String datetime;
    private ArrayList<String> correctAns;
    private ArrayList<String> question;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public ArrayList<String> getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(ArrayList<String> correctAns) {
        this.correctAns = correctAns;
    }

    public ArrayList<String> getQuestion() {
        return question;
    }

    public void setQuestion(ArrayList<String> question) {
        this.question = question;
    }




}
