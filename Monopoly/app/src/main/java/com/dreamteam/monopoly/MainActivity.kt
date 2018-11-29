package com.dreamteam.monopoly

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.ImageButton
import android.content.pm.ActivityInfo
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button


class MainActivity : Activity(), View.OnClickListener {

    //image button
    private var buttonPlay: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        //setting the orientation to landscape
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //getting the button
        buttonPlay = findViewById(R.id.buttonPlay)

        //adding a click listener
        buttonPlay!!.setOnClickListener(this)
    }

    @Override
    override fun onClick(v: View) {
        //starting game activity
        intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
