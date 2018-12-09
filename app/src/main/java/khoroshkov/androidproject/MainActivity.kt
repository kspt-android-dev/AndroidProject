package khoroshkov.androidproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import khoroshkov.androidproject.ui.MainActivityUI
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }
}