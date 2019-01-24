package com.example.fahrenheit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        startTheGame()
        gameProgress()
        settings()
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
