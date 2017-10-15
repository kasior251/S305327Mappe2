package com.example.kasia.s305327mappe2;

/**
 * Created by Kasia on 10.10.2017.
 */

public class Contact {

    private long _ID;
    private String First;
    private String Last;
    private String Phone;

    public Contact() {
    }

    public Contact(String first, String last, String phone) {
        First = first;
        Last = last;
        Phone = phone;
    }

    public Contact(long _ID, String first, String last, String phone) {
        this._ID = _ID;
        First = first;
        Last = last;
        Phone = phone;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public void setFirst(String first) {
        First = first;
    }

    public void setLast(String last) {
        Last = last;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public long get_ID() {

        return _ID;
    }

    public String getFirst() {
        return First;
    }

    public String getLast() {
        return Last;
    }

    public String getPhone() {
        return Phone;
    }
}
