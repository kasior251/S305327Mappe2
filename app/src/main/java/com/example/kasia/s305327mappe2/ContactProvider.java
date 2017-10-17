package com.example.kasia.s305327mappe2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 10.10.2017.
 */

public class ContactProvider extends ContentProvider {

    //Java namespace for Content Provider
    static final String PROVIDER = "com.example.kasia.s305327mappe2";

    //contacts er en virtuell mappe i Content Provideren
    static final String URL = "content://" + PROVIDER + "/contacts";
    static final Uri URI = Uri.parse(URL);
    static final int uriCode = 1;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "contacts", uriCode);
    }

    private SQLiteDatabase db;
    static String TABLE_CONTACTS = "ContactsTable";
    static String KEY_ID = "_ID";
    static String KEY_FIRSTNAME = "First";
    static String KEY_LASTNAME = "Last";
    static String KEY_TEL_NR = "Phone";
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Contacts";
    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_FIRSTNAME + " TEXT, " + KEY_LASTNAME + " TEXT,  " + KEY_TEL_NR + " INTEGER" + ")";

    //klassen til å lage og "betjene" databasen
    private static class DbHelper extends SQLiteOpenHelper {
        DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int olddb, int newdb) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    }
    //slutt av klassen DbHelper

    @Override
    public boolean onCreate() {

        DbHelper helper = new DbHelper(getContext());
        db = helper.getWritableDatabase();
        if (db != null)
            return true;
        return false;
    }

    //returnerer cursor som gir oss lese- og skrive- tilgang til databasen
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
            cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null);
            return cursor;

    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/contacts";
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }
    }

    //håndterer nye INSERT queries til ContentProvider'en
    //inn-data uri for ContentProvider og set av values
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = db.insert(TABLE_CONTACTS, null, values);

        //verifisere om rad var lagt til
        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(URI, id);
            //gi beskjed til alle seere om at en rad ble oppdatert
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        Toast.makeText(getContext(), "Kunne ikke legge til ny rad", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int rowsDeleted = 0;

        switch (uriMatcher.match(uri)) {
            case uriCode:
                rowsDeleted = db.delete(TABLE_CONTACTS, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Feil uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
