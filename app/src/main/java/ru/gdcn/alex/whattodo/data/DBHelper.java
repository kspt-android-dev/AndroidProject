package ru.gdcn.alex.whattodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import ru.gdcn.alex.whattodo.utilities.TextFormer;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "ToDO_Logger";
    private static final String className = "DBHelper";

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "WhatToDO_DB";
    public static final String TABLE_NOTES = "Notes";

    public static final String KEY_ID = "_id";
    public static final String KEY_PARENT_ID = "parent_id";
    public static final String KEY_POSITION = "position";
    public static final String KEY_HEADER = "header";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DATE = "date";
    public static final String KEY_FIXED = "fixed";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NOTES+ "(" +
                KEY_ID + " integer primary key," +
                KEY_POSITION + "integer not null," +
                KEY_PARENT_ID + " integer not null," +
                KEY_HEADER + " text," +
                KEY_CONTENT + " text," +
                KEY_TYPE + " text not null," +
                KEY_DATE + " numeric," +
                KEY_FIXED + " integer not null" + ")";
        db.execSQL(sql);
        Log.d(TAG, TextFormer.getStartText(className) + "Создал таблицу - " + TABLE_NOTES + "!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABLE_NOTES;
        db.execSQL(sql);
        Log.d(TAG, TextFormer.getStartText(className) + "Обновил таблицу - " + TABLE_NOTES + "!");
        onCreate(db);
    }
}
