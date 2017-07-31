package com.myappilication.xpress.finjan2017.models.login.faqfulllist;

/**
 * Created by sureshmano on 3/20/2017.
 */

public class FaqFulllistdatas {

    private int Id;
    private String faq_qus;
    private String faq_ans;
public FaqFulllistdatas()
{

}
    public FaqFulllistdatas(String faq_qus, String faq_ans){

        this.Id=0;
        this.faq_qus = faq_qus;
        this.faq_ans = faq_ans;

    }
public int getFaq_Id(){return  Id;}

    public void setId(int id) {
        Id = id;
    }

    public String getFaq_qus(){return faq_qus;}

    public void setFaq_ans(String faq_ans) {
        this.faq_ans = faq_ans;
    }

    public String getFaq_ans() {
        return faq_ans;
    }

    public void setFaq_qus(String faq_qus) {
        this.faq_qus = faq_qus;
    }
}
