package com.myappilication.xpress.finjan2017.models.login.otpVerification;

/**
 * Created by suresh on 16/5/17.
 */
public class OtpResendReq {

    String email, phone_number;
    public OtpResendReq(String s, String s1) {
            email = s;
        phone_number = s1;
    }
}
