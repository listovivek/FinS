package com.myappilication.xpress.finjan2017.models.login.couponbasedcourses;

/**
 * Created by suresh on 3/6/17.
 */
public class CouponBSReq {

    private String email;
    private String coupon_code;

    public CouponBSReq(String e, String c){
        this.email = e;
        this.coupon_code = c;
    }


}
