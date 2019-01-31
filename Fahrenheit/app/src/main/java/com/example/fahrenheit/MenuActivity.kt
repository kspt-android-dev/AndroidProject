package com.example.fahrenheit

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {

    private var isLight = true

    override fun onResume() {
        super.onResume()
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        isLight = pref.getBoolean("LIGHT", false)
        isLightMode()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        startTheGame()
        gameProgress()
        settings()
    }

    private fun isLightMode() {
        if (isLight) {
            constraint_menu!!.setBackgroundColor(Color.WHITE)
            start_game_btn.setTextColor(Color.BLACK)
            progress_game_btn.setTextColor(Color.BLACK)
            settings_btn.setTextColor(Color.BLACK)
        } else {
            constraint_menu!!.setBackgroundColor(Color.BLACK)
            start_game_btn.setTextColor(Color.WHITE)
            progress_game_btn.setTextColor(Color.WHITE)
            settings_btn.setTextColor(Color.WHITE)
        }
    }

    private fun gameProgress() {
        progress_game_btn.setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))

        }
    }

    private fun settings() {
        settings_btn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun startTheGame() {
        start_game_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
