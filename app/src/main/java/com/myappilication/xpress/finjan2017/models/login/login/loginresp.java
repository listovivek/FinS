package com.myappilication.xpress.finjan2017.models.login.login;

import com.myappilication.xpress.finjan2017.models.login.login.logindata;

/**
 * Created by sureshmano on 3/7/2017.
 */

public class loginresp {

    private logindata details;

    private String token;

    private String status;

   private String msg;

    public String getError() {
        return msg;
    }

    public void setError(String error) {
        this.msg = error;
    }

    public logindata getDetails ()
    {
        return details;
    }

    public void setDetails (logindata details)
    {
        this.details = details;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
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
        return "ClassPojo [details = "+details+", token = "+token+", status = "+status+"]";
    }
}
