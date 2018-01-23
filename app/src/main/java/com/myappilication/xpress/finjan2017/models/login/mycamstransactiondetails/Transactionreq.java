package com.myappilication.xpress.finjan2017.models.login.mycamstransactiondetails;

/**
 * Created by suresh on 6/9/17.
 */
public class Transactionreq {


    String schemes_name;
    String status;
    String message;
    String trxn_no;
    String payment_ref_no;
    String payment_datetime;
    String isin_code;


    public Transactionreq(String scheme,
                          String stus, String msg, String trx, String pay, String date, String isinco) {

        schemes_name = scheme;
        status = stus;
        message = msg;
        trxn_no = trx;
        payment_ref_no = pay;
        payment_datetime = date;
        isin_code = isinco;
    }
}
