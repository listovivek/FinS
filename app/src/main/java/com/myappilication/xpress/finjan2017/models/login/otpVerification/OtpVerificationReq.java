package com.myappilication.xpress.finjan2017.models.login.otpVerification;

/**
 * Created by suresh on 16/5/17.
 */
public class OtpVerificationReq {

    String email, otp;

    public OtpVerificationReq(String e, String o) {
        email = e;
        otp = o;
    }
}
