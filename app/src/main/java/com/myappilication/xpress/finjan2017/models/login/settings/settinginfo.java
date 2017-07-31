package com.myappilication.xpress.finjan2017.models.login.settings;

/**
 * Created by sureshmano on 3/13/2017.
 */

public class settinginfo {

    private settingsdata lists;

    public settingsdata getLists ()
    {
        return lists;
    }

    public void setLists (settingsdata lists)
    {
        this.lists = lists;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lists = "+lists+"]";
    }
}
