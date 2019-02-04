package com.example.lixir.nim

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.lixir.nim.Utils.nextFragment
import com.example.lixir.nim.fragments.menu.MenuFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val tempFragment = try {
            SaveLastFragmentForReCreation.currentFragment
        } catch (e: UninitializedPropertyAccessException) {
            MenuFragment()
        }
        fragmentTransaction.replace(R.id.gameFragmentContainer, tempFragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (SaveLastFragmentForReCreation.currentFragment is MenuFragment) finish()
        this.nextFragment(MenuFragment())
    }
}