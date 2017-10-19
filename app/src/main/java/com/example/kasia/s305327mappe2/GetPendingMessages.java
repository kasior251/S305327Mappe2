package com.example.kasia.s305327mappe2;


import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kasia on 18.10.2017.
 */

public class GetPendingMessages extends Service {

    private MessageDB dbHandler;
    private int sent = 0;
    private int deleted = 0;
    private int updated = 0;
    private int total;


    public int onStartCommand(Intent intent, int flags, int startId) {
        dbHandler = new MessageDB(getApplicationContext());
        Toast.makeText(getApplicationContext(), "getting messages", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "In get pending Messages", Toast.LENGTH_SHORT).show();
        messageHandler(fetchMessages());
        Toast.makeText(getApplicationContext(), "Sent " + sent + " messages", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Updated " + updated + " messages", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Deleted " + deleted + " messages", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Fetched " + total + " messages", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    //få alle meldinger fra databasen
    private ArrayList<Message> fetchMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        //velg alle meldinger i db
        String query = "SELECT * FROM " + MessageDB.TABLE_MESSAGES;
        Log.d("SQL" , query);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setId(cursor.getInt(0));
                message.setText(cursor.getString(1));
                message.setPhone(cursor.getInt(2));
                message.setTime(cursor.getLong(3));
                message.setPeriodic(cursor.getInt(4)>0);
                messages.add(message);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        total = messages.size();
        return messages;
    }

    private void messageHandler(ArrayList<Message> messages) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
        String dateText = df.format(date);
        Toast.makeText(getApplicationContext(), "Date " + dateText, Toast.LENGTH_SHORT).show();
        for (Message m : messages) {
            Log.d("TIME", "now " + now + "should be sent " + m.getTime());
            //sende kun meldinger som er due i løpet av neste 65 sekunder
            if (m.getTime() > now && m.getTime() < now + 65 * 1000) {
                sendMessage(m);
            }
            else if (m.getTime() > now + 65 * 1000) {
                //dersom meldingen ikke er due ennå, fortsett til neste
                continue;
            }
            //behandle meldingen (slett eller oppdater med ny dato)
            deleteMessage(m);
        }
    }

    private void deleteMessage(Message message) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        //dersom meldinger er periodic, skal den ikke slettes men oppdateres med ny dato for sending
        if (message.isPeriodic()) {
            updated++;
            ContentValues values = new ContentValues();
            //sendes igjen om én uke - 7 dager * 24 timer * 60 minutter * 60 sekunder * 1000 milisekunder
            values.put(MessageDB.KEY_TIME, message.getTime() + (7 * 24* 60 * 60 * 1000));
            db.update(MessageDB.TABLE_MESSAGES, values, MessageDB.KEY_ID + " = ? ", new String[] {String.valueOf(message.getId())});
        }
        else {
            //slette medling
            deleted++;
            db.delete(MessageDB.TABLE_MESSAGES, MessageDB.KEY_ID + " = ? ", new String[] {String.valueOf(message.getId())});
        }
        db.close();
    }

    //fjerne alle "gamle" meldinger
    private void sendMessage(Message message) {
        ArrayList<String> numbers = new ArrayList<>();
        numbers.add(Integer.toString(message.getPhone()));
        String text = message.getText();
        Intent intent = new Intent(this, SendMessage.class);
        intent.putStringArrayListExtra("numbers", numbers);
        intent.putExtra("message", text);
        this.startService(intent);
        sent++;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
