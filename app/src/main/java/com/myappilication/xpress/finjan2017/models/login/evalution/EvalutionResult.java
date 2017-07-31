package com.myappilication.xpress.finjan2017.models.login.evalution;

/**
 * Created by balasri on 17/3/17.
 */

public class EvalutionResult {
    private EvalutionInfo info;

    public EvalutionInfo getInfo ()
    {
        return info;
    }

    public void setInfo (EvalutionInfo info)
    {
        this.info = info;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [info = "+info+"]";
    }
}

