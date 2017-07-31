package com.myappilication.xpress.finjan2017.models.login.dashboard;

/**
 * Created by balasri on 14/3/17.
 */

public class DashboardResponse {
    private DashboardResult result;

    private String status;

    public DashboardResult getResult ()
    {
        return result;
    }

    public void setResult (DashboardResult result)
    {
        this.result = result;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+", status = "+status+"]";
    }
}

