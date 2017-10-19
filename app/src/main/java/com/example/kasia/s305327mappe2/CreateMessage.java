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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Kasia on 16.10.2017.
 */

public class CreateMessage extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText message;
    Button sendButton;
    ArrayList<String> numbers;

    int day, month, year, hour, minute, setDay, setMonth, setYear, setHour, setMinute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createmessage_activity);
        message = (EditText) findViewById(R.id.message);
        numbers = setNrList();
    }

    private ArrayList<String> setNrList() {
        ArrayList<String> checkedId = getIntent().getStringArrayListExtra("numbers");
        if (checkedId == null) {
            checkedId = new ArrayList<>();
            String[] projection = new String[] {ContactProvider.KEY_TEL_NR};
            Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    checkedId.add(cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR)));
                } while (cursor.moveToNext());
            }
        }
        return checkedId;
    }

    public void sendNow(View v) {
        Intent intent = new Intent(this, SendMessage.class);
        intent.putStringArrayListExtra("numbers", numbers);
        String messageText = ((EditText)findViewById(R.id.message)).getText().toString();
        if (!textOk(messageText))
        {
            return;
        }
        intent.putExtra("message", messageText);
        this.startService(intent);
        message.setText("");
    }

    //sjekke om meldingen inneholder minimum 3 tegn
    private boolean textOk (String messageText) {
        if (messageText.length() < 3) {

            //endre til popup vindu
            Toast.makeText(this, "Meldingen må inneholde minimum 3 tegn!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void sendLater(View v) {
        String messageText = ((EditText)findViewById(R.id.message)).getText().toString();
        if (!textOk(messageText))
        {
            return;
        }
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
        setMonth = i1+1;
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
        String dateToConvert = setYear + "/" + setMonth + "/" + setDay + " " + setHour + ":" + setMinute;
        Toast.makeText(getApplicationContext(), dateToConvert, Toast.LENGTH_LONG).show();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        long dateMillis = 0;
        long now = System.currentTimeMillis();
        try {
             dateMillis = (format.parse(dateToConvert)).getTime();
        } catch (ParseException e) {
            //parse exception bør ikke skje siden Strengen som parses er formatert
        e.printStackTrace();
        Toast.makeText(getApplicationContext(), "Prøv igjen senere", Toast.LENGTH_SHORT).show();
        finish();
    }

        if (dateMillis < now + (5 * 60 * 1000) ) {
            //endre til pop-up vindu?
            Toast.makeText(getApplicationContext(), "Du må velge et tilspunkt min. 5 minutter fra nå", Toast.LENGTH_SHORT ).show();
            return;
        }

        MessageDB db = new MessageDB(getApplicationContext());
        db.saveMessage(message.getText().toString(), numbers, dateMillis, false);
        Toast.makeText(getApplicationContext(), "Meldingene lagret, skal sendes på senere tidspunkt", Toast.LENGTH_SHORT).show();
        message.setText("");
    }
}
