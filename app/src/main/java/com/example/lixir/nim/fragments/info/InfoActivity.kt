package com.example.lixir.nim.fragments.info

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.lixir.nim.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val layout = info_leaner
        layout.setOnClickListener(this)
    }

    override fun onBackPressed() {
        finish()
    }
}
