package com.myappilication.xpress.finjan2017.models.login.CourseList;

/**
 * Created by balasri on 15/5/17.
 */
public class CourseListResult {

        private CourseListInfo info;

        public CourseListInfo getInfo ()
        {
            return info;
        }

        public void setInfo (CourseListInfo info)
        {
            this.info = info;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [info = "+info+"]";
        }
    }


