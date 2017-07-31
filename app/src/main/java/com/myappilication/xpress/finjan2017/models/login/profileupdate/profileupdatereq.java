package com.myappilication.xpress.finjan2017.models.login.profileupdate;

import java.util.ArrayList;

/**
 * Created by sureshmano on 3/15/2017.
 */

public class profileupdatereq {
    String id;
   // String name;
    String firstname;
    String lastname;
    String email;
    String company_name;
    String exp_date;
    ArrayList <String> modules;



    public profileupdatereq(String id,String firstname,String lastname,String email,
                            String company_name, String exp_date, ArrayList modules)    {
        this.id = id;
        //this.name = name;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.company_name = company_name;
        this.exp_date = exp_date;
        this.modules = modules;



    }

}
