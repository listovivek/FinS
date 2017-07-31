package com.myappilication.xpress.finjan2017.models.login.newfaqcategorylist;

/**
 * Created by suresh on 15/6/17.
 */
public class NewFaqCategoryResponse {

    private String status;
    private FaqCategoryResult result;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FaqCategoryResult getResult() {
        return result;
    }

    public void setResult(FaqCategoryResult result) {
        this.result = result;
    }
}
