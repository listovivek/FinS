package com.myappilication.xpress.finjan2017.models.login.evalution;

/**
 * Created by balasri on 17/3/17.
 */

public class EvalutionResponse {


    private EvalutionResult result;

    private String status;

    public EvalutionResult getResult ()
    {
        return result;
    }

    public void setResult (EvalutionResult result)
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

