package com.example.kasia.s305327mappe2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kasia on 10.10.2017.
 */

public class AddStudent extends AppCompatActivity{
    EditText fname;
    EditText lname;
    EditText tlf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstudent_activity);
        fname = (EditText)findViewById(R.id.first);
        lname = (EditText)findViewById(R.id.last);
        tlf = (EditText)findViewById(R.id.telefon);
    }

    public void save(View v) {
        ContentValues values = new ContentValues();
        values.put(ContactProvider.KEY_FIRSTNAME, fname.getText().toString());
        values.put(ContactProvider.KEY_LASTNAME, lname.getText().toString());
        values.put(ContactProvider.KEY_TEL_NR, Integer.parseInt(tlf.getText().toString()));
        getContentResolver().insert(ContactProvider.URI, values);
        fname.setText("");
        lname.setText("");
        tlf.setText("");
        Toast.makeText(this, "Ny kontakt lagt inn", Toast.LENGTH_SHORT).show();

    }

}
