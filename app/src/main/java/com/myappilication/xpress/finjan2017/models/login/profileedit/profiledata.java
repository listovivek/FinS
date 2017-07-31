package com.myappilication.xpress.finjan2017.models.login.profileedit;

/**
 * Created by sureshmano on 3/13/2017.
 */

public class profiledata {

    private String remember_token;

    private String status;

    private String lastname;

    private String coupon_code;

    private String firstname;

    private String password;

    private String id;

    private String updated_at;

    private String email;

    private String company_name;

    private String name;

    private String created_at;

    private String user_id;

    private String[] modules;

    private String exp_date;

    public String getRemember_token ()
    {
        return remember_token;
    }

    public void setRemember_token (String remember_token)
    {
        this.remember_token = remember_token;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getCoupon_code ()
    {
        return coupon_code;
    }

    public void setCoupon_code (String coupon_code)
    {
        this.coupon_code = coupon_code;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getCompany_name ()
    {
        return company_name;
    }

    public void setCompany_name (String company_name)
    {
        this.company_name = company_name;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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

    public String[] getModules ()
    {
        return modules;
    }

    public void setModules (String[] modules)
    {
        this.modules = modules;
    }

    public String getExp_date ()
    {
        return exp_date;
    }

    public void setExp_date (String exp_date)
    {
        this.exp_date = exp_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [remember_token = "+remember_token+", status = "+status+", lastname = "+lastname+", coupon_code = "+coupon_code+", firstname = "+firstname+", password = "+password+", id = "+id+", updated_at = "+updated_at+", email = "+email+", company_name = "+company_name+", name = "+name+", created_at = "+created_at+", user_id = "+user_id+", modules = "+modules+", exp_date = "+exp_date+"]";
    }
}
