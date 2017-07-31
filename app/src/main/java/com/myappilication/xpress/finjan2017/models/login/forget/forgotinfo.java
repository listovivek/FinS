package com.myappilication.xpress.finjan2017.models.login.forget;

/**
 * Created by sureshmano on 3/29/2017.
 */

public class forgotinfo {

    private String Message;

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Message = "+Message+"]";
    }
}
