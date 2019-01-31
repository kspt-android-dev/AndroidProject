package com.example.fahrenheit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        onOffMusic()
        onOffCensure()
    }

    override fun onResume() {
        super.onResume()
        isLightMode()
        isLightModeClick()
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        val isMusicable = pref.getBoolean("MUSIC", true)
        val isCensor = pref.getBoolean("CENSORSHIP", true)
        if (isMusicable)
            on_off_music_btn.text = "Выключить музыку"
        else
            on_off_music_btn.text = "Включить музыку"
        if (isCensor)
            on_off_censor_btn.text = "Выключить цензуру"
        else
            on_off_censor_btn.text = "Включить цензуру"
    }

    private fun isLightMode() {
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        val isLight = pref.getBoolean("LIGHT", false)
        if (isLight) {
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

    private fun isLightModeClick() {
        switch_style_btn.setOnClickListener {
            val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
            val isLight = pref.getBoolean("LIGHT", true)
            if (isLight) {
                constraint_settings!!.setBackgroundColor(Color.BLACK)
                switch_style_btn.setTextColor(Color.WHITE)
                on_off_music_btn.setTextColor(Color.WHITE)
                on_off_censor_btn.setTextColor(Color.WHITE)
                switch_style_btn.text = "Светлый стиль"
                pref.edit().putBoolean("LIGHT", false).apply()
            } else {
                constraint_settings!!.setBackgroundColor(Color.WHITE)
                switch_style_btn.setTextColor(Color.BLACK)
                on_off_music_btn.setTextColor(Color.BLACK)
                on_off_censor_btn.setTextColor(Color.BLACK)
                switch_style_btn.text = "Темный стиль"
                pref.edit().putBoolean("LIGHT", true).apply()
            }
        }
    }

    private fun onOffMusic() {
        on_off_music_btn.setOnClickListener {
            val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
            val isMusicale = pref.getBoolean("MUSIC", true)
            if (isMusicale) {
                on_off_music_btn.text = "Включить музыку"
                pref.edit().putBoolean("MUSIC", false).apply()
            } else {
                on_off_music_btn.text = "Выключить музыку"
                pref.edit().putBoolean("MUSIC", true).apply()
            }
        }
    }

    private fun onOffCensure() {
        on_off_censor_btn.setOnClickListener {
            val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
            val isCensor = pref.getBoolean("CENSORSHIP", true)
            if (!isCensor) {
                on_off_censor_btn.text = "Выключить цензуру"
                pref.edit().putBoolean("CENSORSHIP", true).apply()
            } else {
                on_off_censor_btn.text = "Включить цензуру"
                pref.edit().putBoolean("CENSORSHIP", false).apply()
            }
        }
    }

}