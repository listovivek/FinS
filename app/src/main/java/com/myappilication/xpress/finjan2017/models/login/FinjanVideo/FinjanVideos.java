package com.myappilication.xpress.finjan2017.models.login.FinjanVideo;

/**
 * Created by balasri on 30/3/17.
 */
public class FinjanVideos {
    private String id;

    private String video_name;

    private String video_title;

    private String module_name;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getVideo_name ()
    {
        return video_name;
    }

    public void setVideo_name (String video_name)
    {
        this.video_name = video_name;
    }

    public String getVideo_title ()
    {
        return video_title;
    }

    public void setVideo_title (String video_title)
    {
        this.video_title = video_title;
    }

    public String getModule_name ()
    {
        return module_name;
    }

    public void setModule_name (String module_name)
    {
        this.module_name = module_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", video_name = "+video_name+", video_title = "+video_title+", module_name = "+module_name+"]";
    }
}



