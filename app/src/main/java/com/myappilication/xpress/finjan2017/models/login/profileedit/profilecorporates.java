package com.myappilication.xpress.finjan2017.models.login.profileedit;

/**
 * Created by sureshmano on 5/4/2017.
 */

public class profilecorporates {

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
