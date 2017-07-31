package com.myappilication.xpress.finjan2017.models.login.dashboard;

/**
 * Created by balasri on 14/3/17.
 */

public class DashboardResult {
    private DashboardInfo info;

    public DashboardInfo getInfo ()
    {
        return info;
    }

    public void setInfo (DashboardInfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}