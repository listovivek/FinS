package com.myappilication.xpress.finjan2017.models.login.CourseList;

/**
 * Created by balasri on 15/5/17.
 */
public class CourseListResponse {

        private CourseListResult result;

        private String status;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

        public CourseListResult getResult ()
        {
            return result;
        }

        public void setResult (CourseListResult result)
        {
            this.result = result;
        }

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [result = "+result+", status = "+status+"]";
        }
    }



