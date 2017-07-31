package com.myappilication.xpress.finjan2017.models.login.changepassword;

/**
 * Created by suresh on 8/5/17.
 */
public class ChangePasswordreq {


    String email;
    String old_password;
    String new_password;
    String confirm_password;




    public ChangePasswordreq(String e,
                             String old, String newp, String confirm) {
        this.email = e;
        this.old_password = old;
        this.new_password = newp;
        this.confirm_password = confirm;

    }
}
