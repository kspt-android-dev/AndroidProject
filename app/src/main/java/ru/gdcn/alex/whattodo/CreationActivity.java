package ru.gdcn.alex.whattodo;

import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class CreationActivity extends AppCompatActivity {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CreationActivity";
    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        setupActionBar();
//    bottomAppBar = findViewById(R.id.creation_bottom_bar);
//    setSupportActionBar(bottomAppBar);
    }


    private void setupActionBar() {
        Log.d(TAG, TextFormer.getStartText(className) + "Настраиваю ActionBar...");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.e(TAG, TextFormer.getStartText(className) + "Не удалось получить ActionBar!");
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        Log.d(TAG, TextFormer.getStartText(className) + "ActionBar настроен!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creation_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return false;
    }
}
