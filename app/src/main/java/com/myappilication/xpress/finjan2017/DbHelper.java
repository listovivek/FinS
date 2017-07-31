package com.myappilication.xpress.finjan2017;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionModularQues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by balasri on 22/3/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "triviaQuiz";
    // tasks table name
    private static final String TABLE_QUEST = "quest";

    private static final String TABLE_USERSELECTED_ANS = "useranswer";

    private static final String TABLE_CALCUATOR = "tablecalc";

    private static final String TABLE_FAQ = "tablefaq";


    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA= "opta"; //option a
    private static final String KEY_OPTB= "optb"; //option b
    private static final String KEY_OPTC= "optc";//option c
    private static final String KEY_OPTD= "optd";
    private static final String  MOUDLE_ID = "moduleid";


    //table of calc
    private static final String  CALCULATOR = "calculator";
    private static final String  CALC_MODULEID = "calcmoduleid";

    private static final String  FAQ = "faq";
    private static final String  FAQ_MODULEID = "faqmoduleid";

    private static final String USER_SELECTED_ANSWER = "userselectedanswer";
    private static final String USER_SELECTED_MCQID = "userselectedmcqid";
    private static final String MODULE_ID1 = "moduleid1";

    private Context con;


   private static final String table_of_mcq = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
            + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, "
            +KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT, "+KEY_OPTD+" TEXT, "+MOUDLE_ID+" TEXT)";

    private static final String table_user_selectedans = "CREATE TABLE IF NOT EXISTS " + TABLE_USERSELECTED_ANS + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MODULE_ID1
            + " TEXT, " + USER_SELECTED_ANSWER+ " TEXT, "+USER_SELECTED_MCQID +" TEXT)";

    private static final String table_of_calc = "CREATE TABLE IF NOT EXISTS " + TABLE_CALCUATOR + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CALC_MODULEID
            + " TEXT, " + CALCULATOR+ " TEXT)";

    private static final String table_of_faq = "CREATE TABLE IF NOT EXISTS " + TABLE_FAQ + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FAQ
            + " TEXT, " + FAQ_MODULEID+ " TEXT)";

    private SQLiteDatabase dbase;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(table_of_mcq);
        db.execSQL(table_user_selectedans);
        db.execSQL(table_of_calc);
        db.execSQL(table_of_faq);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERSELECTED_ANS);
// Create tables again
        onCreate(db);
    }

    public void onDelete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
// Create tables again
        onCreate(db);
    }

    public void setUserSelectedAnswer(String listOfmoduleid, ArrayList<String> selectedData,
                                      ArrayList<String> mcqid) {
        dbase=this.getWritableDatabase();

        boolean condition = searchUserMcqAns(listOfmoduleid, selectedData.size());

        if(condition == false){
            for(int i=0; i<selectedData.size(); i++){
                ContentValues values = new ContentValues();
                values.put(MODULE_ID1, listOfmoduleid);
                values.put(USER_SELECTED_ANSWER, selectedData.get(i));
                values.put(USER_SELECTED_MCQID, mcqid.get(i));
                dbase.insert(TABLE_USERSELECTED_ANS, null, values);
            }
        }else{
            for(int i=0; i<selectedData.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(MODULE_ID1, listOfmoduleid);
                values.put(USER_SELECTED_ANSWER, selectedData.get(i));
                values.put(USER_SELECTED_MCQID, mcqid.get(i));
                dbase.insert(TABLE_USERSELECTED_ANS, null, values);
            }
        }

    }

    private boolean searchUserMcqAns(String listOfmoduleid, int size) {
        dbase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USERSELECTED_ANS + " WHERE "
                + MODULE_ID1 + " = " + listOfmoduleid;
        Log.e("", selectQuery);

        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
               for(int i =0; i<size; i++){
                   dbase.delete(TABLE_USERSELECTED_ANS, MODULE_ID1 + " = ?",
                           new String[] { String.valueOf(listOfmoduleid) });
               }

                Log.d("add", "value");
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

    public String getCalcValue(String module_id) {
        String selectQuery = "SELECT  * FROM " + TABLE_CALCUATOR + " WHERE "
                + CALC_MODULEID + " = " + module_id;

        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                String modID = cursor.getString(1);
                String calcValue = cursor.getString(2);
                //Log.d("user selected ans", calcValue.toString());
                return calcValue;
            } while (cursor.moveToNext());
        }

        dbase.close();

        return null;
    }


    public void addCalcValue(String calc, String moduleid) {

        dbase=this.getWritableDatabase();
        boolean condition = checkCalcAreadlyadded(moduleid);

        if(condition == false){

                ContentValues values = new ContentValues();
                values.put(CALC_MODULEID, moduleid);
                values.put(CALCULATOR, calc);

                dbase.insert(TABLE_CALCUATOR, null, values);

        }else{
           // Toast.makeText(con, "mcq Data already insert in database", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkCalcAreadlyadded(String moduleid) {
        dbase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CALCUATOR + " WHERE "
                + CALC_MODULEID + " = " + moduleid;
        Log.e("", selectQuery);

        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

    public void addFaqValue(String moduleid, String s) {
        dbase=this.getWritableDatabase();
        boolean condition = checkFaqAreadlyadded(moduleid);

        if(condition == false){

            ContentValues values = new ContentValues();
            values.put(FAQ_MODULEID, moduleid);
            values.put(FAQ, s);

            dbase.insert(TABLE_FAQ, null, values);

        }else{
          //  Toast.makeText(con, "mcq Data already insert in database", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkFaqAreadlyadded(String moduleid) {

        dbase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FAQ + " WHERE "
                + FAQ_MODULEID + " = " + moduleid;
        Log.e("", selectQuery);

        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

    public String getFaqValue(String module_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_FAQ + " WHERE "
                + FAQ_MODULEID + " = " + module_id;

        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});
        ArrayList<String> user_Sel_list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String modID = cursor.getString(2);
               String faq = cursor.getString(1);
                Log.d("user selected ans", faq.toString());
                return faq;
            } while (cursor.moveToNext());
        }

        dbase.close();
        return null;
    }



    // Adding new question
    public void addQuestion(ArrayList<String> ques, ArrayList<String> ans1,
                            ArrayList<String> ans2, ArrayList<String> ans3, ArrayList<String> ans4,
                            ArrayList<String> correct_ans, String moduleid) {

        dbase=this.getWritableDatabase();

        boolean condition = getMCQvalues(moduleid);

        if(condition == false){
            for (int t=0; t<ques.size(); t++){
                ContentValues values = new ContentValues();
                values.put(MOUDLE_ID, moduleid);
                values.put(KEY_QUES, ques.get(t));
                values.put(KEY_ANSWER, correct_ans.get(t));
                values.put(KEY_OPTA, ans1.get(t));
                values.put(KEY_OPTB, ans2.get(t));
                values.put(KEY_OPTC, ans3.get(t));
                values.put(KEY_OPTD, ans4.get(t));
                dbase.insert(TABLE_QUEST, null, values);
            }
        }else{
          //  Toast.makeText(con, "mcq Data already insert in database", Toast.LENGTH_LONG).show();
        }
    }

    private boolean getMCQvalues(String id) {

        dbase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_QUEST + " WHERE "
                + MOUDLE_ID + " = " + id;
        Log.e("", selectQuery);

        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;

    }

    public ArrayList<String> getUserSelectedans(String list_of_mod_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_USERSELECTED_ANS + " WHERE "
                + MODULE_ID1 + " = " + list_of_mod_id;

        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});
        ArrayList<String> user_Sel_list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String modID = cursor.getString(1);
                user_Sel_list.add(cursor.getString(2));
                Log.d("user selected ans", user_Sel_list.toString());

            } while (cursor.moveToNext());
        }

        dbase.close();
        return user_Sel_list;
    }



    public List<EvalutionModularQues> getAllQuestions(String id) {
        List<EvalutionModularQues> quesList = new ArrayList<>();
        // Select All Query
        //String selectQuery ="SELECT  * FROM " + TABLE_QUEST + " ORDER BY RANDOM()";
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST + " WHERE "
                + MOUDLE_ID + " = " + id;
        int i =0;

        try{
            dbase=this.getReadableDatabase();
            Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});
            //KEY_ID,KEY_QUES,KEY_ANSWER,KEY_OPTA,KEY_OPTB,KEY_OPTC,KEY_OPTD
            EvalutionModularQues list;
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    list  = new EvalutionModularQues();
                    list.setMcq_id(cursor.getString(0));
                    list.setMcq_qus(cursor.getString(1));
                    list.setCorrect_ans(cursor.getString(2));
                    list.setMcq_ans1(cursor.getString(3));
                    list.setMcq_ans2(cursor.getString(4));
                    list.setMcq_ans3(cursor.getString(5));
                    list.setMcq_ans4(cursor.getString(6));
                    quesList.add(list);
                    Log.d("database random", quesList.toString());

                } while (cursor.moveToNext());
            }

            dbase.close();
        }catch (Exception e){
            return null;
            //Log.d("db error", e.toString());
        }


        return quesList;

    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST + " RANDOM() ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }



}

