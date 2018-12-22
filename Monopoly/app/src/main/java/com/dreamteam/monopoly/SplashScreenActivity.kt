package com.dreamteam.monopoly

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import gr.net.maroulis.library.EasySplashScreen
import android.view.View



class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN


        val easySplashScreenView = EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity(MainActivity::class.java)
                .withSplashTimeOut(4000)
                .withBackgroundResource(android.R.color.white)
                //.withHeaderText("Header")
                //.withFooterText("Footer")
                //.withBeforeLogoText("My cool company")
                .withLogo(R.drawable.background)
                //.withAfterLogoText("Some more details")
                .create()
        setContentView(easySplashScreenView)
    }
}
