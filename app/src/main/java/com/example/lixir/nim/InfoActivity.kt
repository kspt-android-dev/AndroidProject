package com.example.lixir.nim

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.lixir.nim.Utils.nextActivity
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        nextActivity(MainActivity().javaClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val layout = info_leaner
        layout.setOnClickListener(this)
    }

    override fun onBackPressed() {
        nextActivity(MainActivity().javaClass)
    }
}
