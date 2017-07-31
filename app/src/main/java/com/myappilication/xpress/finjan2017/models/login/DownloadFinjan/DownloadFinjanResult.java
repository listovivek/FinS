package com.myappilication.xpress.finjan2017.models.login.DownloadFinjan;

/**
 * Created by balasri on 20/4/17.
 */
public class DownloadFinjanResult {
    private DownloadFinjanInfo info;

    public DownloadFinjanInfo getInfo ()
    {
        return info;
    }

    public void setInfo (DownloadFinjanInfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}

