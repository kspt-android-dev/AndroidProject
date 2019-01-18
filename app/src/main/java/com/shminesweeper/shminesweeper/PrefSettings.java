package com.shminesweeper.shminesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PrefSettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int MAX_FIELD_SIZE = 25;
    private static final int MIN_FIELD_SIZE = 2;
    private static final int MIN_NUMBER_OF_MINES = 2;
    private static final int MAX_NUMBER_OF_MINES = 208;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        setOnPreferenceListener();

        setResult(RESULT_CANCELED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister listener
        Context context = getApplicationContext();
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        prefs.unregisterOnSharedPreferenceChangeListener(this);

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
                    int fieldWidth = Integer.parseInt(sharedPreferences.getString("field_width", ""));
                    if (fieldWidth < MIN_FIELD_SIZE) fieldWidth = MIN_FIELD_SIZE;
                    if (fieldWidth > MAX_FIELD_SIZE) fieldWidth = MAX_FIELD_SIZE;
                    editor.putString("field_width", String.valueOf(fieldWidth)).apply();
                } catch (Exception e) {
                    editor.putString("field_width", "10").apply();
                }
                break;
            case "field_height":
                try {
                    int fieldHeight = Integer.parseInt(sharedPreferences.getString("field_height", ""));
                    if (fieldHeight < MIN_FIELD_SIZE) fieldHeight = MIN_FIELD_SIZE;
                    if (fieldHeight > MAX_FIELD_SIZE) fieldHeight = MAX_FIELD_SIZE;
                    editor.putString("field_height", String.valueOf(fieldHeight)).apply();
                } catch (Exception e) {
                    editor.putString("field_height", "10").apply();
                }
                break;
            case "field_mode":
                ListPreference lp = (ListPreference) findPreference("field_mode");
                int index = lp.findIndexOfValue(lp.getValue());
                sharedPreferences.edit().putInt("field_mode_index", index).apply();
                break;
        }

        // кол-во мин всегда подстраивается под параметры поля
        try {
            int numberOfMines = Integer.parseInt(sharedPreferences.getString("number_of_mines", ""));
            if (numberOfMines < MIN_NUMBER_OF_MINES) numberOfMines = MIN_NUMBER_OF_MINES;
            if (numberOfMines > MAX_NUMBER_OF_MINES) numberOfMines = MAX_NUMBER_OF_MINES;
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
