package com.myappilication.xpress.finjan2017.models.login.download;

/**
 * Created by balasri on 20/3/17.
 */

public class DownloadResponse {
    private DownloadResult result;

    private String status;

    public DownloadResult getResult ()
    {
        return result;
    }

    public void setResult (DownloadResult result)
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



