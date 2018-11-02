package ru.gdcn.alex.whattodo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ru.gdcn.alex.whattodo.customviews.Card;
import ru.gdcn.alex.whattodo.data.DBConnector;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class CreationActivity extends AppCompatActivity {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "CreationActivity";

    private TextView header, content;

    private Card note;

    private boolean clickCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        header = findViewById(R.id.creation_note_header);
        content = findViewById(R.id.creation_note_text);
        initData();
        setupActionBar();
        header.setText(note.getHeader());
        content.setText(note.getContent());
    }

    private void initData() {
        Log.d(TAG, TextFormer.getStartText(className) + "Инициализирую данные...");
        note = (Card) getIntent().getSerializableExtra("card");
        if (note == null)
            note = new Card();
        clickCreate = getIntent().getBooleanExtra("clickCreate", true);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обрабатываю нажатием кнопки верхнего меню...");
        int id = item.getItemId();
        switch (id) {
            case R.id.creation_top_checkable:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Checkable\"!");
//                resetCheckable(); //TODO
                break;
            case R.id.creation_top_fixed:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Fixed\"!");
//                resetFixed(); //TODO
                break;
            case R.id.creation_top_alarm:
                Log.d(TAG, TextFormer.getStartText(className) + "Нажата кнопка \"Alarm\"!");
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

    @Override
    protected void onPause() {
        Log.d(TAG, TextFormer.getStartText(className) + "Сработала пауза!");
        note.setHeader(String.valueOf(header.getText()));
        note.setContent(String.valueOf(content.getText()));
        saveData();
        super.onPause();
    }

    //TODO понять как задавать posotion
    private void saveData() {
        Log.d(TAG, TextFormer.getStartText(className) + "Сохраняю данные...");
        if (clickCreate) {
            if (String.valueOf(header.getText()).equals("") && String.valueOf(content.getText()).equals("")) {
                Log.d(TAG, TextFormer.getStartText(className) + "Пустые поля. Такую запись не добавляю!");
                return;
            }
            DBConnector.insertData(getApplicationContext(), note);
            Log.d(TAG, TextFormer.getStartText(className) + "Данные добавлены!");
        } else {
            DBConnector.updateData(getApplicationContext(), note);
            Log.d(TAG, TextFormer.getStartText(className) + "Данные обновлены!");
        }

    }
}
