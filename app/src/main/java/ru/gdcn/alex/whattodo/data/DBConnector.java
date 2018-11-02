package ru.gdcn.alex.whattodo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.gdcn.alex.whattodo.customviews.Card;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

import static ru.gdcn.alex.whattodo.data.DBHelper.*;

public class DBConnector {
    private static final String TAG = "ToDO_Logger";
    private static final String className = "DBConnector";


    //TODO передавать Card
    public static void insertData(Context activity, int parentId, String header, String text,
                                  String type, String dateString, int fixed) {
        Log.d(TAG, TextFormer.getStartText(className) + "Добавляем запись в таблицу...");
        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //TODO добавить position
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PARENT_ID, parentId);
        contentValues.put(KEY_HEADER, header);
        contentValues.put(KEY_CONTENT, text);
        contentValues.put(KEY_TYPE, type);
        contentValues.put(KEY_DATE, stringToDate(dateString));
        contentValues.put(KEY_FIXED, fixed);
        long c = database.insert(TABLE_NOTES, null, contentValues);
        Log.d(TAG, TextFormer.getStartText(className) + "Размер таблицы: " + database.getPageSize() + " байт!");
        contentValues.clear();
        database.close();
        dbHelper.close();
        Log.d(TAG, TextFormer.getStartText(className) + "Запись добавлена! " + c);
    }

    private static Integer stringToDate(String dateString) {
        return null;
    }

    private static String dateToString(java.util.Date date) {
        return null;
    }

    //TODO передавать Card
    public static void updateData(Context activity, int id, int parentId, String header, String text,
                                  String type, String dateString, int fixed) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обновляю запись в таблице...");
        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //TODO добавить position
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PARENT_ID, parentId);
        contentValues.put(KEY_HEADER, header);
        contentValues.put(KEY_CONTENT, text);
        contentValues.put(KEY_TYPE, type);
        contentValues.put(KEY_DATE, stringToDate(dateString));
        contentValues.put(KEY_FIXED, fixed);
        long c = database.update(TABLE_NOTES, contentValues, KEY_ID + " = " + id, null);
        Log.d(TAG, TextFormer.getStartText(className) + "Размер таблицы: " + database.getPageSize() + " байт!");
        contentValues.clear();
        database.close();
        dbHelper.close();
        Log.d(TAG, TextFormer.getStartText(className) + "Запись обновлена! " + c);
    }

    //TODO передавать Card
    public static void deleteData(Context activity, int id, int parentId, String header, String text,
                                  String type, String dateString, int fixed){
        //TODO удаление записи из БД и всего, что лежит внутри нее
    }

    public static void clearTable(Context activity){
        Log.d(TAG, TextFormer.getStartText(className) + "Пытаюсь отчистить таблицу...");
        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_NOTES, null, null);
        database.execSQL("VACUUM;");
        Log.d(TAG, TextFormer.getStartText(className) + "Размер таблицы: " + database.getPageSize() + " байт!");
        database.close();
        dbHelper.close();
        Log.d(TAG, TextFormer.getStartText(className) + "Таблица отчищена!");
    }

    public static Collection<Card> loadData(Context activity, int parentId) {
        Log.d(TAG, TextFormer.getStartText(className) + "Пытаюсь достать данные из таблицы...");
        List<Card> cards = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //TODO добавить условие выбора по parent_id и соритровка по position
        Cursor cursor = database.query(TABLE_NOTES, null, null, null, null, null, null);
        Log.d(TAG, TextFormer.getStartText(className) + "Достал записей: " + cursor.getCount());
        int indexId = cursor.getColumnIndex(KEY_ID);
        int indexParent = cursor.getColumnIndex(KEY_PARENT_ID);
        int indexHeader = cursor.getColumnIndex(KEY_HEADER);
        int indexContent = cursor.getColumnIndex(KEY_CONTENT);
        int indexType = cursor.getColumnIndex(KEY_TYPE);
        int indexDate = cursor.getColumnIndex(KEY_DATE);
        int indexFixed = cursor.getColumnIndex(KEY_FIXED);
        if (cursor.moveToFirst()) {
            do {
                //TODO добавить position
                cards.add(new Card(
                        cursor.getInt(indexId),
                        cursor.getInt(indexParent),
                        cursor.getString(indexHeader),
                        cursor.getString(indexContent),
                        cursor.getString(indexType),
                        cursor.getString(indexDate),
                        cursor.getInt(indexFixed)
                ));
            } while (cursor.moveToNext());
            Log.d(TAG, TextFormer.getStartText(className) + "Считал все записи!");
        }
        cursor.close();
        database.close();
        dbHelper.close();
        return cards;
    }


}
