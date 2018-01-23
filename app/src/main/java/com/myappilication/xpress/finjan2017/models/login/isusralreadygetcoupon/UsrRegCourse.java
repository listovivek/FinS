package com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon;

/**
 * Created by suresh on 28/9/17.
 */
public class UsrRegCourse {

    private String module_id;

    private String expiry_date;

    public String getModule_id ()
    {
        return module_id;
    }

    public void setModule_id (String module_id)
    {
        this.module_id = module_id;
    }

    public String getExpiry_date ()
    {
        return expiry_date;
    }

    public void setExpiry_date (String expiry_date)
    {
        this.expiry_date = expiry_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [module_id = "+module_id+", expiry_date = "+expiry_date+"]";
    }
}
