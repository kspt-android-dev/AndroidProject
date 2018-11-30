package ru.gdcn.alex.whattodo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.gdcn.alex.whattodo.objects.Item;
import ru.gdcn.alex.whattodo.objects.Note;
import ru.gdcn.alex.whattodo.utilities.TextFormer;

import static ru.gdcn.alex.whattodo.data.DBHelper.*;

public class DBConnector {
    private static final String TAG = "ToDO_Logger";
    private static final String className = "DBConnector";

    public static long insertNote(Context activity, Note note) {
        Log.d(TAG, TextFormer.getStartText(className) + "Добавляем запись в таблицу...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
//        contentValues.put(KEY_ID, note.getId());
        contentValues.put(KEY_POSITION, note.getPosition());
        contentValues.put(KEY_HEADER, note.getHeader());
        contentValues.put(KEY_CONTENT, note.getContent());
        contentValues.put(KEY_TYPE, note.getType());
        contentValues.put(KEY_DATE, stringToDate(note.getDate()));
        contentValues.put(KEY_FIXED, note.getFixed());
        contentValues.put(KEY_DELETED, note.getDeleted());
        long id = database.insert(TABLE_NOTES, null, contentValues);

        Log.d(TAG, TextFormer.getStartText(className) + "Размер базы данных: " + database.getPageSize() + " байт!" + id);

        contentValues.clear();
        database.close();
        dbHelper.close();
        return id;
    }

    public static void updateNote(Context activity, Note note) {
        Log.d(TAG, TextFormer.getStartText(className) + "Обновляю запись в таблице...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_POSITION, note.getPosition());
        contentValues.put(KEY_HEADER, note.getHeader());
        contentValues.put(KEY_CONTENT, note.getContent());
        contentValues.put(KEY_TYPE, note.getType());
        contentValues.put(KEY_DATE, stringToDate(note.getDate()));
        contentValues.put(KEY_FIXED, note.getFixed());
        contentValues.put(KEY_DELETED, note.getDeleted());
        database.update(TABLE_NOTES, contentValues, KEY_ID + " = " + note.getId(), null);

        Log.d(TAG, TextFormer.getStartText(className) + "Размер базы данных: " + database.getPageSize() + " байт!");

        contentValues.clear();
        database.close();
        dbHelper.close();
    }

    public static void deleteNote(Context activity, Note note){
        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Log.d(TAG, TextFormer.getStartText(className) + "Удаляю запись из таблицы...");
        database.delete(TABLE_NOTES, KEY_ID + " = " + note.getId(), null);

        Log.d(TAG, TextFormer.getStartText(className) + "Удаляю пункты данной записи из таблицы...");
        String where = KEY_PARENT_ID + "=?";
        String[] wArgs = {String.valueOf(note.getId())};
        database.delete(TABLE_ITEMS, where, wArgs);

        Log.d(TAG, TextFormer.getStartText(className) + "Размер базы данных: " + database.getPageSize() + " байт!");

        database.close();
        dbHelper.close();

    }

    public static void insertItem(Context activity, Item item){
        Log.d(TAG, TextFormer.getStartText(className) + "Добавляем пункт в таблицу...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PARENT_ID, item.getParentId());
        contentValues.put(KEY_POSITION, item.getPosition());
        contentValues.put(KEY_CONTENT, item.getContent());
        contentValues.put(KEY_CHECKED, item.getChecked());
        database.insert(TABLE_ITEMS, null, contentValues);

        Log.d(TAG, TextFormer.getStartText(className) + "Размер базы данных: " + database.getPageSize() + " байт!");

        contentValues.clear();
        database.close();
        dbHelper.close();
    }

    public static void updateItem(Context activity, Item item){
        Log.d(TAG, TextFormer.getStartText(className) + "Обновляю пункт в таблице...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PARENT_ID, item.getParentId());
        contentValues.put(KEY_POSITION, item.getPosition());
        contentValues.put(KEY_CONTENT, item.getContent());
        contentValues.put(KEY_CHECKED, item.getChecked());
        database.update(TABLE_ITEMS, contentValues, KEY_ID + " = " + item.getId(), null);

        Log.d(TAG, TextFormer.getStartText(className) + "Размер базы данных: " + database.getPageSize() + " байт!");

        contentValues.clear();
        database.close();
        dbHelper.close();
    }

    public static void deleteItem(Context activity, Item item){
        Log.d(TAG, TextFormer.getStartText(className) + "Удаляю пункт из таблицы...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String where = KEY_ID + "=?";
        String[] wArgs = {String.valueOf(item.getId())};
        database.delete(TABLE_ITEMS, where, wArgs);

        Log.d(TAG, TextFormer.getStartText(className) + "Размер базы данных: " + database.getPageSize() + " байт!");

        database.close();
        dbHelper.close();
    }

    public static Collection<Item> loadItems(Context activity, long parentId){
        Log.d(TAG, TextFormer.getStartText(className) + "Пытаюсь достать данные из таблицы...");
        List<Item> items = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String selection = KEY_PARENT_ID + "=?";
        String[] sArgs = {String.valueOf(parentId)};

        Cursor cursor = database.query(TABLE_ITEMS,
                null,
                selection,
                sArgs,
                null,
                null,
                KEY_POSITION + " ASC");
        Log.d(TAG, TextFormer.getStartText(className) + "Достал записей: " + cursor.getCount());

        int indexId = cursor.getColumnIndex(KEY_ID);
        int indexParentId = cursor.getColumnIndex(KEY_PARENT_ID);
        int indexPosition = cursor.getColumnIndex(KEY_POSITION);
        int indexContent = cursor.getColumnIndex(KEY_CONTENT);
        int indexChecked = cursor.getColumnIndex(KEY_CHECKED);

        if (cursor.moveToFirst()) {
            do {
                items.add(new Item(
                        cursor.getInt(indexId),
                        cursor.getInt(indexParentId),
                        cursor.getInt(indexPosition),
                        cursor.getString(indexContent),
                        cursor.getInt(indexChecked)
                ));
            } while (cursor.moveToNext());
            Log.d(TAG, TextFormer.getStartText(className) + "Считал все записи!");
        }

        cursor.close();
        database.close();
        dbHelper.close();
        return items;
    }

    public static List<Note> loadNotes(Context activity) {
        Log.d(TAG, TextFormer.getStartText(className) + "Пытаюсь достать данные из таблицы...");
        List<Note> notes = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

//        String selection = KEY_PARENT_ID + "=?";
//        String[] sArgs = {String.valueOf(parentId)};

        Cursor cursor = database.query(TABLE_NOTES,
                null,
                null,
                null,
                null,
                null,
                KEY_POSITION + " ASC");
        Log.d(TAG, TextFormer.getStartText(className) + "Достал записей: " + cursor.getCount());

        int indexId = cursor.getColumnIndex(KEY_ID);
        int indexPosition = cursor.getColumnIndex(KEY_POSITION);
        int indexHeader = cursor.getColumnIndex(KEY_HEADER);
        int indexContent = cursor.getColumnIndex(KEY_CONTENT);
        int indexType = cursor.getColumnIndex(KEY_TYPE);
        int indexDate = cursor.getColumnIndex(KEY_DATE);
        int indexFixed = cursor.getColumnIndex(KEY_FIXED);
        int indexDeleted = cursor.getColumnIndex(KEY_DELETED);

        if (cursor.moveToFirst()) {
            do {
                notes.add(new Note(
                        cursor.getInt(indexId),
                        cursor.getInt(indexPosition),
                        cursor.getString(indexHeader),
                        cursor.getString(indexContent),
                        cursor.getString(indexType),
                        cursor.getString(indexDate),
                        cursor.getInt(indexFixed),
                        cursor.getInt(indexDeleted)
                ));
            } while (cursor.moveToNext());
            Log.d(TAG, TextFormer.getStartText(className) + "Считал все записи!");
        }

        cursor.close();
        database.close();
        dbHelper.close();
        return notes;
    }

    public static void clearTables(Context activity){
        Log.d(TAG, TextFormer.getStartText(className) + "Пытаюсь отчистить таблицу...");

        DBHelper dbHelper = new DBHelper(activity);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_NOTES, null, null);
        database.execSQL("VACUUM;");
        database.delete(TABLE_ITEMS, null, null);
        database.execSQL("VACUUM;");
        Log.d(TAG, TextFormer.getStartText(className) + "Размер базы данных: " + database.getPageSize() + " байт!");
        database.close();
        dbHelper.close();

        Log.d(TAG, TextFormer.getStartText(className) + "Таблица отчищена!");
    }

    private static Integer stringToDate(String dateString) {
        return null;
    }

    private static String dateToString(java.util.Date date) {
        return null;
    }
}
