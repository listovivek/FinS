package com.myappilication.xpress.finjan2017.models.login.VideoList;

/**
 * Created by balasri on 15/5/17.
 */
    public class VideoListModules {
    private String video_encrypt;

    private String video_name;

    private String video_image;

    private String video_encrypt_type;

    private String course_calculator;

    private String module_id;

    private String module_name;

    private String course_module;

    private String file_Name;


    public String getVideo_encrypt ()
    {
        return video_encrypt;
    }

    public void setVideo_encrypt (String video_encrypt)
    {
        this.video_encrypt = video_encrypt;
    }

    public String getVideo_name ()
    {
        return video_name;
    }

    public void setVideo_name (String video_name)
    {
        this.video_name = video_name;
    }

    public String getModule_name ()
    {
        return module_name;
    }

    public void setModule_name (String module_name)
    {
        this.module_name = module_name;
    }


    public String getVideo_image ()
    {
        return video_image;
    }

    public void setVideo_image (String video_image)
    {
        this.video_image = video_image;
    }
    public String getCourse_module ()
    {
        return course_module;
    }

    public void setCourse_module (String course_module)
    {
        this.course_module = course_module;
    }


    public String getVideo_encrypt_type ()
    {
        return video_encrypt_type;
    }

    public void setVideo_encrypt_type (String video_encrypt_type)
    {
        this.video_encrypt_type = video_encrypt_type;
    }

    public String getCourse_calculator ()
    {
        return course_calculator;
    }

    public void setCourse_calculator (String course_calculator)
    {
        this.course_calculator = course_calculator;
    }

    public String getModule_id ()
    {
        return module_id;
    }

    public void setModule_id (String module_id)
    {
        this.module_id = module_id;
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
        return "ClassPojo [video_encrypt = "+video_encrypt+", video_name = "+video_name+", video_image = "+video_image+", video_encrypt_type = "+video_encrypt_type+", course_calculator = "+course_calculator+", course_module = "+module_id+",file_Name="+file_Name+"]";
    }
}

