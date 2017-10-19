package com.example.kasia.s305327mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import static android.app.AlarmManager.RTC_WAKEUP;

/**
 * Created by Kasia on 18.10.2017.
 */

public class CheckPendingMessages extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "In check pending messages", Toast.LENGTH_LONG).show();
        Log.d("SERVICE", "In check pending messages servive");
        java.util.Calendar calendar = Calendar.getInstance();
        Intent i = new Intent(this, GetPendingMessages.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60 * 1000, pIntent );
        return super.onStartCommand(intent, flags, startId);
    }
}
