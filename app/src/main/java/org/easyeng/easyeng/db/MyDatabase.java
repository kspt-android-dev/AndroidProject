package org.easyeng.easyeng.db;

import android.content.Context;

import org.easyeng.easyeng.db.entities.Example;
import org.easyeng.easyeng.db.entities.ExampleDAO;
import org.easyeng.easyeng.db.entities.Word;
import org.easyeng.easyeng.db.entities.WordDAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import static org.easyeng.easyeng.db.MyDatabase.DATABASE_VERSION;

@Database(entities = {Word.class, Example.class}, version = DATABASE_VERSION, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "easyeng-db";

    public abstract WordDAO wordDAO();

    public abstract ExampleDAO exampleDAO();

    private static MyDatabase mInstance;

    public static MyDatabase getInstance(Context context, String name) {
        if (mInstance == null) {
            if (name == null) name = DATABASE_NAME;
            mInstance = Room.databaseBuilder(context, MyDatabase.class, name)
                    .build();
        }
        return mInstance;
    }
}