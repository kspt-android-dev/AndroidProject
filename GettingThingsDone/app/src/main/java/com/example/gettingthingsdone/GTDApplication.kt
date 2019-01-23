package com.example.gettingthingsdone

import android.app.Application
import androidx.room.Room
import com.example.gettingthingsdone.db.AppDatabase

class GTDApplication : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()
    }

}