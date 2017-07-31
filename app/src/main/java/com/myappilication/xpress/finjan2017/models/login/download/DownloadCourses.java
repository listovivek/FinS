package com.myappilication.xpress.finjan2017.models.login.download;

/**
 * Created by balasri on 20/3/17.
 */

public class DownloadCourses {
    private String created_date;

    private String video_encrypt;

    private String id;

    private String video_name;

    private String status;

    private String video_language;

    private String video_title;

    private String video_encrypt_type;

    public String getCreated_date ()
    {
        return created_date;
    }

    public void setCreated_date (String created_date)
    {
        this.created_date = created_date;
    }

    public String getVideo_encrypt ()
    {
        return video_encrypt;
    }

    public void setVideo_encrypt (String video_encrypt)
    {
        this.video_encrypt = video_encrypt;
    }

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

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getVideo_language ()
    {
        return video_language;
    }

    public void setVideo_language (String video_language)
    {
        this.video_language = video_language;
    }

    public String getVideo_title ()
    {
        return video_title;
    }

    public void setVideo_title (String video_title)
    {
        this.video_title = video_title;
    }

    public String getVideo_encrypt_type ()
    {
        return video_encrypt_type;
    }

    public void setVideo_encrypt_type (String video_encrypt_type)
    {
        this.video_encrypt_type = video_encrypt_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_date = "+created_date+", video_encrypt = "+video_encrypt+", id = "+id+", video_name = "+video_name+", status = "+status+", video_language = "+video_language+", video_title = "+video_title+", video_encrypt_type = "+video_encrypt_type+"]";
    }
}



