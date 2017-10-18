package com.example.kasia.s305327mappe2;

import java.sql.Time;
import java.sql.Date;

/**
 * Created by Kasia on 18.10.2017.
 */

public class Message {

    private int id;
    private String text;
    private int phone;
    private long time;
    private boolean periodic = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isPeriodic() {
        return periodic;
    }

    public void setPeriodic(boolean periodic) {
        this.periodic = periodic;
    }

    public Message(String text, long time) {

        this.text = text;
        this.time = time;
    }

    public Message(String text, int phone, long time) {
        this.text = text;
        this.phone = phone;
        this.time = time;
    }

    public Message(String text, int phone, long time, boolean periodic) {

        this.text = text;
        this.phone = phone;
        this.time = time;
        this.periodic = periodic;
    }
}
