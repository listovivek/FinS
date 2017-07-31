package com.myappilication.xpress.finjan2017.models.login.profileedit;

/**
 * Created by sureshmano on 3/13/2017.
 */

public class profileinfo {

    private profilecorporates[] corporates;

    private profiledata lists;

    public profilecorporates[] getCorporates ()
    {
        return corporates;
    }

    public void setCorporates (profilecorporates[] corporates)
    {
        this.corporates = corporates;
    }

    public profiledata getLists ()
    {
        return lists;
    }

    public void setLists (profiledata lists)
    {
        this.lists = lists;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [corporates = "+corporates+", lists = "+lists+"]";
    }
}
