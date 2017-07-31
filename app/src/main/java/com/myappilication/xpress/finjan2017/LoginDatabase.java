package com.myappilication.xpress.finjan2017;

/**
 * Created by sureshmano on 3/22/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myappilication.xpress.finjan2017.models.login.faq.Faqlistdatas;

import java.util.ArrayList;
import java.util.List;

public class LoginDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "LoginData";

    // Contacts table name
    private static final String TABLE_CONTACTS = "tblogin";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String Question = "Question";
    private static final String Answer = "Answer";

    public LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + Question + " TEXT,"
                + Answer + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Faqlistdatas Faq) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Question, Faq.getFaq_qus()); // Contact Name
        values.put(Answer, Faq.getFaq_ans()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    void addContact(List<Faqlistdatas> FaqList) {
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
void OnDelete()
{
    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
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

}

