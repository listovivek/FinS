package com.myappilication.xpress.finjan2017.models.login.searchfaq;

/**
 * Created by sureshmano on 3/23/2017.
 */

public class searchresult {
    private searchinfo info;

    public searchinfo getInfo ()
    {
        return info;
    }

    public void setInfo (searchinfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}
