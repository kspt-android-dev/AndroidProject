package com.dreamteam.monopoly.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.dreamteam.monopoly.R


class GameFragment1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        val v = inflater.inflate(R.layout.activity_game_fragment1, null)

        return v
    }
}