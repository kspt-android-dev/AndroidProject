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

    private static final int DATABASE_VERSION = 18;
    private static final String DATABASE_NAME = "WhatToDO_DB";
    static final String TABLE_NOTES = "Notes";
    static final String TABLE_ITEMS = "Items";

    static final String KEY_ID = "_id";
    static final String KEY_POSITION = "position";
    static final String KEY_HEADER = "header";
    static final String KEY_CONTENT = "content";
    static final String KEY_TYPE = "type";
    static final String KEY_DATE = "date";
    static final String KEY_FIXED = "fixed";
    static final String KEY_DELETED = "deleted";
    static final String KEY_PARENT_ID = "parent_id";
    static final String KEY_CHECKED = "checked";

    /*

    TABLE_NOTES
        id position header content type date fixed deleted

    TABLE_ITEMS
        id parent_id position content checked

     */

    DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlNotes = "create table " + TABLE_NOTES + "(" +
                KEY_ID + " integer primary key autoincrement," +
                KEY_POSITION + " integer not null," +
                KEY_HEADER + " text," +
                KEY_CONTENT + " text," +
                KEY_TYPE + " text not null," +
                KEY_DATE + " integer," +
                KEY_FIXED + " integer not null," +
                KEY_DELETED + " integer not null" + ")";
        db.execSQL(sqlNotes);

        String sqlItems = "create table " + TABLE_ITEMS + "(" +
                KEY_ID + " integer primary key autoincrement," +
                KEY_PARENT_ID + " integer not null," +
                KEY_POSITION + " integer not null," +
                KEY_CONTENT + " text not null," +
                KEY_CHECKED + " integer not null" + ")";
        db.execSQL(sqlItems);

        Log.d(TAG, TextFormer.getStartText(className) + "Создал таблицу - " + TABLE_NOTES + "!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlNotes = "drop table if exists " + TABLE_NOTES;
        db.execSQL(sqlNotes);
        String sqlItems = "drop table if exists " + TABLE_ITEMS;
        db.execSQL(sqlItems);
        Log.d(TAG, TextFormer.getStartText(className) + "Обновил таблицу - " + TABLE_NOTES + "!");
        onCreate(db);
    }
}
