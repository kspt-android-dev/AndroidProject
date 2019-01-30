package com.example.gettingthingsdone.db.dao

import androidx.room.*
import com.example.gettingthingsdone.db.entity.File


@Dao
interface FileDao {

    @Query("SELECT * FROM file WHERE idParent IN (:id)")
    fun getAllByParent(id: Long): List<File>

    @Query("SELECT * FROM file ")
    fun getAll(): List<File>

    @Insert
    fun insert(file: File)

    @Delete
    fun delete(file: File)

    @Update
    fun update(file:File)

    @Query("SELECT * FROM file WHERE id IN (:id)")
    fun getFileById(id:Long):List<File>

}