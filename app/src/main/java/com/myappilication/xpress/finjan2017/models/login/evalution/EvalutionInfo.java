package com.myappilication.xpress.finjan2017.models.login.evalution;

/**
 * Created by balasri on 17/3/17.
 */

public class EvalutionInfo {
    private EvalutionModularQues[] modularQus;

    public EvalutionModularQues[] getModularQus ()
    {
        return modularQus;
    }

    public void setModularQus (EvalutionModularQues[] modularQus)
    {
        this.modularQus = modularQus;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [modularQus = "+modularQus+"]";
    }
}

