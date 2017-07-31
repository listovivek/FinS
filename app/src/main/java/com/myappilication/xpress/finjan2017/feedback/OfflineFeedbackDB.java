package com.myappilication.xpress.finjan2017.feedback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListModules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suresh on 6/6/17.
 */
public class OfflineFeedbackDB extends SQLiteOpenHelper {

    private static final String DATABASENAME = "feedbackofflinedb";

    private static final int DATABASE_VERSION =1;

    private static final String FEEDBACKTABLE = "feedback";

    private static final String ID = "id";
    private static final String USER_EMAIL_ID = "useremailid";
    private static final String DATETIME = "dateandtime";
    private static final String QUESTION = "question";
    private static final String CORRECT_ANS = "correctans";
    private static final String USERNAME = "username";
    private static final String PHONENUMBR = "phonenumber";
    private static final String EMAIL_ID = "emailid";

    private Context con;
    private SQLiteDatabase sqLiteDatabase;

    public static ArrayList<String> dateList = new ArrayList<>();
    public static ArrayList<String> nameLi = new ArrayList<>();

    private static final String feedbackDetails = "CREATE TABLE IF NOT EXISTS " + FEEDBACKTABLE + " ( "
            + ID + " INTEGER ," + USERNAME +" TEXT,"+ EMAIL_ID + " TEXT,"+ USER_EMAIL_ID + " TEXT,"+ PHONENUMBR + " TEXT,"
            + QUESTION + " TEXT,"+ CORRECT_ANS + " TEXT,"+ DATETIME + " TEXT)";


    public OfflineFeedbackDB(Context feedActivity) {
        super(feedActivity, DATABASENAME, null, DATABASE_VERSION);
        con = feedActivity;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(feedbackDetails);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FEEDBACKTABLE);
        onCreate(db);
    }


    public void setfeedbackValue(String name, String email, String number, ArrayList<String> ans,
                                 ArrayList<String> ques, String date, String em) {
        sqLiteDatabase = this.getWritableDatabase();
        //boolean condition = checkEmailID(email);

       // if(condition==false){


            for(int i=0; i<ques.size(); i++){
                ContentValues values = new ContentValues();
                values.put(EMAIL_ID, em);
                values.put(USERNAME, name);
                values.put(USER_EMAIL_ID, email);
                values.put(PHONENUMBR, number);
                values.put(DATETIME, date);
                values.put(CORRECT_ANS, ans.get(i));
                values.put(QUESTION, ques.get(i));
                sqLiteDatabase.insert(FEEDBACKTABLE, null, values);
            }


       // }
    }

    private boolean checkEmailID(String email) {
        String selectQuery = "SELECT  * FROM " + FEEDBACKTABLE + " WHERE "
                + USER_EMAIL_ID + " = " + email;
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                Log.d("add", "value");
                return true;
            } while (cursor.moveToNext());
        }

        return false;
    }

    public ArrayList<String> getdata(String em) {

        sqLiteDatabase = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + FEEDBACKTABLE + " WHERE "
                + EMAIL_ID + " = " + em;

        try{

            Cursor cursor = sqLiteDatabase.rawQuery(
                    "select * from "+ FEEDBACKTABLE +" where emailid = ? ", new String[]{em});


            ArrayList<String> nameList = new ArrayList<>();
            dateList.clear();
            nameLi.clear();

            if (cursor.moveToFirst()) {
                do {
                  //  String id = cursor.getString(0);
                    String useremail = cursor.getString(1);
                    String date = cursor.getString(7);
                    nameList.add(useremail);
                    dateList.add(date);
                    nameLi.add(useremail);
                   /* String datatime = cursor.getString(2);
                    String ques = cursor.getString(3);
                    String corrans = cursor.getString(4);
                    String username = cursor.getString(5);
                    String num = cursor.getString(6);
                    String email = cursor.getString(7);*/
                    Log.d("user date", date);
                    Log.d("user name", useremail);

                } while (cursor.moveToNext());
            }
            return nameList;
        }catch (Exception e){
            Log.d("user selected ans", "");
        }


    return null;
    }

    public List<FeedbackUserListItem> getparticularData(String s) {
        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from "+ FEEDBACKTABLE +" where username = ? ", new String[]{s});

        ArrayList<String> quesList = new ArrayList<>();
        ArrayList<String> crtAns = new ArrayList<>();

        String username=null, email=null, useremail=null, number=null, datetime=null;

        FeedbackUserListItem tm = new FeedbackUserListItem();

        List<FeedbackUserListItem> tmm = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                //  String id = cursor.getString(0);
                username = cursor.getString(1);
                    email = cursor.getString(2);
                    useremail = cursor.getString(3);


                    number = cursor.getString(4);
                    String ques = cursor.getString(5);
                quesList.add(ques);
                    String crtan = cursor.getString(6);
                crtAns.add(crtan);
                    datetime = cursor.getString(7);
                Log.d("user selected ans", "");


            } while (cursor.moveToNext());
        }

        tm.setUsername(username);
        tm.setEmail(email);
        tm.setUseremail(useremail);
        tm.setPhonenumber(number);
        tm.setDatetime(datetime);
        tm.setCorrectAns(crtAns);
        tm.setQuestion(quesList);
        tmm.add(tm);

        return tmm;
    }
}
