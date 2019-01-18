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

    private lateinit var mainFragment: MainFragment
    private lateinit var noteFragment: NoteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, mainFragment)
            .commit()
        app = application as CustomApplication
    }

    fun openNote(file: File?) {
        noteFragment = NoteFragment()
        noteFragment.setNote(file)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, noteFragment)
            .addToBackStack("note_fragment")
            .commit()
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
            if (!noteFragment.onBackPressed())
                mainFragment.onBackPressed()

        } else
            if (!noteFragment.onBackPressed())
                super.onBackPressed()
    }
}