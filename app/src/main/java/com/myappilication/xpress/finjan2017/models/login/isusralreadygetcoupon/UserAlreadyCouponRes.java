package com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon;

/**
 * Created by suresh on 3/8/17.
 */
public class UserAlreadyCouponRes {

    private UsrCouponRes result;

    private String status;

    public UsrCouponRes getResult ()
    {
        return result;
    }

    public void setResult (UsrCouponRes result)
    {
        this.result = result;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [result = "+result+", status = "+status+"]";
    }
}
