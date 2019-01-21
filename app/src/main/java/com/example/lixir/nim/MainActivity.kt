package com.example.lixir.nim

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try{
            setContentView(R.layout.activity_fullscreen)
        } catch (e: Exception){
            Log.e("onCreateView", e.toString())
            throw e
        }
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val tempFragment = try {
            SaveFragment.currentFragment
        } catch (e: UninitializedPropertyAccessException){
            MenuFragment()
        }
        fragmentTransaction.replace(R.id.gameFragmentContainer, tempFragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (SaveFragment.currentFragment is MenuFragment) finish()
        this.nextFragment(MenuFragment())
    }

    fun nextFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.gameFragmentContainer, fragment)
        fragmentTransaction.commit()
        SaveFragment.currentFragment = fragment
    }

    fun nextActivity() {
        finish()
        val intent = Intent(this, InfoActivity().javaClass)
        startActivity(intent)
    }

}