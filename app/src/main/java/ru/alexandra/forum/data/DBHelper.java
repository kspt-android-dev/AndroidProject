package ru.alexandra.forum.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "LOGGER";

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Forum_DB";
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_THEMES = "Themes";
    public static final String TABLE_ANSWERS = "Answers";

    public static final String KEY_ID = "_id";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STATUS = "status";
    public static final String KEY_THEMES = "themes";
    public static final String KEY_ANSWERS = "answers";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_HEADER = "header";
    public static final String KEY_TEXT = "text";
    public static final String KEY_DATE = "date";
    public static final String KEY_THEME_ID = "theme_id";


    /*

    TABLE_USERS
        id avatar name password status themes answers date

    TABLE_THEMES
        id user_id header text answers date status

    TABLE_ANSWERS
        id theme_id user_id text date

     */

    public DBHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsers = "create table " + TABLE_USERS + "(" +
                KEY_ID + " integer primary key autoincrement," +
                KEY_AVATAR + " blob," +
                KEY_NAME + " text unique not null," +
                KEY_PASSWORD + " integer," +
                KEY_STATUS + " text not null," +
                KEY_THEMES + " integer not null," +
                KEY_ANSWERS + " integer not null," +
                KEY_DATE + " integer" + ")";
        db.execSQL(sqlUsers);
        Log.d(TAG, "Создал таблицу - " + TABLE_USERS + "!");

        String sqlThemes = "create table " + TABLE_THEMES + "(" +
                KEY_ID + " integer primary key autoincrement," +
                KEY_USER_ID + " integer not null," +
                KEY_HEADER + " text not null," +
                KEY_TEXT + " text not null," +
                KEY_ANSWERS + " integer not null," +
                KEY_DATE + " integer," +
                KEY_STATUS + " text not null" + ")";
        db.execSQL(sqlThemes);
        Log.d(TAG, "Создал таблицу - " + TABLE_THEMES + "!");

        String sqlAnswers = "create table " + TABLE_ANSWERS+ "(" +
                KEY_ID + " integer primary key autoincrement," +
                KEY_THEME_ID + " integer not null," +
                KEY_USER_ID + " integer not null," +
                KEY_TEXT + " text not null," +
                KEY_DATE + " integer" + ")";
        db.execSQL(sqlAnswers);
        Log.d(TAG, "Создал таблицу - " + TABLE_ANSWERS + "!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlUsers = "drop table if exists " + TABLE_USERS;
        db.execSQL(sqlUsers);
        String sqlThemes = "drop table if exists " + TABLE_THEMES;
        db.execSQL(sqlThemes);
        String sqlAnswers = "drop table if exists " + TABLE_ANSWERS;
        db.execSQL(sqlAnswers);
        Log.d(TAG, "Обновил таблицы!");
        onCreate(db);
    }
}
