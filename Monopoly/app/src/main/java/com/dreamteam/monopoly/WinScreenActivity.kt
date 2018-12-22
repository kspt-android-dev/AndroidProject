package com.dreamteam.monopoly

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.view.View
import maes.tech.intentanim.CustomIntent

class WinScreenActivity : AppCompatActivity() {

    private var menuButton: Button? = null
    private var winnerNamespace: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val intent = intent
        winnerNamespace = findViewById(R.id.winerName)
        winnerNamespace!!.text = intent.getStringExtra("winnerName")

        menuButton = findViewById(R.id.buttonMenu)
        menuButton!!.setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
            CustomIntent.customType(this, "up-to-bottom")
        }



    }

}
