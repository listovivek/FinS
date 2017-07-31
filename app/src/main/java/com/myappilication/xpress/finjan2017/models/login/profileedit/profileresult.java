package com.myappilication.xpress.finjan2017.models.login.profileedit;

/**
 * Created by sureshmano on 3/13/2017.
 */

public class profileresult {

    private profileinfo info;

    public profileinfo getInfo ()
    {
        return info;
    }

    public void setInfo (profileinfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}
