package com.myappilication.xpress.finjan2017.models.login.searchfaq;

/**
 * Created by sureshmano on 3/23/2017.
 */

public class searchresp {

    private searchresult result;

    private String status;

    public searchresult getResult ()
    {
        return result;
    }

    public void setResult (searchresult result)
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
