package com.myappilication.xpress.finjan2017.models.login.availablecourses;

/**
 * Created by suresh on 2/6/17.
 */
public class AvailableCoursesResponse {

    private String status;
    private AvailableCoursesResult result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AvailableCoursesResult getResult() {
        return result;
    }

    public void setResult(AvailableCoursesResult result) {
        this.result = result;
    }


}
