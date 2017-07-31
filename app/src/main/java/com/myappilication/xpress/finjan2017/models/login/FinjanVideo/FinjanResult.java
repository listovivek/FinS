package com.myappilication.xpress.finjan2017.models.login.FinjanVideo;

/**
 * Created by balasri on 30/3/17.
 */
public class FinjanResult {
    private FinjanInfo info;

    public FinjanInfo getInfo ()
    {
        return info;
    }

    public void setInfo (FinjanInfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}


