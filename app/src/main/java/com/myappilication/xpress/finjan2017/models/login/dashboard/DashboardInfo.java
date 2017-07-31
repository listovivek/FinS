package com.myappilication.xpress.finjan2017.models.login.dashboard;

/**
 * Created by balasri on 14/3/17.
 */

public class DashboardInfo {
    private DashboardCourses[] courses;

    public DashboardCourses[] getCourses ()
    {
        return courses;
    }

    public void setCourses (DashboardCourses[] courses)
    {
        this.courses = courses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [courses = "+courses+"]";
    }
}