package polytech.vladislava.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static polytech.vladislava.sudoku.DBHelper.*;

public class DBConnector {

    public static void addRecord(Context context, Record record) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, record.getName());
        values.put(KEY_TIME, record.getTime());
        values.put(KEY_TIPS, record.getTips());
        database.insert(TABLE_NAME, null, values);
        database.close();
        helper.close();
    }

    public static List<Record> getAllRecords(Context context) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, KEY_TIME + " DESC");
        int indexName = cursor.getColumnIndex(KEY_NAME);
        int indexTime = cursor.getColumnIndex(KEY_TIME);
        int indexTips = cursor.getColumnIndex(KEY_TIPS);

        if (cursor.moveToFirst()) {
            List<Record> records = new ArrayList<>();
            do {
                records.add(new Record(
                        cursor.getString(indexName),
                        cursor.getString(indexTime),
                        cursor.getInt(indexTips)
                ));
            } while (cursor.moveToNext());
            database.close();
            helper.close();
            return records;
        }
        database.close();
        helper.close();
        return null;
    }

}
