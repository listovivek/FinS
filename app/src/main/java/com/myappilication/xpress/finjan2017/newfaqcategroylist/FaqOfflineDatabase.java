package com.myappilication.xpress.finjan2017.newfaqcategroylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;
import com.myappilication.xpress.finjan2017.models.login.offlineDatabase.DatabaseModules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suresh on 6/7/17.
 */
public class FaqOfflineDatabase extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private SQLiteDatabase mSqLiteDatabase;
        private Context context;

    public static ArrayList<String> offlineCname = new ArrayList<String>();
    public static ArrayList<String> offlineCID = new ArrayList<String>();

        // Database Name
        private static final String DATABASE_NAME = "faqoffline";

    //table name
        private static final String TABLE_FAQCATEGORYLIST = "faqcategroylist";
        private static final String TABLE_FAQQUSANS = "faqquesans";

        private static final String faqClisttempID = "faqclisttempid";
        private static final String faqCategorylistname = "faqcategorylistname";
        private static final String faqcategorylistid = "faqcategoryid";

    private static final String faqquesanstempID = "faqquesanstempid";
    private static final String faqcatidforques = "faqcatidforques";
    private static final String faqquestion = "faqquestion";
    private static final String faqanswer = "faqanswer";

        private static final String faqCList = "CREATE TABLE IF NOT EXISTS " + TABLE_FAQCATEGORYLIST + " ( "
                + faqClisttempID + " INTEGER , " + faqCategorylistname
                + " TEXT, "+faqcategorylistid+" TEXT)";

    private static final String faqquesans = "CREATE TABLE IF NOT EXISTS " + TABLE_FAQQUSANS + " ( "
            + faqquesanstempID + " INTEGER , " + faqquestion
            + " TEXT, "+faqanswer+" TEXT, "+faqcatidforques+" TEXT)";


        public FaqOfflineDatabase(Context con) {
            super(con, DATABASE_NAME, null, DATABASE_VERSION);
            context = con;
        }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(faqCList);
        db.execSQL(faqquesans);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAQCATEGORYLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAQQUSANS);
        onCreate(db);
    }

    public void addCategoryName(ArrayList<String> categoryID,
                                ArrayList<String> categoryName) {
        mSqLiteDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        boolean condition = checkFaqList(categoryID);

        if(condition == false){
            for (int n = 0; n < categoryName.size(); n++) {
                values.put(faqcategorylistid, categoryID.get(n));
                values.put(faqCategorylistname, categoryName.get(n));

                mSqLiteDatabase.insert(TABLE_FAQCATEGORYLIST, null, values);
                Log.d("Dbase", String.valueOf(mSqLiteDatabase));
            }
        }


    }

    private boolean checkFaqList(ArrayList<String> categoryID) {

        String selectQuery = "SELECT  * FROM " + TABLE_FAQCATEGORYLIST + " WHERE "
                + faqcategorylistid + " = " + categoryID.get(0);

        mSqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }

        return false;
    }

    public void getFaqList() {


        String selectQuery = "SELECT  * FROM " + TABLE_FAQCATEGORYLIST;
        Log.e("", selectQuery);

        mSqLiteDatabase=this.getReadableDatabase();
        //  Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});


        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                offlineCname.add(cursor.getString(1));
                offlineCID.add(cursor.getString(2));

            } while (cursor.moveToNext());
        }
    }

    public void addQuesAns(List<Faqlistdatas> faqList, String catid) {

        mSqLiteDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        boolean condition = checkquesans(catid);

        if(condition == false){
            for (int n = 0; n < faqList.size(); n++) {
                values.put(faqcatidforques, catid);
                values.put(faqquestion, faqList.get(n).getFaq_qus());
                values.put(faqanswer, faqList.get(n).getFaq_ans());

                mSqLiteDatabase.insert(TABLE_FAQQUSANS, null, values);
                Log.d("Dbase", String.valueOf(mSqLiteDatabase));
            }
        }



    }

    private boolean checkquesans(String catid) {
        String selectQuery = "SELECT  * FROM " + TABLE_FAQQUSANS + " WHERE "
                + faqcatidforques + " = " + catid;

        mSqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }

        return false;
    }

    public void getQuesAns(String cID) {

        String selectQuery = "SELECT  * FROM " + TABLE_FAQQUSANS + " WHERE "
                + faqcatidforques + " = " + cID;

        mSqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                Faqlistdatas dd = new Faqlistdatas(cursor.getString(1), cursor.getString(2));
               /* String qess = cursor.getString(1);
                dd.setFaq_qus(cursor.getString(1));
                dd.setFaq_ans(cursor.getString(2));*/
                FaqCategroyLIstActivity.faqList.add(dd);
            } while (cursor.moveToNext());
        }

    }

    public List<Faqlistdatas> getofflinesearchvalue(String filter) {
        mSqLiteDatabase=this.getReadableDatabase();
        List<Faqlistdatas> contact=new ArrayList<>();;

        Cursor cursor = mSqLiteDatabase.query(true, TABLE_FAQQUSANS, new String[] { faqcatidforques,
                        faqquestion,faqanswer }, faqquestion + " LIKE ?",
                new String[] { "%"+filter+"%" }, null, null, null,
                null);



        Faqlistdatas list;
        int count=0;
        if (cursor.moveToFirst()) {
            do {
                //count++;
                list=new Faqlistdatas();

                //list.setId(Integer.parseInt(cursor.getString(0)));
                Log.d("ques", cursor.getString(1));
                list.setFaq_qus(cursor.getString(1));
                list.setFaq_ans(cursor.getString(2));
                contact.add(list);
               /* if(count==1){
                    Log.d("vali", cursor.getString(1));
                    break;
                }*/
            }while (cursor.moveToNext());
        }else {
            cursor = mSqLiteDatabase.query(true, TABLE_FAQQUSANS, new String[]{faqcatidforques,
                            faqquestion,faqanswer}, faqquestion + " LIKE ?",
                    new String[]{"%"+filter + "%"}, null, null, null,
                    null);
            if (cursor.moveToFirst()) {
                do {
                    list=new Faqlistdatas();

                    list.setId(Integer.parseInt(cursor.getString(0)));
                    list.setFaq_qus(cursor.getString(1));
                    list.setFaq_ans(cursor.getString(2));
                    contact.add(list);
                    if(cursor.getCount()==10){
                        Log.d("vali", cursor.getString(1));
                        break;
                    }
                }while (cursor.moveToNext());
            }

        }
        Log.d("", "");
        return contact;

    }
}
