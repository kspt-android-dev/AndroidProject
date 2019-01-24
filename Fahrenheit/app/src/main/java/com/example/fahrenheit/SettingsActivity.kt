package com.example.fahrenheit

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {

    val DARK_COLOR_STYLE = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        switchStyles()
        onOffMusic()
        onOffCensure()
    }

    private fun switchStyles() {
        switch_style_btn.setOnClickListener {
            if (switch_style_btn.text == "Светлый стиль") {
                constraint_settings!!.setBackgroundColor(Color.WHITE)
                switch_style_btn.setTextColor(Color.BLACK)
                on_off_music_btn.setTextColor(Color.BLACK)
                on_off_censor_btn.setTextColor(Color.BLACK)
                switch_style_btn.text = "Темный стиль"
            } else {
                constraint_settings!!.setBackgroundColor(Color.BLACK)
                switch_style_btn.setTextColor(Color.WHITE)
                on_off_music_btn.setTextColor(Color.WHITE)
                on_off_censor_btn.setTextColor(Color.WHITE)
                switch_style_btn.text = "Светлый стиль"
            }
        }
    }

    private fun onOffMusic() {
        on_off_music_btn.setOnClickListener {
            if (on_off_music_btn.text == "Выключить музыку") {
                on_off_music_btn.text = "Включить музыку"
            } else {
                on_off_music_btn.text = "Выключить музыку"
            }
        }
    }

    private fun onOffCensure() {
        on_off_censor_btn.setOnClickListener {
            if (on_off_censor_btn.text == "Включить цензуру") {
                on_off_censor_btn.text = "Выключить цензуру"
            } else {
                on_off_censor_btn.text = "Включить цензуру"
            }
        }
    }
}