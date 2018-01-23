package com.myappilication.xpress.finjan2017.models.login.helpers;

import java.util.ArrayList;

/**
 * Created by sureshmano on 3/7/2017.
 */

public class StaticConfig {


      //public static String ROOT_URL = "http://103.235.104.118:3001";   // Beta server

  /* public static String ROOT_URL = "http://app.finsafe.in/api";
    public static String Base="http://app.finsafe.in/";
   public static String html_Base="http://app.finsafe.in/";*/
    //192.168.1.53:3001

    //live server

   /*public static String ROOT_URL ="http://13.126.83.144/api";
    public static String Base="http://13.126.83.144/";
   public static String html_Base="http://13.126.83.144/";*/

    public static String ROOT_URL ="http://13.127.10.207/api";
    public static String Base="http://13.127.10.207/";
    public static String html_Base="http://13.127.10.207/";


    // local server

    /*public static String ROOT_URL = "http://183.82.33.232:8094/api";
    public static String Base="http://183.82.33.232:8094/";*/


   // public static String Base="http://app.finsafe.in/";
    public static boolean IsPublicActivity = true;
    public static String faqModule = "";
    public  static String faqmodule_id = "";
    public static String download="downloads/";

    public static ArrayList<String>calc_name = new ArrayList<>();
    public static int posOfcalc ;

    //MYCAMS
    //https://eiscuat1.camsonline.com/camsapp/myCAMSEKYCCheck.aspx
    public static final String JSON_URL = "https://eiscweb.camsonline.com/CAMSApp/mycamsekyccheck.aspx";

    //CCAVENUE


    public static final String PARAMETER_SEP = "&";
    public static final String PARAMETER_EQUALS = "=";
    public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans";

    public static final String MERCHANT_ID = "87729";
    public static final String ACCESS_CODE = "AVIF73EI08BB48FIBB";
    public static final String REDIRECT_URL = "https://finsafe.in/merchant/ccavResponseHandler.php";
    public static final String CANCEL_URL = "https://finsafe.in/merchant/ccavResponseHandler.php";
    public static final String RSA_KEY_URL = "https://finsafe.in/merchant/GetRSA.php";


    //public static String remember_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cLzE4My44Mi4zMy4yMzI6ODA5NFwvYXBpXC9sb2dpbiIsImlhdCI6MTQ5MDI3NzE1MywiZXhwIjoxNDkwMjgwNzUzLCJuYmYiOjE0OTAyNzcxNTMsImp0aSI6ImRhYWU0N2Y2MWI0ZDQ2NjEzMmE2NjY2NGNkZTU5MDRhIn0.pPlFR9xZkrb4_ecgrQSugzJVubtemzgiEciarU1NxRg";

}
