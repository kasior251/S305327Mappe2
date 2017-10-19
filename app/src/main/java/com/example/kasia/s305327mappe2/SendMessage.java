package com.example.kasia.s305327mappe2;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 17.10.2017.
 */

public class SendMessage extends Service {

    String message;
    ArrayList<String> receivers;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        receivers = intent.getStringArrayListExtra("numbers");
        if (receivers.get(0).equals("0")) {
            receivers.remove(0);
            String[] projection = new String[] {ContactProvider.KEY_TEL_NR};
            Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    receivers.add(cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR)));
                } while (cursor.moveToNext());
            }
        }
        message = intent.getStringExtra("message");
        SmsManager manager = SmsManager.getDefault();
        for (String s : receivers)
        {
            try {
                manager.sendTextMessage(s, null, message, null, null);
                Toast.makeText(getApplicationContext(), "Melding sendt til " + s, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Feil, melding sendt ikke til " + s, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
