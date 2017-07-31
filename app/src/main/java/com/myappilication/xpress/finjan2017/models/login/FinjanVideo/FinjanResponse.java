package com.myappilication.xpress.finjan2017.models.login.FinjanVideo;

/**
 * Created by balasri on 30/3/17.
 */
public class FinjanResponse {
    private FinjanResult result;

    private String status;

    public FinjanResult getResult ()
    {
        return result;
    }

    public void setResult (FinjanResult result)
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


