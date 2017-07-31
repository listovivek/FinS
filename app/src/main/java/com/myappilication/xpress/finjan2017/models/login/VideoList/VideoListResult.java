package com.myappilication.xpress.finjan2017.models.login.VideoList;

/**
 * Created by balasri on 15/5/17.
 */
public class VideoListResult {
    private VideoListInfo info;

    public VideoListInfo getInfo ()
    {
        return info;
    }

    public void setInfo (VideoListInfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}



