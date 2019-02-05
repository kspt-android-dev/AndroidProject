package org.easyeng.easyeng.db.entities;

import com.google.firebase.database.annotations.NotNull;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "examples")
public class Example {

    @PrimaryKey
    private int id;
    @NotNull
    private String english;
    @NotNull
    private String russian;

    public Example(int id, @NotNull String english, @NotNull String russian) {
        this.id = id;
        this.english = english;
        this.russian = russian;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getEnglish() {
        return english;
    }

    @NotNull
    public String getRussian() {
        return russian;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Example) {
            final Example other = (Example) obj;

            return id == other.id
                    && english.equals(other.english)
                    && russian.equals(other.russian);
        }
        return false;
    }
}
