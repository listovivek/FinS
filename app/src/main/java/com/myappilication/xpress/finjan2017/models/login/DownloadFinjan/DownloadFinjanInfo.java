package com.myappilication.xpress.finjan2017.models.login.DownloadFinjan;

/**
 * Created by balasri on 20/4/17.
 */
public class DownloadFinjanInfo {
    private DownloadFinjanCourse[] courses;

    public DownloadFinjanCourse[] getCourses ()
    {
        return courses;
    }

    public void setCourses (DownloadFinjanCourse[] courses)
    {
        this.courses = courses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [courses = "+courses+"]";
    }
}
