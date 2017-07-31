package com.myappilication.xpress.finjan2017.models.login.userreg;

/**
 * Created by suresh on 16/5/17.
 */
public class UserRegReq {

    String user_name, first_name, last_name, email, password, phone_number, coupon_code;

    public UserRegReq(String krish, String kannar, String s, String s1, String s2, String fst101) {
       // user_name = kannan;
        first_name = krish;
        last_name = kannar;

        email = s;
        password =s1;
        phone_number = s2;
        coupon_code = fst101;
    }
}
