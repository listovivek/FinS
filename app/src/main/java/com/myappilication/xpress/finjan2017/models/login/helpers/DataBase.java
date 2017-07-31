package com.myappilication.xpress.finjan2017.models.login.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListModules;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.DatabaseModules;

import java.util.ArrayList;
import java.util.List;

//import com.myappilication.xpress.finjan2017.model.login.dashboard.DashboardCourses;

/**
 * Created by balasri on 6/4/17.
 */
public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "triviaQuiz";
    private SQLiteDatabase dbase;



    String module_id="MODULE_ID";
    String module_name="MODULE_NAME";
    String video_nam="VIDEO_NAME";
    String video_title="VIDEO_TITLE";
    String video_encrypt="VIDEO_ENCRYPT";
    String video_encrypt_type="VIDEO_ENCRYPT_TYPE";
    String Calc ="Calc";
    String Filename="Filename";
    String Image="Image";

    String list_of_modules_id = "LISTOFMODULES_ID";
    String list_of_modules_nm = "LISTOFMODULES_NAME";
    String temp_id = "TEMP_ID";


    String finjan_courses = "FINJAN_COURSES";
    String finjan_courses_id = "FINJAN_COURSES_ID";




    private static final String TABLE_FINJAN_COURSES = "finjancourses";
    private static final String TABLE_DOWNLOAD = "download";
    private static final String TABLE_LISTOF_MODULES = "modulelist";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        String video = "CREATE TABLE IF NOT EXISTS " + TABLE_DOWNLOAD + " ( "
                + module_id + " INTEGER , " + module_name
                + " TEXT, " + video_nam+ " TEXT, "+video_title +" TEXT, "
                +video_encrypt +" TEXT, "+video_encrypt_type+" TEXT, "
                +Calc+" TEXT, "+Filename+" TEXT, "+Image+" TEXT)";

        String Listofmod = "CREATE TABLE IF NOT EXISTS " + TABLE_LISTOF_MODULES + " ( "
                + list_of_modules_id + " INTEGER , " + list_of_modules_nm
                + " TEXT, "+temp_id+" TEXT)";

        String fincourses = "CREATE TABLE IF NOT EXISTS " + TABLE_FINJAN_COURSES + " ( "
                + finjan_courses_id + " INTEGER , " + finjan_courses
                + " TEXT, "+temp_id+" TEXT)";

        database.execSQL(video);
        database.execSQL(Listofmod);
        database.execSQL(fincourses);
//db.close();
    }
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOAD);
// Create tables again
        onCreate(db);
    }

    public void onDelete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOAD);
// Create tables again
        onCreate(db);
    }
    // Adding new question

    public void onDeleteListMod()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTOF_MODULES);
// Create tables again
        onCreate(db);
    }

    public void onDeleteCourseMod()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINJAN_COURSES);
// Create tables again
        onCreate(db);
    }


    public void data(List<VideoListModules> quelist) {

        dbase=this.getWritableDatabase();
        for (VideoListModules dc:quelist){
            ContentValues values = new ContentValues();
            values.put(module_id, dc.getModule_id());
            values.put(module_name, dc.getModule_name());
            values.put(video_nam, dc.getVideo_name());
            values.put(video_title, dc.getCourse_module());
            values.put(video_encrypt, dc.getVideo_encrypt());
            values.put(video_encrypt_type, dc.getVideo_encrypt_type());
            values.put(Calc, dc.getCourse_calculator());
      /*  String[] wordnew = dc.getCalculator_name().split(",");
        values.put(Calc, String.valueOf(wordnew));*/
            String s = dc.getVideo_encrypt();
            if(s != null){
                int cs = s.indexOf("/");
                String finalFilname = s.substring(cs, s.length()) + "";
                values.put(Filename, finalFilname);
            }
            values.put(Image, dc.getVideo_image());
            dbase.insert(TABLE_DOWNLOAD, null, values);
            Log.d("Dbase", String.valueOf(dbase));
        }


    }

    public List<VideoListModules> getdata() {
        List<VideoListModules> quesList = new ArrayList<>();
        // Select All Query
        String selectQuery ="SELECT  * FROM " + TABLE_DOWNLOAD ;
        int i =0;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});
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
                list.setVideo_image(cursor.getString(7));
                //     list.setCalculator_name(cursor.getString(9));

                quesList.add(list);
            } while (cursor.moveToNext());
        }

        dbase.close();
        return quesList;

    }

    public List<DatabaseModules> getListOfCourseMod() {
        List<DatabaseModules> valueIn = new ArrayList<>();
        String selectQuery ="SELECT  * FROM " + TABLE_LISTOF_MODULES;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});
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

    public List<DatabaseModules> getFinjan_courses() {
        List<DatabaseModules> valueIn = new ArrayList<>();
        String selectQuery ="SELECT  * FROM " + TABLE_FINJAN_COURSES;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});
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

    public void listOFmodules(ArrayList<String> cModule, ArrayList<String> moduleID, String id) {

        dbase=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int n=0; n<moduleID.size(); n++){
            values.put(temp_id, id);
            values.put(list_of_modules_id, moduleID.get(n));
            values.put(list_of_modules_nm, cModule.get(n));


            dbase.insert(TABLE_LISTOF_MODULES, null, values);
            Log.d("Dbase", String.valueOf(dbase));
        }
    }




    public void finjan_Courses(ArrayList<String> module, ArrayList<String> module_id) {
        dbase=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for(int n=0; n<module_id.size(); n++){
           // values.put(temp_id, id);
            values.put(finjan_courses_id, module_id.get(n));
            values.put(finjan_courses, module.get(n));


            dbase.insert(TABLE_FINJAN_COURSES, null, values);
            Log.d("Dbase", String.valueOf(dbase));
        }
    }


}

