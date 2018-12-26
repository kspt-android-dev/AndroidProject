package com.example.danila.minerandroid;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class DBConnector {


    static void addRecord(Context context, Record record) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_NAME, record.getName());
        values.put(DBHelper.KEY_TIME, record.getTime());
        database.insert(DBHelper.TABLE_NAME, null, values);
        database.close();
        helper.close();
    }

    static List<Record> getAllRecords(Context context) {

        final int MAX_RECORDS_SIZE = 10;

        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null,
                null, null, null, DBHelper.KEY_TIME + " DESC");

        int indexName = cursor.getColumnIndex(DBHelper.KEY_NAME);

        int indexTime = cursor.getColumnIndex(DBHelper.KEY_TIME);

        if (cursor.moveToFirst()) {
            List<Record> records = new ArrayList<>();
            do {
                records.add(new Record(
                        cursor.getString(indexName),
                        cursor.getString(indexTime)
                ));
            } while (cursor.moveToNext() && records.size() < MAX_RECORDS_SIZE);

            database.close();
            helper.close();
            cursor.close();

            Collections.sort(records);


            return records;
        }
        database.close();
        helper.close();
        return null;
    }

}
