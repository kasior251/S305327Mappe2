package com.example.kasia.s305327mappe2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this);
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

    }
}
