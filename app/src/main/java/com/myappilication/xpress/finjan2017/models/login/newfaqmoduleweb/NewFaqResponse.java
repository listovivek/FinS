package com.myappilication.xpress.finjan2017.models.login.newfaqmoduleweb;

/**
 * Created by suresh on 14/6/17.
 */
public class NewFaqResponse {

    private String status;
    private NewFaqResult result;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NewFaqResult getResult() {
        return result;
    }

    public void setResult(NewFaqResult result) {
        this.result = result;
    }
}
