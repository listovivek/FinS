package com.myappilication.xpress.finjan2017.models.login.searchfaq;

/**
 * Created by sureshmano on 3/23/2017.
 */

public class searchinfo {


    private searchfaq[] Faq;

    public searchfaq[] getFaq ()
    {
        return Faq;
    }

    public void setFaq (searchfaq[] Faq)
    {
        this.Faq = Faq;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Faq = "+Faq+"]";
    }
}
