package com.example.kasia.s305327mappe2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //hvis variabelen er satt til true vil utsatte meldinger sendes
    private static boolean sending = false;

    public static void setSending(boolean setValue) {
        sending = setValue;
    }

    public static boolean getSending() {
        return sending;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = new Intent();
        intent.setAction("com.example.kasia.s305327mappe2.mybroadcast");
        sendBroadcast(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences:
                Intent intent = new Intent(getApplicationContext(), ShowPreferences.class);
                startActivity(intent);
            case R.id.weeklymsg:
                Intent intent2 = new Intent(getApplicationContext(), CreateWeeklyMessage.class);
                startActivity(intent2);
        }
        return true;
    }

    public void add(View v) {
        Intent intent = new Intent(this, AddStudent.class);
        startActivity(intent);
    }

    public void list(View v) {
        Intent intent = new Intent(this, ShowList.class);
        startActivity(intent);
    }

    public void send(View v) {
        Intent intent = new Intent(this, CreateMessage.class);

        //trenges ikke - håndtert i CreateMessage
        /*ArrayList<String> numbers = new ArrayList<>();
        String[] projection = new String[] {ContactProvider.KEY_TEL_NR};
        //hent alle registrerte tlf numrene
        Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                numbers.add(cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR)));
            } while (cursor.moveToNext());
        }

        intent.putStringArrayListExtra("numbers", numbers);*/
        startActivity(intent);
    }
}
