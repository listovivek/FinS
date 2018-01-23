package com.myappilication.xpress.finjan2017.models.login.completemodpushtoserver;

/**
 * Created by suresh on 4/8/17.
 */
public class Completemodreq {

    String email;
    String module_id;
    String course_id;

    public Completemodreq(String e, String c, String list_of_moduleID) {
        email = e;
        module_id = c;
        course_id = list_of_moduleID;
    }
}
