package com.myappilication.xpress.finjan2017.models.login.faqfulllist;

/**
 * Created by sureshmano on 29-06-2017.
 */

public class faqfulllistresult {
    private faqfulllistinfo info;

    public faqfulllistinfo getInfo ()
    {
        return info;
    }

    public void setInfo (faqfulllistinfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}
