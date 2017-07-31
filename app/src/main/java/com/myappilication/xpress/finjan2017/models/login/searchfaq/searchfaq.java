package com.myappilication.xpress.finjan2017.models.login.searchfaq;

/**
 * Created by sureshmano on 5/23/2017.
 */

public class searchfaq {

    private String faq_id;

    private String faq_ans;

    private String faq_qus;

    private String Categories;

    public String getFaq_id ()
    {
        return faq_id;
    }

    public void setFaq_id (String faq_id)
    {
        this.faq_id = faq_id;
    }

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

    public String getCategories ()
    {
        return Categories;
    }

    public void setCategories (String Categories)
    {
        this.Categories = Categories;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [faq_id = "+faq_id+", faq_ans = "+faq_ans+", faq_qus = "+faq_qus+", Categories = "+Categories+"]";
    }
}
