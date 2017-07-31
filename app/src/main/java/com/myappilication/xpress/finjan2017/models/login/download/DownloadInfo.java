package com.myappilication.xpress.finjan2017.models.login.download;

/**
 * Created by balasri on 20/3/17.
 */

public class DownloadInfo {
    private DownloadCourses[] courses;

    public DownloadCourses[] getCourses ()
    {
        return courses;
    }

    public void setCourses (DownloadCourses[] courses)
    {
        this.courses = courses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [courses = "+courses+"]";
    }
}


