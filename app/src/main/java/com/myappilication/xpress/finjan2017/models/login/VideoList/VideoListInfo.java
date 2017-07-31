package com.myappilication.xpress.finjan2017.models.login.VideoList;

import com.myappilication.xpress.finjan2017.models.login.faq.faqdata;
import com.myappilication.xpress.finjan2017.models.login.faq.faqinfo;

/**
 * Created by balasri on 15/5/17.
 */
public class VideoListInfo {
    private VideoListModules[] modules;

    private MCQ[] MCQ;

    public faqdata[] getFAQ() {
        return FAQ;
    }

    public void setFAQ(faqdata[] FAQ) {
        this.FAQ = FAQ;
    }

    private faqdata[] FAQ;



    public VideoListModules[] getModules ()
    {
        return modules;
    }

    public void setModules (VideoListModules[] modules)
    {
        this.modules = modules;
    }

    public MCQ[] getMCQ ()
    {
        return MCQ;
    }

    public void setMCQ (MCQ[] MCQ)
    {
        this.MCQ = MCQ;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [modules = "+modules+", MCQ = "+MCQ+"]";
    }
}



