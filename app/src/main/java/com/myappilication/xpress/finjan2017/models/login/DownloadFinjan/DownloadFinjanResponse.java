package com.myappilication.xpress.finjan2017.models.login.DownloadFinjan;

/**
 * Created by balasri on 20/4/17.
 */
public class DownloadFinjanResponse {
    private DownloadFinjanResult result;

    private String status;

    public DownloadFinjanResult getResult ()
    {
        return result;
    }

    public void setResult (DownloadFinjanResult result)
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


