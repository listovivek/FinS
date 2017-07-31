package com.myappilication.xpress.finjan2017.models.login.settings;

/**
 * Created by sureshmano on 3/13/2017.
 */

public class settingresult {

    private settinginfo info;

    public settinginfo getInfo ()
    {
        return info;
    }

    public void setInfo (settinginfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}
