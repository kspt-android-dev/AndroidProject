package com.shminesweeper.shminesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PrefSettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        setOnPreferenceListener();

        setResult(RESULT_CANCELED);
    }

    private void setOnPreferenceListener() {
        Context context = getApplicationContext();
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        // проверяем введённые данные на валидность
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        switch (key) {
            case "field_width":
                try {
                    int fieldWidth = Integer.parseInt(sharedPreferences.getString(key, ""));
                    if (fieldWidth < 2) fieldWidth = 2;
                    if (fieldWidth > 25) fieldWidth = 25;
                    editor.putString(key, String.valueOf(fieldWidth)).apply();
                } catch (Exception e) {
                    editor.putString(key, "10").apply();
                }
                break;
            case "field_height":
                try {
                    int fieldHeight = Integer.parseInt(sharedPreferences.getString(key, ""));
                    if (fieldHeight < 2) fieldHeight = 2;
                    if (fieldHeight > 25) fieldHeight = 25;
                    editor.putString(key, String.valueOf(fieldHeight)).apply();
                } catch (Exception e) {
                    editor.putString(key, "10").apply();
                }
                break;
            case "field_mode" :
                ListPreference lp = (ListPreference) findPreference("field_mode");
                int index = lp.findIndexOfValue(lp.getValue());
                sharedPreferences.edit().putInt("field_mode_index", index).apply();
                break;
        }

        // кол-во мин всегда подстраивается под параметры поля
        try {
            int numberOfMines = Integer.parseInt(sharedPreferences.getString("number_of_mines", ""));
            if (numberOfMines < 2) numberOfMines = 2;
            if (numberOfMines > 208) numberOfMines = 208;
            if (numberOfMines >= Integer.parseInt(sharedPreferences.getString("field_width", "")) *
                    Integer.parseInt(sharedPreferences.getString("field_height", "")) )
                numberOfMines = Integer.parseInt(sharedPreferences.getString("field_width", "")) *
                        Integer.parseInt(sharedPreferences.getString("field_height", "")) - 1;
            editor.putString("number_of_mines", String.valueOf(numberOfMines)).apply();
        } catch (Exception e) {
            int numberOfMines = ( Integer.parseInt(sharedPreferences.getString("field_width", "")) *
                    Integer.parseInt(sharedPreferences.getString("field_height", "")) )/3;
            editor.putString("number_of_mines", String.valueOf(numberOfMines)).apply();
        }



        setResult(RESULT_OK);
    }
}
