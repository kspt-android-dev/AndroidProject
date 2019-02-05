package org.easyeng.easyeng.db.entities;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WordDAO {

    @Query("SELECT * FROM words WHERE id=:wordId")
    Word getWordById(int wordId);

    @Query("SELECT * FROM words WHERE lastTraining<:date")
    List<Word> getWordsRepeatedBefore(Date date);

    @Query("SELECT * FROM words")
    LiveData<List<Word>> getAllWords();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWords(Word... word);

    @Update
    void update(Word... word);

    @Query("DELETE FROM words WHERE id=:wordId")
    void delete(int wordId);

    @Query("DELETE FROM words")
    void deleteAll();
}