package com.dreamteam.monopoly

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_win_screen.*
import maes.tech.intentanim.CustomIntent

class WinScreenActivity : AppCompatActivity() {

    var menuButton: Button? = null
    var winnerNamespace: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_screen)

        val intent = intent
        winnerNamespace!!.findViewById<TextView>(R.id.winnerName)
        winnerNamespace!!.text = intent.getStringExtra("winnerName")

        menuButton!!.findViewById<Button>(R.id.buttonMenu)
        menuButton!!.setOnClickListener { view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "up-to-bottom")
        }



    }

}
