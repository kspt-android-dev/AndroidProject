package com.julia.tag.records;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract RecordDao recordDao();
}
