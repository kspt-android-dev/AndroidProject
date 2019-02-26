package com.example.anew.tictactoe

import android.os.Bundle
import android.preference.PreferenceActivity


class Settings : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
    }

}
