package ru.alexandra.forum.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.alexandra.forum.objects.Answer;
import ru.alexandra.forum.objects.Theme;
import ru.alexandra.forum.objects.User;

import static ru.alexandra.forum.data.DBHelper.*;

public class DBConnector {
    private static final String TAG = "LOGGER";

    public static long insertUser(AppCompatActivity activity, User user) {
        Log.d(TAG, "Добавляем пользователя " + user.getName() + " в таблицу...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //TODO здесь будет аватар
        contentValues.put(KEY_NAME, user.getName());
        contentValues.put(KEY_PASSWORD, user.getPass());
        contentValues.put(KEY_STATUS, user.getStatus());
        contentValues.put(KEY_THEMES, user.getThemes());
        contentValues.put(KEY_ANSWERS, user.getAnswers());
        //TODO здесь будет дата создания
        long id = database.insert(TABLE_USERS, null, contentValues);
        Log.d(TAG, "Пользователь "
                + user.getName()
                + " добавлен в таблицу с id = " + id);

        database.close();
        dbHelper.close();
        return id;
    }

    public static void updateUser(AppCompatActivity activity, User user) {
        Log.d(TAG, "Обновляем пользователя " + user.getName() + "...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //TODO здесь будет аватар
        contentValues.put(KEY_NAME, user.getName());
        contentValues.put(KEY_PASSWORD, user.getPass());
        contentValues.put(KEY_STATUS, user.getStatus());
        contentValues.put(KEY_THEMES, user.getThemes());
        contentValues.put(KEY_ANSWERS, user.getAnswers());
        //TODO здесь будет дата создания
        database.update(TABLE_USERS, contentValues, KEY_ID + " = " + user.getId(), null);
        Log.d(TAG, "Пользователь " + user.getName() + " обновлен!");

        database.close();
        dbHelper.close();
    }

    public static User loadUser(Context context, String login) {
        Log.d(TAG, "Загрузка пользователя с именем " + login + "...");
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String selection = KEY_NAME + "=?";
        String[] sArgs = {login};
        Cursor cursor = database.query(TABLE_USERS,
                null,
                selection,
                sArgs,
                null,
                null,
                null);
        Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

        int indexId = cursor.getColumnIndex(KEY_ID);
        //TODO здесь будет аватар
        int indexName = cursor.getColumnIndex(KEY_NAME);
        int indexPass = cursor.getColumnIndex(KEY_PASSWORD);
        int indexStatus = cursor.getColumnIndex(KEY_STATUS);
        int indexThemes = cursor.getColumnIndex(KEY_THEMES);
        int indexAnswers = cursor.getColumnIndex(KEY_ANSWERS);
        //TODO здесь будет дата создания

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(indexId),
                    cursor.getString(indexName),
                    cursor.getString(indexPass),
                    cursor.getString(indexStatus),
                    cursor.getInt(indexThemes),
                    cursor.getInt(indexAnswers)
            );
        }

        database.close();
        dbHelper.close();

        return user;
    }

    public static User loadUser(Context context, long id) {
        Log.d(TAG, "Загрузка пользователя с id = " + id + "...");
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String selection = KEY_ID + "=?";
        String[] sArgs = {String.valueOf(id)};
        Cursor cursor = database.query(TABLE_USERS,
                null,
                selection,
                sArgs,
                null,
                null,
                null);
        Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

        int indexId = cursor.getColumnIndex(KEY_ID);
        //TODO здесь будет аватар
        int indexName = cursor.getColumnIndex(KEY_NAME);
        int indexPass = cursor.getColumnIndex(KEY_PASSWORD);
        int indexStatus = cursor.getColumnIndex(KEY_STATUS);
        int indexThemes = cursor.getColumnIndex(KEY_THEMES);
        int indexAnswers = cursor.getColumnIndex(KEY_ANSWERS);
        //TODO здесь будет дата создания

        User user = null;
        if (cursor.moveToFirst())
            user = new User(
                    cursor.getInt(indexId),
                    cursor.getString(indexName),
                    cursor.getString(indexPass),
                    cursor.getString(indexStatus),
                    cursor.getInt(indexThemes),
                    cursor.getInt(indexAnswers)
            );

        database.close();
        dbHelper.close();

        return user;
    }


    public static long insertTheme(AppCompatActivity activity, Theme theme) {
        Log.d(TAG, "Добавляем тему " + theme.getHeader() + " в таблицу...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_ID, theme.getUserId());
        contentValues.put(KEY_HEADER, theme.getHeader());
        contentValues.put(KEY_TEXT, theme.getText());
        contentValues.put(KEY_ANSWERS, theme.getAnswers());
        //TODO здесь будет дата создания
        contentValues.put(KEY_STATUS, theme.getStatus());
        long id = database.insert(TABLE_THEMES, null, contentValues);
        Log.d(TAG, "Тема "
                + theme.getHeader()
                + " добавлена в таблицу с id = " + id);

        database.close();
        dbHelper.close();
        return id;
    }

    public static void updateTheme(AppCompatActivity activity, Theme theme) {
        Log.d(TAG, "Обновляем тему " + theme.getHeader() + "...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_ID, theme.getUserId());
        contentValues.put(KEY_HEADER, theme.getHeader());
        contentValues.put(KEY_TEXT, theme.getText());
        contentValues.put(KEY_ANSWERS, theme.getAnswers());
        //TODO здесь будет дата создания
        contentValues.put(KEY_STATUS, theme.getStatus());
        database.update(TABLE_THEMES, contentValues, KEY_ID + " = " + theme.getId(), null);
        Log.d(TAG, "Тема "
                + theme.getHeader()
                + " обновлена!");

        database.close();
        dbHelper.close();
    }

    public static void deleteTheme(AppCompatActivity activity, Theme theme) {
        Log.d(TAG, "Удаляю тему " + theme.getHeader() + " из таблицы...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String where = KEY_ID + "=?";
        String[] wArgs = {String.valueOf(theme.getId())};
        database.delete(TABLE_THEMES, where, wArgs);

        database.close();
        dbHelper.close();
    }

    public static Theme loadTheme(AppCompatActivity activity, long id) {
        Log.d(TAG, "Загрузка темы с id = " + id + "...");
        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String selection = KEY_ID + "=?";
        String[] sArgs = {String.valueOf(id)};
        Cursor cursor = database.query(TABLE_THEMES,
                null,
                selection,
                sArgs,
                null,
                null,
                null);
        Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

        int indexId = cursor.getColumnIndex(KEY_ID);
        int indexUsetId = cursor.getColumnIndex(KEY_USER_ID);
        int indexHeader = cursor.getColumnIndex(KEY_HEADER);
        int indexText = cursor.getColumnIndex(KEY_TEXT);
        int indexAnswers = cursor.getColumnIndex(KEY_ANSWERS);
        //TODO здесь будет дата создания
        int indexStatus = cursor.getColumnIndex(KEY_STATUS);

        Theme theme = new Theme(
                cursor.getInt(indexId),
                cursor.getInt(indexUsetId),
                cursor.getString(indexHeader),
                cursor.getString(indexText),
                cursor.getInt(indexAnswers),
                cursor.getString(indexStatus),
                loadUser(activity, cursor.getInt(indexUsetId))
        );
        Log.d(TAG, "Считал тему!");

        database.close();
        dbHelper.close();

        return theme;
    }

    public static List<Theme> loadThemes(Context activity) {
        Log.d(TAG, "Загрузка тем...");
        List<Theme> themes = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(TABLE_THEMES,
                null,
                null,
                null,
                null,
                null,
                null);
        Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

        int indexId = cursor.getColumnIndex(KEY_ID);
        int indexUsetId = cursor.getColumnIndex(KEY_USER_ID);
        int indexHeader = cursor.getColumnIndex(KEY_HEADER);
        int indexText = cursor.getColumnIndex(KEY_TEXT);
        int indexAnswers = cursor.getColumnIndex(KEY_ANSWERS);
        //TODO здесь будет дата создания
        int indexStatus = cursor.getColumnIndex(KEY_STATUS);

        if (cursor.moveToFirst()) {
            do {
                themes.add(new Theme(
                        cursor.getInt(indexId),
                        cursor.getInt(indexUsetId),
                        cursor.getString(indexHeader),
                        cursor.getString(indexText),
                        cursor.getInt(indexAnswers),
                        cursor.getString(indexStatus),
                        loadUser(activity, cursor.getInt(indexUsetId))
                ));
            } while (cursor.moveToNext());
            Log.d(TAG, "Считал все темы!");
        }

        database.close();
        dbHelper.close();

        return themes;
    }


    public static long insertAnswer(Context activity, Answer answer) {
        Log.d(TAG, "Добавляем ответ @[ " + answer.getText() + " ]@ в таблицу...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_THEME_ID, answer.getThemeId());
        contentValues.put(KEY_USER_ID, answer.getUserId());
        contentValues.put(KEY_TEXT, answer.getText());
        //TODO здесь будет дата создания
        long id = database.insert(TABLE_ANSWERS, null, contentValues);
        Log.d(TAG, "Ответ добавлен с id = " + answer.getId());

        database.close();
        dbHelper.close();
        return id;
    }

    public static void updateAnswer(AppCompatActivity activity, Answer answer) {
        Log.d(TAG, "Обновляем ответ @[ " + answer.getText() + " ]@...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_THEME_ID, answer.getThemeId());
        contentValues.put(KEY_USER_ID, answer.getUserId());
        contentValues.put(KEY_TEXT, answer.getText());
        //TODO здесь будет дата создания
        database.update(TABLE_ANSWERS, contentValues, KEY_ID + " = " + answer.getId(), null);
        Log.d(TAG, "Ответ обновлен!");

        database.close();
        dbHelper.close();
    }

    public static List<Answer> loadAnswers(Context activity, long themeId) {
        Log.d(TAG, "Загрузка ответов...");
        List<Answer> answers = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String selection = KEY_THEME_ID + "=?";
        String[] sArgs = {String.valueOf(themeId)};
        Cursor cursor = database.query(TABLE_ANSWERS,
                null,
                selection,
                sArgs,
                null,
                null,
                null);
        Log.d(TAG, "Достал записей: " + cursor.getCount() + "!");

        int indexId = cursor.getColumnIndex(KEY_ID);
        int indexThemeId = cursor.getColumnIndex(KEY_THEME_ID);
        int indexUsetId = cursor.getColumnIndex(KEY_USER_ID);
        int indexText = cursor.getColumnIndex(KEY_TEXT);
        //TODO здесь будет дата создания

        if (cursor.moveToFirst()) {
            do {
                answers.add(new Answer(
                        cursor.getInt(indexId),
                        cursor.getInt(indexThemeId),
                        cursor.getInt(indexUsetId),
                        cursor.getString(indexText),
                        loadUser(activity, cursor.getInt(indexUsetId))
                ));
            } while (cursor.moveToNext());
            Log.d(TAG, "Считал все ответы!");
        }

        database.close();
        dbHelper.close();

        return answers;
    }
}