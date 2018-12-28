package polytech.vladislava.sudoku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static polytech.vladislava.sudoku.DBHelper.*;

class DBConnector {

    private DBHelper helper;
    private SQLiteDatabase database;

    DBConnector(Context context) {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }

    public void addRecord(Record record) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, record.getName());
        values.put(KEY_TIME, record.getTime());
        values.put(KEY_TIPS, record.getTips());
        database.insert(TABLE_NAME, null, values);
    }

    public List<Record> getAllRecords() {
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
            cursor.close();
            return records;
        }
        cursor.close();
        return null;
    }

    void close() {
        helper.close();
        database.close();
    }
}
