package com.julia.tag.records;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecordDao {

    @Query("SELECT * FROM records ORDER BY moves ASC")
    List<Record> getAll();

    @Insert
    void insert(Record record);

    @Query("DELETE FROM records")
    void deleteAll();
}
