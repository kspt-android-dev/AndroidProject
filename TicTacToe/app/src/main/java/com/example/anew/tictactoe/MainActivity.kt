package com.example.anew.tictactoe

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var pref: SharedPreferences?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        val set = Intent(baseContext, Settings::class.java)
        button_set.setOnClickListener {
            startActivity(set)
        }
        val intent = Intent(baseContext, Game::class.java)
        button_main.setOnClickListener {
            if (pref?.getBoolean("sound", false)!!) {
                intent.putExtra("Flag", true)
            }
            else{
                intent.putExtra("Flag",false)
            }
            startActivity(intent)
        }
    }
}

