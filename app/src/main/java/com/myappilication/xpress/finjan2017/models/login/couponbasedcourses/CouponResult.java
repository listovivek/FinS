package com.myappilication.xpress.finjan2017.models.login.couponbasedcourses;

/**
 * Created by suresh on 1/8/17.
 */
public class CouponResult {

    private String id;

    private String status;

    private String module_id;

    private String coupon_id;

    private String created_at;

    private String user_id;

    private String usage_limit;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getModule_id ()
    {
        return module_id;
    }

    public void setModule_id (String module_id)
    {
        this.module_id = module_id;
    }

    public String getCoupon_id ()
    {
        return coupon_id;
    }

    public void setCoupon_id (String coupon_id)
    {
        this.coupon_id = coupon_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getUsage_limit ()
    {
        return usage_limit;
    }

    public void setUsage_limit (String usage_limit)
    {
        this.usage_limit = usage_limit;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", status = "+status+", module_id = "+module_id+", coupon_id = "+coupon_id+", created_at = "+created_at+", user_id = "+user_id+", usage_limit = "+usage_limit+"]";
    }
}

