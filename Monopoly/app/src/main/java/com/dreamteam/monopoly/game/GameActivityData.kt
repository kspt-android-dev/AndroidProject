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

    fun getPlayersMoneyData(): ArrayList<Int> {
        val playersMoney = ArrayList<Int>(gameManager.players.size)
        for (p in gameManager.players) {
            playersMoney.add(p.money)
        }
        return playersMoney
    }

    fun getPlayersPositionsData(): ArrayList<Int> {
        val playersPositions = ArrayList<Int>(gameManager.players.size)
        for (p in gameManager.players) {
            playersPositions.add(p.currentPosition)
        }
        return playersPositions
    }

    fun getPlayersCellsData(index: Int): ArrayList<Int> {
        val playersCells = ArrayList<Int>(gameManager.players.size)
        for (i in 0 until gameManager.players[index].cells.size)
            playersCells.add(gameManager.players[index].cells[i].id.toInt())
        return playersCells
    }
}