package com.dreamteam.monopoly

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.view.View
import com.dreamteam.monopoly.game.Data.ValuesData
import com.dreamteam.monopoly.helpers.makeTinyAlert
import maes.tech.intentanim.CustomIntent

class WinScreenActivity : AppCompatActivity() {

    private var menuButton: Button? = null
    private var winnerNamespace: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        hideTopBar()

        val intent = intent
        winnerNamespace = findViewById(R.id.winerName)
        if (savedInstanceState == null) {
            winnerNamespace?.text = intent.getStringExtra(ValuesData.winnerName)
            makeTinyAlert(this, resources.getString(R.string.congratulation) + winnerNamespace?.text)
        } else
            dataRestore(savedInstanceState)

        menuButton = findViewById(R.id.buttonMenu)
        menuButton?.setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            startActivity(newIntent)
            CustomIntent.customType(this, getString(R.string.up_to_bottom))
        }
    }

    @Override
    override fun finish() {
        super.finish()
        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
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

    private fun dataRestore(savedInstanceState: Bundle) {
        winnerNamespace?.text = savedInstanceState.getString(ValuesData.winnerName)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(ValuesData.winnerName, winnerNamespace!!.text.toString())
        super.onSaveInstanceState(outState)
    }
}
