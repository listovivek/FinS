package com.myappilication.xpress.finjan2017.models.login.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myappilication.xpress.finjan2017.models.login.DownloadFinjan.DownloadFinjanCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by balasri on 25/4/17.
 */
public class FinalVideoDB extends SQLiteOpenHelper {
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
    String video_language="VIDEO_LANGUAGE";
    String  Filename="Filename";




    private static final String TABLE_FINJAN = "download";

    public FinalVideoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        String finjan_video = "CREATE TABLE IF NOT EXISTS " + TABLE_FINJAN + " ( "
                + module_id + " INTEGER , " + module_name
                + " TEXT, " + video_nam+ " TEXT, "+video_title +" TEXT, " +video_encrypt +" TEXT, "+video_encrypt_type+" TEXT, "+video_language+" TEXT, "+Filename+" TEXT)";
        database.execSQL(finjan_video);
//db.close();
    }
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINJAN);
// Create tables again
        onCreate(db);
    }

    public void onDelete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINJAN);
// Create tables again
        onCreate(db);
    }
    // Adding new question

    public void Finjan_video(List<DownloadFinjanCourse> que) {

        dbase=this.getWritableDatabase();
        for (DownloadFinjanCourse dc:que){
            ContentValues values = new ContentValues();
            values.put(module_id, dc.getId());
            values.put(module_name, dc.getVideo_title());
            values.put(video_nam, dc.getVideo_name());
            values.put(video_title, dc.getVideo_title());
            values.put(video_encrypt, dc.getVideo_encrypt());
            values.put(video_encrypt_type, dc.getVideo_encrypt_type());
            values.put(video_language, dc.getVideo_language());
            String s = dc.getVideo_encrypt();
            int cs = s.indexOf("/");
            String finalFilname = s.substring(cs, s.length()) + "";
            values.put(Filename, finalFilname);

            dbase.insert(TABLE_FINJAN, null, values);
            Log.d("Dbase", String.valueOf(dbase));
        }


    }

    public List<DownloadFinjanCourse> getFinjan_video() {
        List<DownloadFinjanCourse> quesList = new ArrayList<>();
        // Select All Query
        String selectQuery ="SELECT  * FROM " + TABLE_FINJAN ;
        int i =0;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, new String[]{});
        //KEY_ID,KEY_QUES,KEY_ANSWER,KEY_OPTA,KEY_OPTB,KEY_OPTC,KEY_OPTD
        DownloadFinjanCourse list;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list  = new DownloadFinjanCourse();
                //String name = cursor.getString(7).toString();
                list.setId(cursor.getString(0));
                list.setVideo_title(cursor.getString(1));
                list.setVideo_name(cursor.getString(2));
                list.setVideo_title(cursor.getString(3));
                list.setVideo_encrypt(cursor.getString(4));
                list.setVideo_encrypt_type(cursor.getString(5));
                list.setVideo_language(cursor.getString(6));
                list.setFile_Name(cursor.getString(7));

                quesList.add(list);
            } while (cursor.moveToNext());
        }

        dbase.close();
        return quesList;

    }





}



