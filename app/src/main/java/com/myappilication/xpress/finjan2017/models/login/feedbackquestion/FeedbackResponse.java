package com.myappilication.xpress.finjan2017.models.login.feedbackquestion;

/**
 * Created by suresh on 6/6/17.
 */
public class FeedbackResponse {

    private String status;
    private FeedbackResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FeedbackResult getResult() {
        return result;
    }

    public void setResult(FeedbackResult result) {
        this.result = result;
    }
}
