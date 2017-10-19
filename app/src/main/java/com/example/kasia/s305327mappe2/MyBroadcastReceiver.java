package com.example.kasia.s305327mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Kasia on 18.10.2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "I broadcast receiver", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, CheckPendingMessages.class);
        context.startService(i);
    }
}
