package com.example.gettingthingsdone.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gettingthingsdone.db.dao.FileDao
import com.example.gettingthingsdone.db.entity.File


@Database(entities = [File::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fileDao(): FileDao
}