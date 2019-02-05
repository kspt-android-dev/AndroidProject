package org.easyeng.easyeng.db.entities;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ExampleDAO {

    @Query("SELECT * FROM examples WHERE id=:exampleId")
    Example getExampleById(int exampleId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertExamples(Example... example);

    @Update
    void update(Example... example);

    @Query("DELETE FROM examples WHERE id=:exampleId")
    void delete(int exampleId);

    @Query("DELETE FROM examples")
    void deleteAll();
}