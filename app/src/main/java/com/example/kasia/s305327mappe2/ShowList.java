package com.example.kasia.s305327mappe2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasia on 11.10.2017.
 */

public class ShowList extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    LinearLayout layout;
    DBHandler dbHandler;
    String text;
    boolean allChecked = false;
    List<Contact> contacts;
    List<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlist_activity);
        layout = (LinearLayout) findViewById(R.id.studentlist);
        dbHandler = new DBHandler(this);
        contacts = dbHandler.findAll();
        int i = 0;
        CheckBox checkBox = new CheckBox(this);
        checkBox.setOnCheckedChangeListener(this);
        checkBox.setId(i++);
        checkBox.setText("select all");
        checkBoxes.add(checkBox);
        for (Contact c : contacts) {
            text = "ID: " + c.get_ID() + " " + c.getFirst() + " " + c.getLast() + " " + c.getPhone() + System.lineSeparator();
            checkBox = new CheckBox(this);
            //checkBox.setOnCheckedChangeListener(this);
            checkBox.setId(i++);
            checkBox.setText(text);
            checkBoxes.add(checkBox);
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
}




