package com.myappilication.xpress.finjan2017.models.login.faq;

/**
 * Created by sureshmano on 3/16/2017.
 */

public class faqresp {
    private faqresult result;

    private String status;

    private String error;

    public faqresult getResult ()
    {
        return result;
    }

    public void setResult (faqresult result)
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+", status = "+status+", error = "+error+"]";
    }
}
