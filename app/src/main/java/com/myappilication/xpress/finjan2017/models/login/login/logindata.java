package com.myappilication.xpress.finjan2017.models.login.login;

/**
 * Created by sureshmano on 3/7/2017.
 */

public class logindata {


    private String id;

    private String company_name;

    private String email;

    private String name;

    private String lastname;

    private String firstname;

    private logincorporates[] corporates;

    public FinishedcourseDetails[] getFinished_course_details() {
        return finished_course_details;
    }

    public void setFinished_course_details(FinishedcourseDetails[] finished_course_details) {
        this.finished_course_details = finished_course_details;
    }

    private FinishedcourseDetails[] finished_course_details;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCompany_name ()
    {
        return company_name;
    }

    public void setCompany_name (String company_name)
    {
        this.company_name = company_name;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public logincorporates[] getCorporates ()
    {
        return corporates;
    }

    public void setCorporates (logincorporates[] corporates)
    {
        this.corporates = corporates;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", company_name = "+company_name+", email = "+email+", name = "+name+", lastname = "+lastname+", firstname = "+firstname+", corporates = "+corporates+"]";
    }
}