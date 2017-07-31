package com.myappilication.xpress.finjan2017.models.login.CourseList;

/**
 * Created by balasri on 15/5/17.
 */
public class CourseListCourses {
    private String module_id;

    private String module_name;

    public String getModule_id ()
    {
        return module_id;
    }

    public void setModule_id (String module_id)
    {
        this.module_id = module_id;
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
        return "ClassPojo [module_id = "+module_id+", module_name = "+module_name+"]";
    }
}


