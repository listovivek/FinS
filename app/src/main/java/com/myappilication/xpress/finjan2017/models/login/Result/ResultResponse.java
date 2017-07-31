package com.myappilication.xpress.finjan2017.models.login.Result;

/**
 * Created by balasri on 28/3/17.
 */
public class ResultResponse {
    private String result;

    private String status;

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
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


