package com.julia.tag.records;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "records")
public class Record {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public int moves;
}
