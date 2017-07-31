package com.myappilication.xpress.finjan2017.models.login.helpers;

import java.util.ArrayList;

/**
 * Created by sureshmano on 3/7/2017.
 */

public class StaticConfig {

   // public static String ROOT_URL = "http://183.82.33.232:8094/api";  // local server
    //  public static String ROOT_URL = "http://103.235.104.118:3001";   // Beta server
  // public static String ROOT_URL = "http://app.finsafe.in/api";   //live server
    //192.168.1.53:3001
   public static String ROOT_URL = "http://13.126.83.144/api";

    public static String Base="http://183.82.33.232:8094/";
   // public static String Base="http://app.finsafe.in/";
    public static boolean IsPublicActivity = true;
    public static String faqModule = "";
    public  static String faqmodule_id = "";
    public static String download="downloads/";

    public static ArrayList<String>calc_name = new ArrayList<>();
    public static int posOfcalc ;

    //public static String remember_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cLzE4My44Mi4zMy4yMzI6ODA5NFwvYXBpXC9sb2dpbiIsImlhdCI6MTQ5MDI3NzE1MywiZXhwIjoxNDkwMjgwNzUzLCJuYmYiOjE0OTAyNzcxNTMsImp0aSI6ImRhYWU0N2Y2MWI0ZDQ2NjEzMmE2NjY2NGNkZTU5MDRhIn0.pPlFR9xZkrb4_ecgrQSugzJVubtemzgiEciarU1NxRg";

}
