package com.dreamteam.monopoly

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class GameActivity : AppCompatActivity(), View.OnClickListener  {

    //image button
    private var cellButtons: ArrayList<Button> = ArrayList<Button>(44)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        for (i in 0..44)
        {
            cellButtons.add(findViewById(R.id.buttonPlay))
            cellButtons[cellButtons.lastIndex].setOnClickListener(this)
        }


        //adding a click listener

    }

    @Override
    override fun onClick(v: View) {
        //starting game activity
        intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
