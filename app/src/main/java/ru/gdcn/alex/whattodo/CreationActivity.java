package ru.gdcn.alex.whattodo;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.objects.Note;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class CreationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CreationActivity";

    private TextView header, content;

    private Note note;
    private int countCards;

    private boolean clickCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        header = findViewById(R.id.creation_note_header);
        content = findViewById(R.id.creation_note_text);
        findViewById(R.id.creation_bottom_menu_note).setSelected(true);

        initData();
        setupActionBar();
    }

    private void initData() {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализирую данные...");
        note = (Note) getIntent().getSerializableExtra("card");
        if (note == null)
            note = new Note();
        countCards = getIntent().getIntExtra("count_cards", 0);
        clickCreate = getIntent().getBooleanExtra("clickCreate", true);
        header.setText(note.getHeader());
        content.setText(note.getContent());
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализация данных завершена!");
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
        if (note.getFixed() == 1) {
            menu.getItem(0).setChecked(true);
            menu.getItem(0).getIcon().setTint(Color.parseColor("#FF2ABFF1"));
        }
        if (note.getDate() != null) {
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
            note.setFixed(0);
        } else {
            item.setChecked(true);
            item.getIcon().setTint(Color.parseColor("#FF2ABFF1"));
            note.setFixed(1);
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
    protected void onPause() {
        Log.d(TAG, TextFormer.getStartText(className) + "Сработала пауза!");
        saveData();
        super.onPause();
    }

    private void saveData() {
        Log.d(TAG, TextFormer.getStartText(className) + "Сохраняю данные...");
        note.setHeader(String.valueOf(header.getText()));
        note.setContent(String.valueOf(content.getText()));
        note.setPosition(countCards + 1);
        if (clickCreate) {
            if (String.valueOf(header.getText()).equals("") && String.valueOf(content.getText()).equals("")) {
                Log.d(TAG, TextFormer.getStartText(className) + "Пустые поля. Такую запись не добавляю!");
                return;
            }
            DBConnector.insertNote(getApplicationContext(), note);
            Log.d(TAG, TextFormer.getStartText(className) + "Данные добавлены!");
        } else {
            DBConnector.updateNote(getApplicationContext(), note);
            Log.d(TAG, TextFormer.getStartText(className) + "Данные обновлены!");
        }

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
                    setType("note");
                }
                break;
            case R.id.creation_bottom_menu_list:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Список\"!");
                if (!v.isSelected()) {
                    v.setSelected(true);
                    findViewById(R.id.creation_bottom_menu_note).setSelected(false);
                    setType("list");
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

    private void setType(String type) {
        note.setType(type);
        if (type.equals("note") && findViewById(R.id.creation_bottom_menu_list).isSelected()){
            
        }
        if (type.equals("list") && findViewById(R.id.creation_bottom_menu_note).isSelected()){

        }
    }


}
