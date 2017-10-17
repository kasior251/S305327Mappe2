package com.example.kasia.s305327mappe2;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kasia on 17.10.2017.
 */

public class Edit extends AppCompatActivity {

    EditText fname, lname, phone;
    String id, newfName, newlName;
    int newPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        phone = (EditText) findViewById(R.id.phone);
        id = getIntent().getStringExtra("id");
        fname.setText(getIntent().getStringExtra("fname"));
        lname.setText(getIntent().getStringExtra("lname"));
        phone.setText(getIntent().getStringExtra("phone"));
    }

    public void save(View v) {
        newfName = ((EditText) findViewById(R.id.fname)).getText().toString();
        newlName = ((EditText) findViewById(R.id.lname)).getText().toString();
        try {
            newPhone = Integer.parseInt(((EditText) findViewById(R.id.phone)).getText().toString());
        } catch (java.lang.NumberFormatException e) {

            //endre til en pop-up vindu?
            Toast.makeText(this, "Feil format på tlf nummer", Toast.LENGTH_SHORT).show();
            phone.setText("");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(ContactProvider.KEY_FIRSTNAME, newfName);
        values.put(ContactProvider.KEY_LASTNAME, newlName);
        values.put(ContactProvider.KEY_TEL_NR, newPhone);

        getContentResolver().update(ContactProvider.URI, values, ContactProvider.KEY_ID + " = ? " , new String[] {id});
        finish();
    }

    public void cancel(View v) {
        finish();
    }
}
