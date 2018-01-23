package com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon;

/**
 * Created by suresh on 28/9/17.
 */
public class UsrPendingCourse {

    private String id;

    private String module_amount;

    private String expiry_date;

    private String module_name;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getModule_amount ()
    {
        return module_amount;
    }

    public void setModule_amount (String module_amount)
    {
        this.module_amount = module_amount;
    }

    public String getExpiry_date ()
    {
        return expiry_date;
    }

    public void setExpiry_date (String expiry_date)
    {
        this.expiry_date = expiry_date;
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
        return "ClassPojo [id = "+id+", module_amount = "+module_amount+", expiry_date = "+expiry_date+", module_name = "+module_name+"]";
    }

}
