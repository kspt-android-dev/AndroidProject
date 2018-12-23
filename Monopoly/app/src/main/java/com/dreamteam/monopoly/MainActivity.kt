package com.dreamteam.monopoly

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dreamteam.monopoly.helpers.shakeEffect
import maes.tech.intentanim.CustomIntent
import maes.tech.intentanim.CustomIntent.customType


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var buttonPlay: Button? = null
    private var buttonExit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        hideTopBar()

        buttonPlay = findViewById(R.id.buttonPlay)
        buttonExit = findViewById(R.id.buttonExit)

        buttonPlay?.setOnClickListener(this)

        buttonExit?.setOnClickListener {
            shakeEffect(buttonExit!!)
            finish()
            System.exit(0)
        }
    }

    @Override
    override fun finish() {
        super.finish()
        CustomIntent.customType(this, getString(R.string.up_to_bottom))
    }

    override fun onResume() {
        super.onResume()
        hideTopBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideTopBar()
    }

    private fun hideTopBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    @Override
    override fun onClick(v: View) {
        //starting setup players activity
        shakeEffect(buttonPlay!!)
        intent = Intent(this, AmountOfPlayersActivity::class.java)
        startActivity(intent)
        customType(this, getString(R.string.bottom_to_up))
    }
}
