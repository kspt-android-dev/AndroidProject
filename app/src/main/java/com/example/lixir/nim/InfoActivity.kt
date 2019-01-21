package com.example.lixir.nim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        finish()
        val intent = Intent(this, MainActivity().javaClass)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val layout:ConstraintLayout = info_leaner
        layout.setOnClickListener(this)
    }

    override fun onBackPressed() {
        finish()
        val intent = Intent(this, MainActivity().javaClass)
        startActivity(intent)
    }
}
