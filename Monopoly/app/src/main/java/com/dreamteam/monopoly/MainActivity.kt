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
import com.dreamteam.monopoly.helpers.showToast
import com.muddzdev.styleabletoastlibrary.StyleableToast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    //image button
    private var buttonPlay: Button? = null
    private var buttonExit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        //setting the orientation to landscape
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT*/

        //getting the button
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonExit = findViewById(R.id.buttonExit)

        //adding a click listener
        buttonPlay!!.setOnClickListener(this)

        buttonExit!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
                System.exit(0)
            }
        })

    }

    @Override
    override fun onClick(v: View) {
        //starting game activity
        showToast(v, "Time to play the game!")
        intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
