package com.myappilication.xpress.finjan2017.models.login.offlineDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.myappilication.xpress.finjan2017.ListofModuleFinjan;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListModules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by suresh on 22/5/17.
 */
public class OfflineDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase mSqLiteDatabase;
    private Context con;

    // Database Name
    private static final String DATABASE_NAME = "finjanofflinedatabase";


    //Table name
    private static final String TABLE_COURSES = "finjancourses";
    private static final String TABLE_LISTOF_MODULES = "modulelist";
    private static final String TABLE_OF_DASHBOARD = "dashboard";
    private static final String TABLE_OF_EXPIRY_DATE = "expirydatevalidation";

    private static final String TABLE_OF_VIDEOVALIDATION = "videovalidation";

    private static final String TABLE_MCQ_COMPLETE = "mcqcomplete";
    private static final String TABLE_CALC_COMPLETE = "calccomplete";
    private static final String TABLE_FAQ_COMPLETE = "faqcomplete";

    private static final String TABLE_FINISHED_COURSES= "finishedcourses";

    //complete
    private static final String mcq_module_id = "mcqmoduleid";
    private static final String mcq_boolean_condition = "mcqbooleancondition";
    private static final String mcq_course_id = "mcqcourseid";

    private static final String calc_module_id = "calcmoduleid";
    private static final String calc_boolean_condition = "calcbooleancondition";
    private static final String calc_course_id= "calccourseid";

    private static final String faq_module_id = "faqmoduleid";
    private static final String faq_boolean_condition = "faqbooleancondition";
    private static final String faq_course_id = "faqcourseid";

    private static final String finish_coursesID = "finishcourseid";
    private static final String finish_tempcourseid = "finishtempcourseid";
    private static final String finish_moduleID = "finishmoduleid";

    ////

    //categories
    private static final String courses = "COURSES";
    private static final String courses_id = "COURSES_ID";
    private static final String courses_id_for_exp = "COURSES_ID_FOR_EXP";
    private static final String user_emailid = "USER_EMAILID";
    private static final String courses_position = "COURSES_POSITION";


    private static final String list_of_modules_id = "LISTOFMODULES_ID";
    private static final String list_of_modules_name = "LISTOFMODULES_NAME";
    private static final String courses_id_for_moudles = "TEMP_ID";
    private static final String expiry_day = "EXPIRY_DAY";
    private static final String created_date = "CREATED_DATE";
    private static final String emailid_for_listofmodules = "EMAILID_FOR_LISTOFMOD";


    private static final String module_id="MODULE_ID";
    private static final String module_name="MODULE_NAME";
    private static final String video_nam="VIDEO_NAME";
    private static final String video_title="VIDEO_TITLE";
    private static final String video_encrypt="VIDEO_ENCRYPT";
    private static final String video_encrypt_type="VIDEO_ENCRYPT_TYPE";
    private static final String Calc ="CALC";
    private static final String Filename="FILENAME";
    private static final String Image="IMAGE";

    private static final String Moduler_position="MODULERPOS";

    private static final String video_id="videoid";
    private static final String video_vali_coursename="videovalicoursename";
    private static final String video_vali_courseid="videovalicourseid";
    private static final String video_vali_condition="videovalicondition";



    private static final String fincourses = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSES + " ( "
            + courses_id + " INTEGER , " + courses
            + " TEXT, "+courses_position+" TEXT)";

    private static final String videovalidate = "CREATE TABLE IF NOT EXISTS " + TABLE_OF_VIDEOVALIDATION + " ( "
            + video_id + " INTEGER , " + video_vali_coursename
            + " TEXT, "+video_vali_condition+" TEXT, "+video_vali_courseid+" TEXT)";

    private static final String Listofmodules = "CREATE TABLE IF NOT EXISTS " + TABLE_LISTOF_MODULES + " ( "
            + list_of_modules_id + " INTEGER , " + list_of_modules_name
            + " TEXT, "+courses_id_for_moudles+" TEXT, "+ expiry_day +" TEXT, "+ created_date +" TEXT, "+
            emailid_for_listofmodules +" TEXT)";


    private static final String expirydate = "CREATE TABLE IF NOT EXISTS " + TABLE_OF_EXPIRY_DATE + " ( "
            + courses_id_for_exp + " INTEGER , " + expiry_day +" TEXT, "+
            created_date +" TEXT, " + user_emailid +" TEXT)";


    private static final String dashboard = "CREATE TABLE IF NOT EXISTS " + TABLE_OF_DASHBOARD + " ( "
            + module_id + " INTEGER , " + module_name
            + " TEXT, " + video_nam+ " TEXT, "+video_title +" TEXT, "
            +video_encrypt +" TEXT, "+video_encrypt_type+" TEXT, "
            +Calc+" TEXT, "+Filename+" TEXT, "+Image+" TEXT, "+Moduler_position+" TEXT)";



    //complete table

    private static final String mcqComplete = "CREATE TABLE IF NOT EXISTS " + TABLE_MCQ_COMPLETE + " ( "
            + mcq_module_id + " INTEGER , " + mcq_course_id+ " TEXT, "+ mcq_boolean_condition + " TEXT)";

    private static final String calcComplete = "CREATE TABLE IF NOT EXISTS " + TABLE_CALC_COMPLETE + " ( "
            + calc_module_id + " INTEGER , " + calc_course_id+" TEXT, "+ calc_boolean_condition
            + " TEXT)";

    private static final String faqComplete = "CREATE TABLE IF NOT EXISTS " + TABLE_FAQ_COMPLETE + " ( "
            + faq_module_id + " INTEGER , " + faq_course_id+" TEXT,"+ faq_boolean_condition
            + " TEXT)";


    //complete courses

    private static final String finishCourses = "CREATE TABLE IF NOT EXISTS " + TABLE_FINISHED_COURSES + " ( "
            + finish_moduleID + " INTEGER , " + finish_coursesID+" TEXT,"+ finish_tempcourseid
            + " TEXT)";


    public OfflineDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(fincourses);
        db.execSQL(Listofmodules);
        db.execSQL(dashboard);

        db.execSQL(mcqComplete);
        db.execSQL(calcComplete);
        db.execSQL(faqComplete);

        db.execSQL(finishCourses);

        db.execSQL(expirydate);
        db.execSQL(videovalidate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTOF_MODULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OF_DASHBOARD);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MCQ_COMPLETE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALC_COMPLETE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAQ_COMPLETE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OF_EXPIRY_DATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OF_VIDEOVALIDATION);

        onCreate(db);
    }

    ///COURSES

    public List<DatabaseModules> get_courses() {
        List<DatabaseModules> valueIn = new ArrayList<>();
        String selectQuery ="SELECT  * FROM " + TABLE_COURSES;
        mSqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});
        DatabaseModules vaIN = null;
        if (cursor.moveToFirst()) {
            do {
                vaIN  = new DatabaseModules();
                vaIN.setFinjan_coursesid(cursor.getString(0));
                vaIN.setFinjan_courses(cursor.getString(1));

                valueIn.add(vaIN);
            } while (cursor.moveToNext());
        }
        return valueIn;
    }

    public void set_courses(ArrayList<String> module, ArrayList<String> module_id) {
        mSqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int n = 0; n < module_id.size(); n++) {
            // values.put(temp_id, id);
            values.put(courses_id, module_id.get(n));
            values.put(courses, module.get(n));

            mSqLiteDatabase.insert(TABLE_COURSES, null, values);
            Log.d("Dbase", String.valueOf(mSqLiteDatabase));
        }
    }

    ///MODULES LIST
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LISTOF_MODULES, null, null);
        db.delete(TABLE_FAQ_COMPLETE, null, null);
        db.delete(TABLE_CALC_COMPLETE, null, null);
        db.delete(TABLE_MCQ_COMPLETE, null, null);
        db.delete(TABLE_COURSES, null, null);
        db.delete(TABLE_FINISHED_COURSES, null, null);
        db.delete(TABLE_OF_DASHBOARD, null, null);
        db.delete(TABLE_OF_VIDEOVALIDATION, null, null);

        db.close();
    }


    public void deleteSingleEmail(String string) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OF_EXPIRY_DATE, user_emailid + " = ?",
                new String[] { String.valueOf(string) });
       /* db.delete(TABLE_LISTOF_MODULES, null, null);
        db.delete(TABLE_FAQ_COMPLETE, null, null);
        db.delete(TABLE_CALC_COMPLETE, null, null);
        db.delete(TABLE_MCQ_COMPLETE, null, null);
        db.delete(TABLE_COURSES, null, null);
        db.delete(TABLE_FINISHED_COURSES, null, null);
        db.delete(TABLE_OF_DASHBOARD, null, null);*/
        db.close();

        getexpValidation(string);
    }

    public void set_listOFmodules(ArrayList<String> cModule, ArrayList<String> moduleID,
                                  String id, String exp, String crt, String email) {

        mSqLiteDatabase=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        boolean condition = getListOfModules(id);

        if(condition == false && cModule.size() > 0){
            for(int n=0; n<moduleID.size(); n++){
                values.put(courses_id_for_moudles, id);
                values.put(list_of_modules_id, moduleID.get(n));
                values.put(list_of_modules_name, cModule.get(n));
                values.put(emailid_for_listofmodules, email);

                mSqLiteDatabase.insert(TABLE_LISTOF_MODULES, null, values);
                Log.d("Dbase", String.valueOf(mSqLiteDatabase));
            }

        }else {
          //  Toast.makeText(con, "Data already insert in database", Toast.LENGTH_LONG).show();
        }
        mSqLiteDatabase.delete(TABLE_OF_EXPIRY_DATE,
                "COURSES_ID_FOR_EXP=?", new String[] {id});

        boolean condition1 = checkexpdate(id);

        if(condition1 == false){
            ContentValues values1 = new ContentValues();

            values1.put(courses_id_for_exp, id);
            values1.put(expiry_day, exp);
            values1.put(created_date, crt);
            values1.put(user_emailid, email);

            mSqLiteDatabase.insert(TABLE_OF_EXPIRY_DATE, null, values1);
            Log.d("Dbase", String.valueOf(mSqLiteDatabase));
        }


    }



    public String getexpValidation(String em) {
        String selectQuery = "SELECT  * FROM " + TABLE_OF_EXPIRY_DATE + " WHERE "
                + user_emailid + " = " + em;
        Log.e("", selectQuery);

        mSqLiteDatabase=this.getReadableDatabase();
      //  Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});


        Cursor cursor = mSqLiteDatabase.rawQuery(
                "select * from "+TABLE_OF_EXPIRY_DATE +" where " + user_emailid + " = ? ",
                new String[]{em});

       /* Cursor cursor = mSqLiteDatabase.query(true, TABLE_OF_EXPIRY_DATE,new String[]
                { user_emailid}, null, null, null, null, null,null);*/

        String expDate = null;
        if (cursor.moveToFirst()) {
            do {
                expDate = cursor.getString(1);
                String ss = cursor.getString(2);
                String rr = cursor.getString(3);
                break;

            } while (cursor.moveToNext());
        }
        return expDate;
    }


    public List<DatabaseModules> getListOfCourseMod(String id) {
        List<DatabaseModules> valueIn = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LISTOF_MODULES + " WHERE "
                + courses_id_for_moudles + " = " + id;
        Log.e("", selectQuery);

        mSqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});
        DatabaseModules vaIN = null;

        if (cursor.moveToFirst()) {
            do {
                vaIN  = new DatabaseModules();
                //String name = cursor.getString(7).toString();
                vaIN.set_listof_modID(cursor.getString(0));
                vaIN.set_listof_CourseName(cursor.getString(1));

                valueIn.add(vaIN);
            } while (cursor.moveToNext());
        }
        return valueIn;
    }


    private boolean checkexpdate(String id) {

        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_OF_EXPIRY_DATE + " WHERE "
                + user_emailid + " = " + id;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }


    private boolean getListOfModules(String id) {
        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_LISTOF_MODULES + " WHERE "
                + courses_id_for_moudles + " = " + id;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
              return true;
            } while (cursor.moveToNext());
        }
        return false;
    }


    public List<VideoListModules> getdata(String modID) {
        List<VideoListModules> quesList = new ArrayList<>();
        // Select All Query
      //  String selectQuery ="SELECT  * FROM " + TABLE_OF_DASHBOARD ;
        String selectQuery = "SELECT  * FROM " + TABLE_OF_DASHBOARD + " WHERE "
                + Moduler_position + " = " + modID;
        int i =0;
        mSqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});
        //KEY_ID,KEY_QUES,KEY_ANSWER,KEY_OPTA,KEY_OPTB,KEY_OPTC,KEY_OPTD
        VideoListModules list;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list  = new VideoListModules();
                //String name = cursor.getString(7).toString();
                list.setModule_id(cursor.getString(0));
                list.setModule_name(cursor.getString(1));
                list.setVideo_name(cursor.getString(2));
                list.setCourse_module(cursor.getString(3));
                list.setVideo_encrypt(cursor.getString(4));
                list.setVideo_encrypt_type(cursor.getString(5));
                list.setCourse_calculator(cursor.getString(6));
                list.setFile_Name(cursor.getString(6));
                list.setVideo_image(cursor.getString(8));
                //     list.setCalculator_name(cursor.getString(9));

                quesList.add(list);
            } while (cursor.moveToNext());
        }

        mSqLiteDatabase.close();
        return quesList;

    }




    public void setDashboardValue(List<VideoListModules> quelist, String moduleid) {

        mSqLiteDatabase = this.getWritableDatabase();

        boolean condition = getDashboardvalidation(moduleid);
        if(condition == false){
            for (VideoListModules dc:quelist){
                ContentValues values = new ContentValues();
                values.put(Moduler_position, moduleid);
                values.put(module_id, dc.getModule_id());
                values.put(module_name, dc.getModule_name());
                values.put(video_nam, dc.getVideo_name());
                values.put(video_title, dc.getCourse_module());
                values.put(video_encrypt, dc.getVideo_encrypt());
                values.put(video_encrypt_type, dc.getVideo_encrypt_type());
                values.put(Calc, dc.getCourse_calculator());
      /*  String[] wordnew = dc.getCalculator_name().split(",");        values.put(Calc, String.valueOf(wordnew));*/
                String s = dc.getVideo_encrypt();
                if(s != null){
                    int cs = s.indexOf("/");
                    String finalFilname = s.substring(cs, s.length()) + "";
                    values.put(Filename, finalFilname);
                }
                values.put(Image, dc.getVideo_image());
                mSqLiteDatabase.insert(TABLE_OF_DASHBOARD, null, values);
                Log.d("Dbase", String.valueOf(mSqLiteDatabase));
            }
        }else{
           // Toast.makeText(con, "Data already insert in database", Toast.LENGTH_LONG).show();
        }
    }

    private boolean getDashboardvalidation(String moduleid) {

        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_OF_DASHBOARD + " WHERE "
                + Moduler_position + " = " + moduleid;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }


    public void onDeleteCourseMod() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Drop older table if existed
        db.delete(TABLE_COURSES, null, null);
        //db.execSQL("DELETE " + TABLE_COURSES);
        // Create tables again
        onCreate(db);
    }


    public List<DatabaseModules> getAllCALCComplete(boolean b) {
        mSqLiteDatabase=this.getReadableDatabase();
        ArrayList<DatabaseModules> values = new ArrayList<>();

        String cc = ListofModuleFinjan.course_ID;

        // String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE;
        String selectQuery = "SELECT  * FROM " + TABLE_CALC_COMPLETE + " WHERE "
                + calc_course_id + " = " + ListofModuleFinjan.course_ID;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});
        DatabaseModules vaIN = null;
        if (cursor.moveToFirst()) {
            do {
                vaIN = new DatabaseModules();
                String modID = cursor.getString(0);
                if(b==true){
                    String courseid = cursor.getString(1);
                    vaIN.setCalc_complete_all_courseid(cursor.getString(1));
                    Log.d("validation", courseid);
                }else{
                    String moduleid = cursor.getString(2);
                    //vaIN.setMcq_complete_all_value(cursor.getString(2));
                    Log.d("validation", moduleid);
                }


                vaIN.setMcq_complete_all_modid(cursor.getString(0));

                values.add(vaIN);
            } while (cursor.moveToNext());
        }

        return values;
    }



    public List<DatabaseModules> getAllFAQComplete(boolean b) {
        mSqLiteDatabase=this.getReadableDatabase();
        ArrayList<DatabaseModules> values = new ArrayList<>();

        String cc = ListofModuleFinjan.course_ID;

        // String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE;
        String selectQuery = "SELECT  * FROM " + TABLE_FAQ_COMPLETE + " WHERE "
                + faq_course_id + " = " + ListofModuleFinjan.course_ID;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});
        DatabaseModules vaIN = null;
        if (cursor.moveToFirst()) {
            do {
                vaIN = new DatabaseModules();
                String modID = cursor.getString(0);
                if(b==true){
                    String courseid = cursor.getString(1);
                    vaIN.setFaq_complete_all_courseid(cursor.getString(1));
                    Log.d("validation", courseid);
                }else{
                    String moduleid = cursor.getString(2);
                    //vaIN.setMcq_complete_all_value(cursor.getString(2));
                    Log.d("validation", moduleid);
                }


                vaIN.setMcq_complete_all_modid(cursor.getString(0));

                values.add(vaIN);
            } while (cursor.moveToNext());
        }

        return values;
    }


    public void setMCQcompleted(String listOfmoduleid, String b) {

        mSqLiteDatabase = this.getWritableDatabase();

        boolean condition = checkmcq(listOfmoduleid);
        if(condition == false){
            ContentValues values = new ContentValues();
            values.put(mcq_module_id, listOfmoduleid);
            values.put(mcq_course_id, ListofModuleFinjan.course_ID);
            values.put(mcq_boolean_condition, b);
            mSqLiteDatabase.insert(TABLE_MCQ_COMPLETE, null, values);
        }else{
          //  Toast.makeText(con, "MCQ complete already insert in database", Toast.LENGTH_LONG).show();
        }

    }

    public String getMcqComplete(String listOfmoduleid) {
        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE + " WHERE "
                + mcq_module_id + " = " + listOfmoduleid;
        Log.e("", selectQuery);
        String con=null;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                String mID = cursor.getString(0);
                con = cursor.getString(2);
                Log.d("check", con);

            } while (cursor.moveToNext());
        }
        return con;
    }

    public List<DatabaseModules> getAllMCQComplete(boolean b) {
        mSqLiteDatabase=this.getReadableDatabase();
        ArrayList<DatabaseModules> values = new ArrayList<>();
        String cc = ListofModuleFinjan.course_ID;

       // String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE;
        String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE + " WHERE "
                + mcq_course_id + " = " + ListofModuleFinjan.course_ID;




        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});
        DatabaseModules vaIN = null;
        if (cursor.moveToFirst()) {
            do {
                vaIN = new DatabaseModules();
                String modID = cursor.getString(0);
                if(b==true){
                    String courseid = cursor.getString(1);
                    vaIN.setMcq_complete_all_courseid(cursor.getString(1));
                    Log.d("validation", courseid);
                }else{
                    String moduleid = cursor.getString(2);
                    //vaIN.setMcq_complete_all_value(cursor.getString(2));
                    Log.d("validation", moduleid);
                }

                vaIN.setMcq_complete_all_modid(cursor.getString(0));

                values.add(vaIN);
            } while (cursor.moveToNext());
        }

        return values;
    }

    private boolean checkmcq(String listOfmoduleid) {

        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE + " WHERE "
                + mcq_module_id + " = " + listOfmoduleid;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;

    }


    public void setCalcCompleted(String listOfmoduleid, String b) {
        mSqLiteDatabase = this.getWritableDatabase();

        boolean condition = checkCalc(listOfmoduleid);
        if(condition == false){
            ContentValues values = new ContentValues();
            values.put(calc_module_id, listOfmoduleid);
            values.put(calc_course_id, ListofModuleFinjan.course_ID);
            values.put(calc_boolean_condition, b);
            mSqLiteDatabase.insert(TABLE_CALC_COMPLETE, null, values);
        }else{
          //  Toast.makeText(con, "MCQ complete already insert in database", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkCalc(String list_of_module_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_CALC_COMPLETE + " WHERE "
                + calc_module_id + " = " + list_of_module_id;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

    public String getCalcComplete(String listOfmoduleid) {
        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CALC_COMPLETE + " WHERE "
                + calc_module_id + " = " + listOfmoduleid;
        Log.e("", selectQuery);
        String con=null;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                String mID = cursor.getString(0);
                con = cursor.getString(1);
                Log.d("check", con);

            } while (cursor.moveToNext());
        }
        return con;
    }

    public void setFaqCompleted(String list_of_module_id, String b) {
        mSqLiteDatabase = this.getWritableDatabase();

        boolean condition = checkFaq(list_of_module_id);

        if(condition == false){
            ContentValues values = new ContentValues();
            values.put(faq_module_id, list_of_module_id);
            values.put(faq_course_id, ListofModuleFinjan.course_ID);
            values.put(faq_boolean_condition, b);
            mSqLiteDatabase.insert(TABLE_FAQ_COMPLETE, null, values);
        }else{
          //  Toast.makeText(con, "MCQ complete already insert in database", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkFaq(String list_of_module_id) {
        String selectQuery = "SELECT  * FROM " + TABLE_FAQ_COMPLETE + " WHERE "
                + faq_module_id + " = " + list_of_module_id;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;

    }

    public String getFaqComplete(String listOfmoduleid) {

        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FAQ_COMPLETE + " WHERE "
                + faq_module_id + " = " + listOfmoduleid;
        Log.e("", selectQuery);
        String con=null;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {

                String mID = cursor.getString(0);
                con = cursor.getString(1);
                Log.d("check", con);

            } while (cursor.moveToNext());
        }
        return con;
    }

    public void CourseCheckForComplete(ArrayList<String> module_id, String aTrue) {
        mSqLiteDatabase = this.getWritableDatabase();

       /* boolean condition = finishedCsForcheck("true");
        if(condition==false){*/
            for(int nn=0; nn<module_id.size(); nn++){

                String selectQuery = "SELECT  * FROM " + TABLE_FINISHED_COURSES + " WHERE "
                        + finish_coursesID + " = " + module_id.get(nn);
                Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

                if (cursor.moveToFirst()) {
                    do {
                        Log.d("", "");
                    } while (cursor.moveToNext());
                }else{
                    ContentValues values = new ContentValues();
                    values.put(finish_coursesID, module_id.get(nn));
                 //   values.put(finish_condition, "true");
                    mSqLiteDatabase.insert(TABLE_FINISHED_COURSES, null, values);
                }
            }
       // }
    }

    private boolean finishedCsForcheck(String aTrue) {
        String selectQuery = "SELECT  * FROM " + TABLE_FINISHED_COURSES + " WHERE "
                + finish_coursesID + " = " + aTrue;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }

        return false;
    }

    public void setFinishedCourse(String course_id, String s) {
        mSqLiteDatabase = this.getWritableDatabase();

        boolean condition = finishedCourseValidation(course_id);
        if(condition == false){
            ContentValues values = new ContentValues();
            values.put(finish_coursesID, course_id);
           //values.put(faq_course_id, ListofModuleFinjan.course_ID);
          //  values.put(finish_condition, s);
           mSqLiteDatabase.insert(TABLE_FINISHED_COURSES, null, values);
       // mSqLiteDatabase.update(TABLE_FINISHED_COURSES, values, finish_coursesID+"="+course_id, null);
        Log.d("finished", "update");
        }
    }

    private boolean finishedCourseValidation(String course_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_FINISHED_COURSES + " WHERE "
                + finish_coursesID + " = " + course_id;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }

        return false;
    }

    public List<String> getCourseValue() {

        mSqLiteDatabase=this.getReadableDatabase();
        List<String> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_FINISHED_COURSES + " order by " +finish_coursesID;
        Log.e("", selectQuery);

        String con=null;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                String mID = cursor.getString(0);
                con = cursor.getString(1);
                list.add(con);
                Log.d("check", con);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteFinishedCoures(ArrayList<String> del) {
        try{
            for(int t=0; t<del.size(); t++){
                mSqLiteDatabase.delete(TABLE_FINISHED_COURSES,
                        "finishcourseid=?", new String[] {del.get(t)});
            }
        }catch (Exception e){
            Log.d("check", "");
        }
    }


    public void setvideoComplete(String list_of_moduleID, String aTrue) {

        mSqLiteDatabase = this.getWritableDatabase();

        boolean condition = checkVideoComplete(list_of_moduleID);
        if(condition == false){
            ContentValues values = new ContentValues();
            values.put(video_vali_coursename, list_of_moduleID);
            values.put(video_vali_courseid, ListofModuleFinjan.course_ID);
            values.put(video_vali_condition, aTrue);
            mSqLiteDatabase.insert(TABLE_OF_VIDEOVALIDATION, null, values);
        }else{
            //  Toast.makeText(con, "MCQ complete already insert in database", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkVideoComplete(String list_of_moduleID) {

        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_OF_VIDEOVALIDATION + " WHERE "
                + video_vali_coursename + " = " + list_of_moduleID;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

    public String getVideoCompleted(String list_of_moduleID) {
        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_OF_VIDEOVALIDATION + " WHERE "
                + video_vali_coursename + " = " + list_of_moduleID;
        Log.e("", selectQuery);
        String con=null;

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                String mID = cursor.getString(0);
                con = cursor.getString(2);
                Log.d("check", con);

            } while (cursor.moveToNext());
        }
        return con;

    }

    public List<DatabaseModules> getAllVideoComplete(boolean b) {
        mSqLiteDatabase=this.getReadableDatabase();
        ArrayList<DatabaseModules> values = new ArrayList<>();
        String cc = ListofModuleFinjan.course_ID;

        // String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE;
        String selectQuery = "SELECT  * FROM " + TABLE_OF_VIDEOVALIDATION + " WHERE "
                + video_vali_courseid + " = " + ListofModuleFinjan.course_ID;




        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});
        DatabaseModules vaIN = null;
        if (cursor.moveToFirst()) {
            do {
                vaIN = new DatabaseModules();
                String modID = cursor.getString(0);
                if(b==true){
                    String courseid = cursor.getString(3);
                    vaIN.setVideo_complete_all_courseid(cursor.getString(3));
                    Log.d("validation", courseid);
                }else{
                    String moduleid = cursor.getString(2);
                    //vaIN.setMcq_complete_all_value(cursor.getString(2));
                    Log.d("validation", moduleid);
                }

                vaIN.setMcq_complete_all_modid(cursor.getString(0));

                values.add(vaIN);
            } while (cursor.moveToNext());
        }

        return values;
    }

    public int checkinDatabase() {
        mSqLiteDatabase=this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MCQ_COMPLETE;
        Log.e("", selectQuery);

        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        int count = cursor.getCount();
        Log.d("cursor count", ""+count);
        return count;
    }
}
