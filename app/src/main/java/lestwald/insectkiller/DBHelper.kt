package lestwald.insectkiller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, private val dbName: String) : SQLiteOpenHelper(context, dbName, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table $dbName($KEY_ID integer primary key,$KEY_NAME text,$KEY_SCORE integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $dbName")
        onCreate(db)
    }

    companion object {
        const val DB_VERSION = 1
        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
        const val KEY_SCORE = "score"
    }
}

