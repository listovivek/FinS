package com.myappilication.xpress.finjan2017.models.login.forget;

/**
 * Created by sureshmano on 3/29/2017.
 */

public class forgotresp {

    private String status;

    private forgotinfo info;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public forgotinfo getInfo ()
    {
        return info;
    }

    public void setInfo (forgotinfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", info = "+info+"]";
    }
}



