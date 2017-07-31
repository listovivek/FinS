package com.myappilication.xpress.finjan2017.models.login.VideoList;

/**
 * Created by balasri on 15/5/17.
 */
public class MCQ {
    private String mcq_qus;

    private String mcq_ans4;

    private String mcq_id;

    private String mcq_ans3;

    private String mcq_ans2;

    private String modular;

    private String mcq_ans1;

    private String course_module_id;

    private String correct_ans;

    public String getMcq_qus ()
    {
        return mcq_qus;
    }

    public void setMcq_qus (String mcq_qus)
    {
        this.mcq_qus = mcq_qus;
    }

    public String getMcq_ans4 ()
    {
        return mcq_ans4;
    }

    public void setMcq_ans4 (String mcq_ans4)
    {
        this.mcq_ans4 = mcq_ans4;
    }

    public String getMcq_id ()
    {
        return mcq_id;
    }

    public void setMcq_id (String mcq_id)
    {
        this.mcq_id = mcq_id;
    }

    public String getMcq_ans3 ()
    {
        return mcq_ans3;
    }

    public void setMcq_ans3 (String mcq_ans3)
    {
        this.mcq_ans3 = mcq_ans3;
    }

    public String getMcq_ans2 ()
    {
        return mcq_ans2;
    }

    public void setMcq_ans2 (String mcq_ans2)
    {
        this.mcq_ans2 = mcq_ans2;
    }

    public String getModular ()
    {
        return modular;
    }

    public void setModular (String modular)
    {
        this.modular = modular;
    }

    public String getMcq_ans1 ()
    {
        return mcq_ans1;
    }

    public void setMcq_ans1 (String mcq_ans1)
    {
        this.mcq_ans1 = mcq_ans1;
    }

    public String getCourse_module_id ()
    {
        return course_module_id;
    }

    public void setCourse_module_id (String course_module_id)
    {
        this.course_module_id = course_module_id;
    }

    public String getCorrect_ans ()
    {
        return correct_ans;
    }

    public void setCorrect_ans (String correct_ans)
    {
        this.correct_ans = correct_ans;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [mcq_qus = "+mcq_qus+", mcq_ans4 = "+mcq_ans4+", mcq_id = "+mcq_id+", mcq_ans3 = "+mcq_ans3+", mcq_ans2 = "+mcq_ans2+", modular = "+modular+", mcq_ans1 = "+mcq_ans1+", course_module_id = "+course_module_id+", correct_ans = "+correct_ans+"]";
    }
}


