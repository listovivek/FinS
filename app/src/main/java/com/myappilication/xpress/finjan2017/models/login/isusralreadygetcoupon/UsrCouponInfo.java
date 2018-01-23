package com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon;

/**
 * Created by suresh on 3/8/17.
 */
public class UsrCouponInfo {


    private UsrRegCourse[] registered;

    private UsrPendingCourse[] pendingCourses;

    public UsrRegCourse[] getRegistered ()
    {
        return registered;
    }

    public void setRegistered (UsrRegCourse[] registered)
    {
        this.registered = registered;
    }

    public UsrPendingCourse[] getPendingCourses ()
    {
        return pendingCourses;
    }

    public void setPendingCourses (UsrPendingCourse[] pendingCourses)
    {
        this.pendingCourses = pendingCourses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [registered = "+registered+", pendingCourses = "+pendingCourses+"]";
    }
}
