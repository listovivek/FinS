package com.myappilication.xpress.finjan2017.models.login.download;

/**
 * Created by balasri on 20/3/17.
 */

public class DownloadResult {
    private DownloadInfo info;

    public DownloadInfo getInfo ()
    {
        return info;
    }

    public void setInfo (DownloadInfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}



