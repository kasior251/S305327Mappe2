package com.example.kasia.s305327mappe2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Kasia on 10.10.2017.
 */

public class AddStudent extends AppCompatActivity{
    EditText fname;
    EditText lname;
    EditText tlf;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstudent_activity);
        fname = (EditText)findViewById(R.id.first);
        lname = (EditText)findViewById(R.id.last);
        tlf = (EditText)findViewById(R.id.telefon);
        dbHandler = new DBHandler(this);
    }

    public void save(View v) {
        Contact contact = new Contact(fname.getText().toString(), lname.getText().toString(), tlf.getText().toString());
        dbHandler.addContact(contact);
    }

}
