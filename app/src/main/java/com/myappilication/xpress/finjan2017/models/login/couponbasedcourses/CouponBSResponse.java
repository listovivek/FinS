package com.myappilication.xpress.finjan2017.models.login.couponbasedcourses;

/**
 * Created by suresh on 3/6/17.
 */
public class CouponBSResponse {


    private String status;
    private String msg;
    private String module_id;
    private String coupon_image;

    public String getCoupon_image() {
        return coupon_image;
    }

    public void setCoupon_image(String coupon_image) {
        this.coupon_image = coupon_image;
    }

    //private CouponResult[] result;

    public String getModule_id ()
    {
        return module_id;
    }

    public void setModule_id (String module_id)
    {
        this.module_id = module_id;
    }

    /*public CouponResult[] getResult ()
    {
        return result;
    }

    public void setResult (CouponResult[] result)
    {
        this.result = result;
    }*/

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }


}
