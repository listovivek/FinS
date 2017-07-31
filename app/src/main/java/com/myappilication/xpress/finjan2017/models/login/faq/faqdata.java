package com.myappilication.xpress.finjan2017.models.login.faq;

/**
 * Created by sureshmano on 3/16/2017.
 */

public class faqdata {

    private String faq_ans;

    private String faq_qus;

    public String getFaq_ans ()
    {
        return faq_ans;
    }

    public void setFaq_ans (String faq_ans)
    {
        this.faq_ans = faq_ans;
    }

    public String getFaq_qus ()
    {
        return faq_qus;
    }

    public void setFaq_qus (String faq_qus)
    {
        this.faq_qus = faq_qus;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [faq_ans = "+faq_ans+", faq_qus = "+faq_qus+"]";
    }
}
