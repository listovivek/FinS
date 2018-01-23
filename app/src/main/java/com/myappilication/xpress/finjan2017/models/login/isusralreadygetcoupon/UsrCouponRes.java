package com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon;

/**
 * Created by suresh on 3/8/17.
 */
public class UsrCouponRes {

    private UsrCouponInfo info;

    public UsrCouponInfo getInfo ()
    {
        return info;
    }

    public void setInfo (UsrCouponInfo info)
    {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ClassPojo [info = "+info+"]";
    }
}
