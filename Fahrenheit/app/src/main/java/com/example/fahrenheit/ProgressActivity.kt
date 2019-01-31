package com.example.fahrenheit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
    }

    private var isLight = true

    override fun onResume() {
        super.onResume()
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        isLight = pref.getBoolean("LIGHT", false)
        isLightMode()
    }

    private fun isLightMode() {
        if (isLight) {
            constraint_progress!!.setBackgroundColor(Color.WHITE)
            progress_count_view.setTextColor(Color.BLACK)
        } else {
            constraint_progress!!.setBackgroundColor(Color.BLACK)
            progress_count_view.setTextColor(Color.WHITE)
        }
    }
}
