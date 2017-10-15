package com.example.kasia.s305327mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 10.10.2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_CONTACTS = "ContactsTable";
    static String KEY_ID = "_ID";
    static String KEY_FIRSTNAME = "First";
    static String KEY_LASTNAME = "Last";
    static String KEY_TEL_NR = "Phone";
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Contacts";

    public DBHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_FIRSTNAME + " TEXT, " + KEY_LASTNAME + " TEXT,  " + KEY_TEL_NR + " INTEGER" + ")";
        Log.d("SQL", CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, contact.getFirst());
        values.put(KEY_LASTNAME, contact.getLast());
        values.put(KEY_TEL_NR, contact.getPhone());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public List<Contact> findAll() {
        List<Contact> allContacts = new ArrayList<Contact>();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.set_ID(cursor.getLong(0));
                contact.setFirst(cursor.getString(1));
                contact.setLast(cursor.getString(2));
                contact.setPhone(cursor.getString(3));
                allContacts.add(contact);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return allContacts;
    }
}
