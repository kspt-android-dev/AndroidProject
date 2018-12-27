package com.dreamteam.monopoly

import android.arch.lifecycle.ViewModel
import com.dreamteam.monopoly.game.GameManager

class GameActivityData : ViewModel() {
    var isInited = false
    lateinit var gameManager: GameManager

    fun save(gameManagerToSave: GameManager) {
        gameManager = gameManagerToSave
        isInited = true
    }
}