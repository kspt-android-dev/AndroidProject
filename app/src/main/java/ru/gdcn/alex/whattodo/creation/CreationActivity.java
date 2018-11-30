package ru.gdcn.alex.whattodo.creation;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.sqlite.core.DB;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.objects.Note;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class CreationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CreationActivity";

    private TextView header;

    private CreationManager noteManager;

    private FragmentManager fragmentManager;
    private ListFragment listFragment;
    private NoteFragment noteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        header = findViewById(R.id.creation_note_header);

        fragmentManager = getSupportFragmentManager();
        noteFragment = new NoteFragment();
        listFragment = new ListFragment();

        noteManager = new CreationManager(this);
        noteManager.init(getIntent());

        setupActionBar();
        header.setText(noteManager.getNote().getHeader());
        if (noteManager.getNote().getType().equals("note")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.creation_main_space, noteFragment)
                    .commit();
            findViewById(R.id.creation_bottom_menu_note).setSelected(true);
        }
        if (noteManager.getNote().getType().equals("list")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.creation_main_space, listFragment)
                    .commit();
            findViewById(R.id.creation_bottom_menu_list).setSelected(true);
        }
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
        if (noteManager.getNote().getFixed() == 1) {
            menu.getItem(0).setChecked(true);
            menu.getItem(0).getIcon().setTint(Color.parseColor("#FF2ABFF1"));
        }
        if (noteManager.getNote().getDate() != null) {
            menu.getItem(1).setChecked(true);
            menu.getItem(1).getIcon().setTint(Color.parseColor("#FF2ABFF1"));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обрабатываю нажатием кнопки верхнего меню...");
        int id = item.getItemId();
        switch (id) {
            case R.id.creation_top_fixed:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Fixed\"!");
                resetFixed(item);
                break;
            case R.id.creation_top_alarm:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Alarm\"!");
                resetColor(item);
//                resetAlarm(); //TODO
//                createSchedule(); //TODO
                break;
            case android.R.id.home:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Назад\"!");
                this.finish();
                break;
        }

        return false;
    }

    private void resetFixed(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
            item.getIcon().setTint(Color.parseColor("#FFFFFF"));
            noteManager.getNote().setFixed(0);
        } else {
            item.setChecked(true);
            item.getIcon().setTint(Color.parseColor("#FF2ABFF1"));
            noteManager.getNote().setFixed(1);
        }
    }

    private void resetColor(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
            item.getIcon().setTint(Color.parseColor("#FFFFFF"));
        } else {
            item.setChecked(true);
            item.getIcon().setTint(Color.parseColor("#FF2ABFF1"));
        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, TextFormer.getStartText(className) + "Сработала стоп!");
        super.onStop();
        saveData();
    }

    private void saveData() {
        Log.d(TAG, TextFormer.getStartText(className) + "Сохраняю данные...");
        noteManager.getNote().setHeader(String.valueOf(header.getText()));
        noteManager.save();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обрабатываю нажатием кнопки нижнего меню...");
        switch (v.getId()) {
            case R.id.creation_bottom_menu_add:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Доабвить\"!");
                if (v.isSelected()) {
                    v.setSelected(false);
                } else {
                    v.setSelected(true);
                    findViewById(R.id.creation_bottom_menu_other).setSelected(false);
                }
                break;
            case R.id.creation_bottom_menu_note:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Заметка\"!");
                if (!v.isSelected()) {
                    v.setSelected(true);
                    findViewById(R.id.creation_bottom_menu_list).setSelected(false);
                    noteManager.getNote().setType("note");
                    fragmentManager.beginTransaction()
                            .replace(R.id.creation_main_space, noteFragment)
                            .commit();
                }
                break;
            case R.id.creation_bottom_menu_list:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Список\"!");
                if (!v.isSelected()) {
                    v.setSelected(true);
                    findViewById(R.id.creation_bottom_menu_note).setSelected(false);
                    noteManager.getNote().setType("list");
                    fragmentManager.beginTransaction()
                            .replace(R.id.creation_main_space, listFragment)
                            .commit();
                }
                break;
            case R.id.creation_bottom_menu_other:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Другое\"!");
                if (v.isSelected()) {
                    v.setSelected(false);
                } else {
                    v.setSelected(true);
                    findViewById(R.id.creation_bottom_menu_add).setSelected(false);
                }
                break;
            default:
                Log.d(TAG, TextFormer.getStartText(className) + "Нет такой кнопки!");
                break;
        }
    }

    public CreationManager getNoteManager() {
        return noteManager;
    }
}
