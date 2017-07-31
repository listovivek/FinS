package com.myappilication.xpress.finjan2017.models.login.userreg;

/**
 * Created by suresh on 16/5/17.
 */
public class UserRegResponse {

    private String status;

    private String msg;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", msg = "+msg+"]";
    }


}
