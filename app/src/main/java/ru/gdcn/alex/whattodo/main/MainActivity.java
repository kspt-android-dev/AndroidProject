package ru.gdcn.alex.whattodo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.gdcn.alex.whattodo.AboutActivity;
import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.SettingsActivity;
import ru.gdcn.alex.whattodo.creation.CreationActivity;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ToDO_Logger";
    public static final String className = "MainActivity";

    FragmentManager fragmentManager;

    NotesFragment notes;
    TrashFragment tasks;
    CalendarFragment calendarFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, TextFormer.getStartText(className) + "Создание MainActivity...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        notes = new NotesFragment();
        tasks = new TrashFragment();
        calendarFragment = new CalendarFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.main_space, notes)
                .commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.main_bottom_menu_notes:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, notes)
                                        .commit();
                                item.setChecked(true);
                                break;
                            case R.id.main_bottom_menu_trash:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, tasks)
                                        .commit();
                                item.setChecked(true);
                                DBConnector.clearTables(getApplicationContext());
                                break;
                            case R.id.main_bottom_menu_calendar:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_space, calendarFragment)
                                        .commit();
                                item.setChecked(true);
                                break;
                        }
                        return false;
                    }
                });
        Log.d(TAG, TextFormer.getStartText(className) + "Создание MainActivity завершено!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обработка нажатия на элемент выпадающего меню...");
        int id = item.getItemId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.top_right_menu_settings:
                intent.setClass(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Настройки\"!");
                return true;
            case R.id.top_right_menu_about:
                intent.setClass(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"О программе\"!");
                return true;
            case R.id.main_top_menu_search:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Поиск\"!");
                return true;
            default:
                Log.e(TAG, TextFormer.getStartText(className) + "Не найдено такой кнопки!");
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickCreate(View v){
        Log.d(TAG, TextFormer.getStartText(className) + "Обработка нажатия кнопки \"Создать\"...");
        Intent intent = new Intent(this, CreationActivity.class);
        intent.putExtra("count_cards",
                DBConnector.loadNotes(getApplicationContext()).size()); //TODO ну и костыль
        startActivity(intent);
        Log.d(TAG, TextFormer.getStartText(className) + "Обработка нажатия кнопки \"Создать\" завершено!");
    }

}
