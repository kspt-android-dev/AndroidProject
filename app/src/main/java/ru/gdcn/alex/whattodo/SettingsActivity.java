package ru.gdcn.alex.whattodo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();
    }

    private void setupActionBar() {
        Log.d(TAG, TextFormer.getStartText(className) + "Настраиваю ActionBar...");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.e(TAG, TextFormer.getStartText(className) + "Не удалось получить ActionBar!");
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Settings");
        Log.d(TAG, TextFormer.getStartText(className) + "ActionBar настроен!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return false;
    }
}
