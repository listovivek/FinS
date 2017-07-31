package com.myappilication.xpress.finjan2017.models.login.profileedit;

/**
 * Created by sureshmano on 3/13/2017.
 */

public class profileresp {

    private profileresult result;

    private String status;

    public profileresult getResult ()
    {
        return result;
    }

    public void setResult (profileresult result)
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
