package com.myappilication.xpress.finjan2017.models.login.faqfulllist;

/**
 * Created by sureshmano on 29-06-2017.
 */

public class faqfulllistinfo {


    private faqfulllistfaq[] Faq;

    public faqfulllistfaq[] getFaq ()
    {
        return Faq;
    }

    public void setFaq (faqfulllistfaq[] Faq)
    {
        this.Faq = Faq;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Faq = "+Faq+"]";
    }
}
