package com.example.kasia.s305327mappe2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by Kasia on 18.10.2017.
 */

public class ShowPreferences extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment {

        CheckBoxPreference checkBoxPreference;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            checkBoxPreference = (CheckBoxPreference) findPreference("checkbox_preference");

            checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    if (o.toString().equals("true")) {
                        MainActivity.setSending(true);
                        Toast.makeText(getContext(), "The value is " + MainActivity.getSending(), Toast.LENGTH_SHORT).show();

                    }
                    else {
                        MainActivity.setSending(false);
                        Toast.makeText(getContext(), "The value is " + MainActivity.getSending(), Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
        }
    }

}
