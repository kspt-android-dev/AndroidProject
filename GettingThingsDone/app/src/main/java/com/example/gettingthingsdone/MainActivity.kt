package com.example.gettingthingsdone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gettingthingsdone.db.entity.File


class MainActivity : AppCompatActivity() {

    private var app: GTDApplication? = null

    private lateinit var mainFragment: MainFragment

    private var noteFragment: NoteFragment? = null

    lateinit var sqlEngine: SQLEngine

    var rootId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as GTDApplication
        sqlEngine = SQLEngine(app!!)
        createMainFragment()
    }

    private fun createMainFragment() {
        mainFragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container, mainFragment)
            .commit()
    }

    fun createNoteFragment(file: File?) {
        noteFragment = NoteFragment()
        noteFragment!!.setNote(file)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, noteFragment!!)
            .addToBackStack("note_fragment")
            .commit()
    }

    override fun onBackPressed() {
        if (noteFragment == null) {
            if (!mainFragment.onBackPressed())
                super.onBackPressed()
        } else {
            if (!noteFragment!!.onBackPressed())
                if (!mainFragment.onBackPressed())
                    super.onBackPressed()
        }
    }
}