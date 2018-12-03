package com.dreamteam.monopoly.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreamteam.monopoly.R

class GameFragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        val v = inflater.inflate(R.layout.activity_game_fragment1, null)

        return v
    }
}