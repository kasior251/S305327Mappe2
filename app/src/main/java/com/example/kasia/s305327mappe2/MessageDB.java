package com.example.kasia.s305327mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kasia on 18.10.2017.
 */

public class MessageDB extends SQLiteOpenHelper {

    static String TABLE_MESSAGES = "Messages";
    static String KEY_ID = "_ID";
    static String KEY_MESSAGE = "Text";
    static String KEY_PHONE = "Phone";
    static String KEY_TIME = "Time";
    static String KEY_PEDIODIC = "Periodic";
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Messages";  //bruk samme db som for å lagre kontakter
    static String CREATE_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
            KEY_MESSAGE + " TEXT NOT NULL, " + KEY_PHONE + " INTEGER, " + KEY_TIME + " LONG, " +
            KEY_PEDIODIC + " BOOLEAN)";

    public MessageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d("SQL", CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(sqLiteDatabase);
    }

    public void saveMessage(String text, ArrayList<String> phones, long time, boolean periodic) {
        if (periodic) {
            Message message = new Message(text, 0, time, true);
            writeToDB(message);
        }
        else {
            ArrayList<Message> messages = new ArrayList<>();
            for (String s : phones) {
                Log.d("DB", "String s is " + s);
                Message message = new Message(text, Integer.parseInt(s), time, false);
                messages.add(message);
            }
            writeToDB(messages);
        }
    }

    private void writeToDB(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message.getText());
        values.put(KEY_PHONE, message.getPhone());
        values.put(KEY_TIME, message.getTime());
        values.put(KEY_PEDIODIC, message.isPeriodic());
        db.insert(TABLE_MESSAGES, null, values);
        db.close();
    }

    private void writeToDB(ArrayList<Message> messages) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Message m : messages) {
            ContentValues values = new ContentValues();
            values.put(KEY_MESSAGE, m.getText());
            values.put(KEY_PHONE, m.getPhone());
            values.put(KEY_TIME, m.getTime());
            values.put(KEY_PEDIODIC, m.isPeriodic());
            db.insert(TABLE_MESSAGES, null, values);
        }
        db.close();
    }
}
