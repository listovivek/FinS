package com.myappilication.xpress.finjan2017.models.login.faqfulllist;

/**
 * Created by sureshmano on 29-06-2017.
 */

public class faqfulllistresp {
    private faqfulllistresult result;

    private String status;

    public faqfulllistresult getResult ()
    {
        return result;
    }

    public void setResult (faqfulllistresult result)
    {
        this.result = result;
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
        return "ClassPojo [result = "+result+", status = "+status+"]";
    }
}
