package com.example.kasia.s305327mappe2;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 17.10.2017.
 */

public class Contact {

    private int _ID;
    private String first;
    private String last;
    private String Phone;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Contact() {

    }

    public Contact(int _ID, String first, String last, String phone) {

        this._ID = _ID;
        this.first = first;
        this.last = last;
        Phone = phone;
    }

    public Contact(String first, String last, String phone) {

        this.first = first;
        this.last = last;
        Phone = phone;
    }

    public Contact(int _ID) {

        this._ID = _ID;
    }
}
