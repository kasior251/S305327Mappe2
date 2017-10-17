package com.example.kasia.s305327mappe2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kasia on 16.10.2017.
 */

public class CreateMessage extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText message;
    Button sendButton;

    int day, month, year, hour, minute, setDay, setMonth, setYear, setHour, setMinute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmessage_activity);
    }

    public void sendNow(View v) {
        ArrayList<String> numbers = new ArrayList<>();
        String[] projection = new String[] {ContactProvider.KEY_TEL_NR};
        Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                numbers.add(cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR)));
            } while (cursor.moveToNext());
        }
        Intent intent = new Intent(this, SendMessage.class);
        intent.putStringArrayListExtra("numbers", numbers);
        intent.putExtra("message", ((EditText)findViewById(R.id.message)).getText().toString());
        this.startService(intent);
    }

    public void sendLater(View v) {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(CreateMessage.this, CreateMessage.this, year, month, day );
        dialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        setYear = i;
        setMonth = i1;
        setDay = i2;

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(CreateMessage.this, CreateMessage.this, hour, minute, DateFormat.is24HourFormat(this));
        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        setHour = i;
        setMinute = i1;
        TextView date = (TextView) findViewById(R.id.date);
        date.setText("Date " + setYear + "/" + setMonth + "/" + setDay + " " + setHour + ":" + setMinute);
    }
}
