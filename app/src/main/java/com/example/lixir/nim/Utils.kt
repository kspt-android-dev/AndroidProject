package com.example.lixir.nim

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

object Utils {
    fun AppCompatActivity.nextFragment(fragment: Fragment) {
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.gameFragmentContainer, fragment)
        fragmentTransaction.commit()
        SaveLastFragmentForReCreation.currentFragment = fragment
    }

    fun Activity.nextActivity(javaClass: Class<*>){
        this.nextActivity(javaClass, false)
    }

    fun Activity.nextActivity(javaClass: Class<*>, finish: Boolean) {
        if (finish) finish()
        val intent = Intent(this, javaClass)
        startActivity(intent)
    }
}