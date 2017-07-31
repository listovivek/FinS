package com.myappilication.xpress.finjan2017.models.login.faq;

/**
 * Created by sureshmano on 3/16/2017.
 */

public class faqresult {
    private faqinfo info;

    public faqinfo getInfo ()
    {
        return info;
    }

    public void setInfo (faqinfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}
