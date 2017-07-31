package com.myappilication.xpress.finjan2017.models.login.faq;

/**
 * Created by sureshmano on 3/16/2017.
 */

public class faqinfo {
    private faqdata[] Faq;

    public faqdata[] getFaq ()
    {
        return Faq;
    }

    public void setFaq (faqdata[] Faq)
    {
        this.Faq = Faq;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Faq = "+Faq+"]";
    }
}
