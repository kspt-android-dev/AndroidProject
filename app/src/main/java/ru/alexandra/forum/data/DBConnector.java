package ru.alexandra.forum.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.alexandra.forum.objects.Answer;
import ru.alexandra.forum.objects.Theme;
import ru.alexandra.forum.objects.User;

import static ru.alexandra.forum.data.DBHelper.*;

public class DBConnector {
    private static final String TAG = "DBConnector";

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBConnector(Context context){
        this.dbHelper = new DBHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
        database.close();
    }

    public long insertUser(User user) {
        Log.d(TAG, "Добавляем пользователя " + user.getName() + " в таблицу...");

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, user.getName());
        contentValues.put(KEY_PASSWORD, user.getPass());
        contentValues.put(KEY_THEMES, user.getThemes());
        contentValues.put(KEY_ANSWERS, user.getAnswers());
        long id = database.insert(TABLE_USERS, null, contentValues);
        Log.d(TAG, "Пользователь "
                + user.getName()
                + " добавлен в таблицу с id = " + id);
        return id;
    }

    public User loadUser(String login) {
        Log.d(TAG, "Загрузка пользователя с именем " + login + "...");

        String selection = KEY_NAME + "=?";
        String[] sArgs = {login};
        User user = null;
        try (Cursor cursor = database.query(TABLE_USERS,
                null,
                selection,
                sArgs,
                null,
                null,
                null);
        ) {
            Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

            int indexId = cursor.getColumnIndex(KEY_ID);
            int indexName = cursor.getColumnIndex(KEY_NAME);
            int indexPass = cursor.getColumnIndex(KEY_PASSWORD);
            int indexThemes = cursor.getColumnIndex(KEY_THEMES);
            int indexAnswers = cursor.getColumnIndex(KEY_ANSWERS);

            if (cursor.moveToFirst()) {
                user = new User(
                        cursor.getInt(indexId),
                        cursor.getString(indexName),
                        cursor.getString(indexPass),
                        cursor.getInt(indexThemes),
                        cursor.getInt(indexAnswers)
                );
            }
        }
        return user;
    }

    public User loadUser(long id) {
        Log.d(TAG, "Загрузка пользователя с id = " + id + "...");

        String selection = KEY_ID + "=?";
        String[] sArgs = {String.valueOf(id)};
        User user = null;
        try (Cursor cursor = database.query(TABLE_USERS,
                null,
                selection,
                sArgs,
                null,
                null,
                null);
        ) {
            Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

            int indexId = cursor.getColumnIndex(KEY_ID);
            int indexName = cursor.getColumnIndex(KEY_NAME);
            int indexPass = cursor.getColumnIndex(KEY_PASSWORD);
            int indexThemes = cursor.getColumnIndex(KEY_THEMES);
            int indexAnswers = cursor.getColumnIndex(KEY_ANSWERS);


            if (cursor.moveToFirst())
                user = new User(
                        cursor.getInt(indexId),
                        cursor.getString(indexName),
                        cursor.getString(indexPass),
                        cursor.getInt(indexThemes),
                        cursor.getInt(indexAnswers)
                );
        }

        return user;
    }

    public long insertTheme(Theme theme) {
        Log.d(TAG, "Добавляем тему " + theme.getHeader() + " в таблицу...");

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_ID, theme.getUserId());
        contentValues.put(KEY_HEADER, theme.getHeader());
        contentValues.put(KEY_TEXT, theme.getText());
        contentValues.put(KEY_ANSWERS, theme.getAnswers());
        long id = database.insert(TABLE_THEMES, null, contentValues);
        Log.d(TAG, "Тема "
                + theme.getHeader()
                + " добавлена в таблицу с id = " + id);

        return id;
    }

    public List<Theme> loadThemes() {
        Log.d(TAG, "Загрузка тем...");
        List<Theme> themes = new ArrayList<>();

        try (Cursor cursor = database.query(TABLE_THEMES,
                null,
                null,
                null,
                null,
                null,
                KEY_ID + " DESC");
        ) {
            Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

            int indexId = cursor.getColumnIndex(KEY_ID);
            int indexUsetId = cursor.getColumnIndex(KEY_USER_ID);
            int indexHeader = cursor.getColumnIndex(KEY_HEADER);
            int indexText = cursor.getColumnIndex(KEY_TEXT);
            int indexAnswers = cursor.getColumnIndex(KEY_ANSWERS);

            if (cursor.moveToFirst()) {
                do {
                    themes.add(new Theme(
                            cursor.getInt(indexId),
                            cursor.getInt(indexUsetId),
                            cursor.getString(indexHeader),
                            cursor.getString(indexText),
                            cursor.getInt(indexAnswers),
                            loadUser(cursor.getInt(indexUsetId))
                    ));
                } while (cursor.moveToNext());
                Log.d(TAG, "Считал все темы!");
            }
        }

        return themes;
    }

    public long insertAnswer(Answer answer) {
        Log.d(TAG, "Добавляем ответ @[ " + answer.getText() + " ]@ в таблицу...");

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_THEME_ID, answer.getThemeId());
        contentValues.put(KEY_USER_ID, answer.getUserId());
        contentValues.put(KEY_TEXT, answer.getText());
        long id = database.insert(TABLE_ANSWERS, null, contentValues);
        Log.d(TAG, "Ответ добавлен с id = " + answer.getId());
        return id;
    }

    public List<Answer> loadAnswers(long themeId) {
        Log.d(TAG, "Загрузка ответов...");
        List<Answer> answers = new ArrayList<>();

        String selection = KEY_THEME_ID + "=?";
        String[] sArgs = {String.valueOf(themeId)};
        try (Cursor cursor = database.query(TABLE_ANSWERS,
                null,
                selection,
                sArgs,
                null,
                null,
                null);
        ) {
            Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

            int indexId = cursor.getColumnIndex(KEY_ID);
            int indexThemeId = cursor.getColumnIndex(KEY_THEME_ID);
            int indexUsetId = cursor.getColumnIndex(KEY_USER_ID);
            int indexText = cursor.getColumnIndex(KEY_TEXT);

            if (cursor.moveToFirst()) {
                do {
                    answers.add(new Answer(
                            cursor.getInt(indexId),
                            cursor.getInt(indexThemeId),
                            cursor.getInt(indexUsetId),
                            cursor.getString(indexText),
                            loadUser(cursor.getInt(indexUsetId))
                    ));
                } while (cursor.moveToNext());
                Log.d(TAG, "Считал все ответы!");
            }
        }
        return answers;
    }
}