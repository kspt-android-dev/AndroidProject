package com.example.gettingthingsdone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gettingthingsdone.db.CustomApplication
import com.example.gettingthingsdone.db.entity.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private var app: CustomApplication? = null

    var root: Long = 0

    lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, mainFragment)
            .commit()
        app = application as CustomApplication
    }

    suspend fun insertNote(note: File) = withContext(Dispatchers.IO) {
        app!!.db.fileDao().insert(note)
    }

    suspend fun deleteNote(note: File) = withContext(Dispatchers.IO) {
        app!!.db.fileDao().delete(note)
    }

    suspend fun updateNote(note: File) = withContext(Dispatchers.IO) {
        app!!.db.fileDao().update(note)
    }

    suspend fun getAllByParent() = withContext(Dispatchers.IO) {
        app!!.db.fileDao().getAllByParent(root)
    }

    suspend fun getParentByChild() = withContext(Dispatchers.IO) {
        app!!.db.fileDao().getParentByChild(root)
    }

    suspend fun getAll() = withContext(Dispatchers.IO) {
        app!!.db.fileDao().getAll()
    }

    override fun onBackPressed() {
            if (root != 0L) {
                mainFragment.onBackPressed()
            } else
                super.onBackPressed()
    }
}