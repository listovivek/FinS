package com.myappilication.xpress.finjan2017.models.login.login;

/**
 * Created by sureshmano on 5/22/2017.
 */

public class logincorporates {

    private String id;

    private String company_name;

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

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", company_name = "+company_name+"]";
    }

}
