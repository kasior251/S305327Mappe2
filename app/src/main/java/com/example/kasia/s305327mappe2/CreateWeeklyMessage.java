package com.example.kasia.s305327mappe2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kasia on 19.10.2017.
 */

public class CreateWeeklyMessage extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText message;
    TextView weeklyDate;
    private MessageDB dbHandler;
    String date = "";
    int day, month, year, hour, minute, setDay, setMonth, setYear, setHour, setMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createweeklymessage_activity);
        dbHandler = new MessageDB(getApplicationContext());
        message = (EditText) findViewById(R.id.weeklymessage);
        weeklyDate = (TextView) findViewById(R.id.weeklydate);
        if (findMessage() != null) {
            message.setText(findMessage());
            weeklyDate.setText(setDate());
        }
    }

    private String findMessage() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.query(MessageDB.TABLE_MESSAGES, new String[] {MessageDB.KEY_MESSAGE, MessageDB.KEY_TIME},
                MessageDB.KEY_PEDIODIC + " = ? ", new String[] {String.valueOf(1)}, null, null, null, null);
        if (cursor == null || !cursor.moveToFirst() ) {
            return null;
        }
        cursor.moveToFirst();
        String text = cursor.getString(0);
        date = cursor.getString(1);
        setDate();
        cursor.close();
        db.close();
        return text;
    }

    private String setDate() {
        String string = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Toast.makeText(getApplicationContext(), "Day " + day, Toast.LENGTH_SHORT).show();
        switch (day) {
            case Calendar.MONDAY:
                string = "Sendes hver mandag, kl. ";
                break;
            case Calendar.TUESDAY:
                string = "Sendes hver tirsdag, kl. ";
                break;
            case Calendar.WEDNESDAY:
                string = "Sendes hver onsdag, kl. ";
                break;
            case Calendar.THURSDAY:
                string = "Sendes hver torsdag, kl. ";
                break;
            case Calendar.FRIDAY:
                string = "Sendes hver fredag,kl. ";
                break;
            case Calendar.SATURDAY:
                string = "Sendes hver lørdag, kl. ";
                break;
            case Calendar.SUNDAY:
                string = "Sendes hver søndag, kl. ";
                break;
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        string += hour + ":";
        int minute = calendar.get(Calendar.MINUTE);
        string += minute;
        return string;
    }

    private boolean textOk (String messageText) {
        if (messageText.length() < 3) {

            //endre til popup vindu
            Toast.makeText(this, "Meldingen må inneholde minimum 3 tegn!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void save(View v) {
        String messageText = ((EditText)findViewById(R.id.weeklymessage)).getText().toString();
        if (!textOk(messageText))
        {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(CreateWeeklyMessage.this, CreateWeeklyMessage.this, year, month, day );
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

        TimePickerDialog dialog = new TimePickerDialog(CreateWeeklyMessage.this, CreateWeeklyMessage.this, hour, minute, DateFormat.is24HourFormat(this));
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

        ArrayList<String> numbers = new ArrayList<>();
        String[] projection = new String[] {ContactProvider.KEY_TEL_NR};
        Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                numbers.add(cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR)));
            } while (cursor.moveToNext());
        }
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(MessageDB.TABLE_MESSAGES, MessageDB.KEY_PEDIODIC + " = ? ", new String[] {String.valueOf(1)});
        db.close();
        dbHandler.saveMessage(message.getText().toString(), numbers, dateMillis, true);
        Toast.makeText(getApplicationContext(), "Meldingene lagret, skal sendes på senere tidspunkt", Toast.LENGTH_SHORT).show();
        message.setText("");
    }
}
