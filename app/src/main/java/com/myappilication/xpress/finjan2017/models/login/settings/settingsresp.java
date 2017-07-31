package com.myappilication.xpress.finjan2017.models.login.settings;

/**
 * Created by sureshmano on 3/9/2017.
 */

public class settingsresp {

    private settingresult result;

    private String status;

    public settingresult getResult ()
    {
        return result;
    }

    public void setResult (settingresult result)
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
