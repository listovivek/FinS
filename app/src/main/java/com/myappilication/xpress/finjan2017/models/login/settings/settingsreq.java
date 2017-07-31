package com.myappilication.xpress.finjan2017.models.login.settings;

/**
 * Created by sureshmano on 3/9/2017.
 */

public class settingsreq {
    String remember_token, status, lastname,firstname,password,id,updated_at,email,company_name,name,created_at,user_id,modules,exp_date  ;
    public settingsreq(String remember_token, String status, String lastname, String firstname, String password, String id, String updated_at, String email, String company_name, String name, String created_at, String user_id, String modules, String exp_date){


        this.remember_token = remember_token;
        this.status = status;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.id = id;
        this.updated_at = updated_at;
        this.email = email;
        this.company_name = company_name;
        this.name = name;
        this.created_at = created_at;
        this.user_id = user_id;
        this.modules = modules;
        this.exp_date = exp_date;



    }
}
