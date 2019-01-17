package com.example.gettingthingsdone.db

import android.app.Application
import androidx.room.Room

class CustomApplication : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        ).build()
    }

}