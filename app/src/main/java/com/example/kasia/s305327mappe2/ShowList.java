package com.example.kasia.s305327mappe2;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 11.10.2017.
 */

public class ShowList extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    LinearLayout layout;
    String text;
    boolean allChecked = false;
    List<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlist_activity);
        findAll();
    }

    private void findAll() {

        //felt som vi skal ha med i viewet
        String[] projection = new String[] {ContactProvider.KEY_ID, ContactProvider.KEY_FIRSTNAME, ContactProvider.KEY_LASTNAME, ContactProvider.KEY_TEL_NR};
        Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, null, null, null);
        List<String> contactList = new ArrayList<>();
        layout = (LinearLayout) findViewById(R.id.studentlist);
        CheckBox checkBox = new CheckBox(this);
        checkBox.setOnCheckedChangeListener(this);
        checkBox.setId(0);
        checkBox.setText("select all");
        checkBoxes.add(checkBox);
        if (cursor.moveToFirst()) {
            do{
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_ID)));
                String fname = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_FIRSTNAME));
                String lname = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_LASTNAME));
                String tlf = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR));

                text = fname + " " + lname + " "  + System.lineSeparator();
                checkBox = new CheckBox(this);
                checkBox.setId(id);
                checkBox.setText(text);
                checkBoxes.add(checkBox);

            } while (cursor.moveToNext());
        }
        LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkParams.setMargins(10, 10, 10, 10);
        checkParams.gravity = Gravity.LEFT;
        for (CheckBox cb : checkBoxes) {
            layout.addView(cb, checkParams);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!allChecked) {
            for (CheckBox cb : checkBoxes) {
                cb.setChecked(true);
                allChecked = true;
            }
        } else {
            for (CheckBox cb : checkBoxes) {
                cb.setChecked(false);
                allChecked = false;
            }

        }
    }

    public void delete(View v) {
        ArrayList<String> idNumbers = getCheckedId();

        if (!isSelected(idNumbers)) {
            return;
        }

        for (String s : idNumbers) {
            getContentResolver().delete(ContactProvider.URI, ContactProvider.KEY_ID + " = ? ",new String[]{s} );
        }
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void edit(View v) {
        ArrayList<String> idNumbers = getCheckedId();
        if (!isSelected(idNumbers)) {
            return;
        }
        for (String s : idNumbers) {
            String[] projection = new String[] {ContactProvider.KEY_ID, ContactProvider.KEY_FIRSTNAME, ContactProvider.KEY_LASTNAME, ContactProvider.KEY_TEL_NR};

            Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, ContactProvider.KEY_ID + " = ? ",
                    new String[] {s}, null);

            finish();

            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_ID));
                    String fname = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_FIRSTNAME));
                    String lname = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_LASTNAME));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR));
                    Intent i = new Intent(this, Edit.class);
                    i.putExtra("id", id);
                    i.putExtra("fname", fname);
                    i.putExtra("lname", lname);
                    i.putExtra("phone", phone);
                    startActivity(i);
                } while (cursor.moveToNext());
            }
        }
    }


    public void sendMsg(View v) {
        ArrayList<String> idNumbers = getCheckedId();
        if (!isSelected(idNumbers)) {
            return;
        }

        ArrayList<String> numbers = new ArrayList<>();
        String[] projection = new String[] {ContactProvider.KEY_TEL_NR};

        for (String s : idNumbers) {
            Cursor cursor = getContentResolver().query(ContactProvider.URI, projection, ContactProvider.KEY_ID + " = ? ",
                    new String[] {s}, null);
            if (cursor.moveToFirst()) {
                String nrTel = cursor.getString(cursor.getColumnIndex(ContactProvider.KEY_TEL_NR));
                numbers.add(nrTel);
            }
        }


        Intent intent = new Intent(this, CreateMessage.class);
        intent.putStringArrayListExtra("numbers", numbers);
        this.startActivity(intent);
    }

    //hjelpemetode for å få id nmrene over checked kontakter
    private ArrayList<String> getCheckedId() {
        ArrayList<String> idList = new ArrayList<>();
        for (CheckBox cb: checkBoxes) {
            //ta ikke med den første check-boxen
            if(cb.getId() == 0)
                continue;
            if (cb.isChecked()) {
                idList.add(Integer.toString(cb.getId()));
            }
        }
        return idList;
    }

    //returnerer false hvis ingen kontakt er valgt
    private boolean isSelected(ArrayList<String> idNumbers) {
        if (idNumbers.size() < 1) {
            Toast.makeText(this, "Velg minimum én kontakt fra lista", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}




