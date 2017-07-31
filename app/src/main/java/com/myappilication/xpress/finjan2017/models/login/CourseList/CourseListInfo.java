package com.myappilication.xpress.finjan2017.models.login.CourseList;

/**
 * Created by balasri on 15/5/17.
 */
public class CourseListInfo {

        private CourseListCourses[] courses;

        public CourseListCourses[] getCourses ()
        {
            return courses;
        }

        public void setCourses (CourseListCourses[] courses)
        {
            this.courses = courses;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [courses = "+courses+"]";
        }
    }

