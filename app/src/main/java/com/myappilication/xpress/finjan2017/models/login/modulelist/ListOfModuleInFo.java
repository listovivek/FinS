package com.myappilication.xpress.finjan2017.models.login.modulelist;

/**
 * Created by suresh on 15/5/17.
 */
public class ListOfModuleInFo {

    private  LOCourse[] course;
    private LOModules[] modules;


    public LOCourse[] getCourseList() {
        return course;
    }

    public void setCourseList(LOCourse[] courseList) {
        this.course = courseList;
    }

    public LOModules[] getModulesList() {
        return modules;
    }

    public void setModulesList(LOModules[] modulesList) {
        this.modules = modulesList;
    }


}
