package com.dreamteam.monopoly

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.ImageButton
import android.content.pm.ActivityInfo
import android.view.View


class MainActivity : AppCompatActivity(), View.OnClickListener {

    //image button
    private var buttonPlay: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting the orientation to landscape
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        //getting the button
        buttonPlay = findViewById(R.id.buttonPlay)

        //adding a click listener
        buttonPlay!!.setOnClickListener(this)
    }

    @Override
    override fun onClick(v: View) {

        //starting game activity
        //startActivity(Intent(this, GameActivity.))
    }
}
