package com.myappilication.xpress.finjan2017.models.login.VideoList;

/**
 * Created by balasri on 15/5/17.
 */
public class VideoListResponse {
    private VideoListResult result;

    private String status;

    public VideoListResult getResult ()
    {
        return result;
    }

    public void setResult (VideoListResult result)
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


