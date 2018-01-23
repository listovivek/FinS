package com.myappilication.xpress.finjan2017;

/**
 * Created by sureshmano on 3/22/2017.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "FAQManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "tblFaq";
    private static final String TABLE_USER = "userdatble";

    private static final String TABLE_ISGETCOUPON = "isusrgetcoupon";

    private static final String TABLE_FAQ_FINISHED = "faqfinished";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String Question = "Question";
    private static final String Answer = "Answer";

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String FAQKEY_ID = "id";
    private static final String COURSEID_FOR_FAQ = "faqcourseid";
    private static final String FAQ_CONDITION = "condition";

    private static final String isusrId = "isuserId";
    private static final String isusrgetEmail = "isuseremail";
    private static final String isusrgetModID = "isusergetModid";
    private static final String isusrgetExpDate = "isusergetExpDate";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + Question + " TEXT,"
                + Answer + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_USERS_TABLE="CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + EMAIL + " TEXT,"
                + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);


        String CREATE_FAQ_FINISHED_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_FAQ_FINISHED + "("
                + FAQKEY_ID + " TEXT," + COURSEID_FOR_FAQ + " TEXT,"
                + FAQ_CONDITION + " TEXT" + ")";
        db.execSQL(CREATE_FAQ_FINISHED_TABLE);

        String CREATE_IS_USRGET_COUPON ="CREATE TABLE IF NOT EXISTS " + TABLE_ISGETCOUPON + "("
                + isusrId + " TEXT," + isusrgetEmail + " TEXT," + isusrgetExpDate + " TEXT,"
                + isusrgetModID + " TEXT" + ")";
        db.execSQL(CREATE_IS_USRGET_COUPON);
    }




    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAQ_FINISHED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISGETCOUPON);
        // Create tables again
        onCreate(db);


    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact

    void addUser(String email,String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL, email); // Contact Name
        values.put(PASSWORD, password); // Contact Phone

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    boolean Checkuser(String email,String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();

       /* Cursor cursor = db.query(true, TABLE_USER,new String[]
                { EMAIL, PASSWORD }, null, null, null, null, null,null);*/

        Cursor cursor = db.rawQuery(
                "select * from "+TABLE_USER +" where " + EMAIL + " = ? ",
                new String[]{email});

      //  Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_USER, null );

        if(cursor.getCount()>0)
        {
            do {
                cursor.moveToFirst() ;
                String Email=cursor.getString(0);
                String Pass=cursor.getString(1);

                if(Email.equals(email) && Pass.equals(password)){
                    return true;
                }else{
                    return false;
                }

        //voevlele

            }while (cursor.moveToNext());

        }
        else{

        }
        return false;
    }

    void addContact(Faqlistdatas Faq) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Question, Faq.getFaq_qus()); // Contact Name
        values.put(Answer, Faq.getFaq_ans()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    public void addContact(List<Faqlistdatas> FaqList) {
        for (Faqlistdatas Faq :FaqList             ) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Question, Faq.getFaq_qus()); // Contact Name
            values.put(Answer, Faq.getFaq_ans()); // Contact Phone

            // Inserting Row
            db.insert(TABLE_CONTACTS, null, values);
            db.close();
        }
        // Closing database connection
    }
public void OnDelete()
{
    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    onCreate(db);
}

    // Getting Search details
    List<Faqlistdatas> getContact(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Faqlistdatas> contact=new ArrayList<>();;
        Cursor cursor = db.query(true, TABLE_CONTACTS, new String[] { KEY_ID,
                        Question,Answer }, Question + " LIKE ?",
                new String[] { "%"+filter+"%" }, null, null, null,
                null);
        Faqlistdatas list;
        if (cursor.getCount()>0) {
            do {
                list=new Faqlistdatas();
                cursor.moveToFirst();
                list.setId(Integer.parseInt(cursor.getString(0)));
                list.setFaq_qus(cursor.getString(1));
                list.setFaq_ans(cursor.getString(2));
                contact.add(list);
            }while (cursor.moveToNext());
        }

        else {
            cursor = db.query(true, TABLE_CONTACTS, new String[]{KEY_ID,
                            Question,Answer}, Answer + " LIKE ?",
                    new String[]{"%"+filter + "%"}, null, null, null,
                    null);
            if (cursor.getCount()>0) {
                do {
                    list=new Faqlistdatas();
                    cursor.moveToFirst();
                    list.setId(Integer.parseInt(cursor.getString(0)));
                    list.setFaq_qus(cursor.getString(1));
                    list.setFaq_ans(cursor.getString(2));
                    contact.add(list);
                }while (cursor.moveToNext());
            }

        }
       return contact;
    }

    // Getting All Contacts
    public List<Faqlistdatas> getAllContacts() {
        List<Faqlistdatas> contactList = new ArrayList<Faqlistdatas>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Faqlistdatas contact = new Faqlistdatas();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setFaq_qus(cursor.getString(1));
                contact.setFaq_ans(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Faqlistdatas contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Question, contact.getFaq_qus());
        values.put(Answer, contact.getFaq_ans());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getFaq_Id()) });
    }

    // Deleting single contact
    public void deleteContact(Faqlistdatas contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getFaq_Id()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void setFaqFinished(String course_id, String aTrue) {

        boolean condition = isCourseIDThere(course_id);

        if(condition == false){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(COURSEID_FOR_FAQ, course_id);
            values.put(FAQ_CONDITION, aTrue);

            db.insert(TABLE_FAQ_FINISHED, null, values);
            db.close();
        }else{
        }


    }

    private boolean isCourseIDThere(String course_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FAQ_FINISHED + " WHERE "
                + COURSEID_FOR_FAQ + " = " + course_id;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                return true;
            } while (cursor.moveToNext());
        }
        return false;
    }

    public boolean isFaqFinised(String course_id) {

        String selectQuery = "SELECT  * FROM " + TABLE_FAQ_FINISHED + " WHERE "
                + COURSEID_FOR_FAQ + " = " + course_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
        if (cursor.moveToFirst()) {
            do {
                String conditon = cursor.getString(2);
                return Boolean.valueOf(conditon);
            } while (cursor.moveToNext());
        }
        return false;
    }

    public void setIsUsrgCoupon(String email, String modID, String exp) {
        SQLiteDatabase db = this.getWritableDatabase();



    }
}

