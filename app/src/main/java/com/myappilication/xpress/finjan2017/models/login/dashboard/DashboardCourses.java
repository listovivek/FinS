package com.myappilication.xpress.finjan2017.models.login.dashboard;

/**
 * Created by balasri on 14/3/17.
 */

public class DashboardCourses {
    private String video_encrypt;

    private String id;

    private String video_name;

    private String calculator_name;

    private String video_image;

    private String video_language;

    private String video_title;

    private String video_encrypt_type;

    private String module_name;

    private String file_Name;

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

    public String getCalculator_name ()
    {
        return calculator_name;
    }

    public void setCalculator_name (String calculator_name)
    {
        this.calculator_name = calculator_name;
    }

    public String getVideo_image ()
    {
        return video_image;
    }

    public void setVideo_image (String video_image)
    {
        this.video_image = video_image;
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

    public String getModule_name ()
    {
        return module_name;
    }

    public void setModule_name (String module_name)
    {
        this.module_name = module_name;
    }

    public String getFile_Name() {
        return file_Name;
    }

    public void setFile_Name(String file_Name) {
        this.file_Name = file_Name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [video_encrypt = "+video_encrypt+", id = "+id+", video_name = "+video_name+", calculator_name = "+calculator_name+", video_image = "+video_image+", video_language = "+video_language+", video_title = "+video_title+", video_encrypt_type = "+video_encrypt_type+", module_name = "+module_name+", file_Name = "+file_Name+"]";
    }
}


