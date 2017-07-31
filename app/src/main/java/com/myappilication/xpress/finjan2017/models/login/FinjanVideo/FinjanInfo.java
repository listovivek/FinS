package com.myappilication.xpress.finjan2017.models.login.FinjanVideo;

/**
 * Created by balasri on 30/3/17.
 */
public class FinjanInfo {
    private FinjanVideos[] finjanvideo;

    public FinjanVideos[] getFinjanvideo ()
    {
        return finjanvideo;
    }

    public void setFinjanvideo (FinjanVideos[] finjanvideo)
    {
        this.finjanvideo = finjanvideo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [finjanvideo = "+finjanvideo+"]";
    }
}


