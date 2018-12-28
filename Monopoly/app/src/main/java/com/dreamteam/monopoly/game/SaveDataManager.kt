package com.dreamteam.monopoly.game

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.dreamteam.monopoly.GameActivity
import com.dreamteam.monopoly.game.data.ValuesData

class SaveDataManager : ViewModel() {
    var isInited = false
    private lateinit var savedGameManager: GameManager

    fun save(gameManagerToSave: GameManager) {
        savedGameManager = gameManagerToSave
        isInited = true
    }

    fun restoreFromBundle(savedInstanceState: Bundle, activity: GameActivity) {
        activity.getGameManager().resetPlayersPositions(savedInstanceState.getIntegerArrayList(ValuesData.playersPositions))
        restoreSuicidePlayers(savedInstanceState.getIntegerArrayList(ValuesData.playersSuicideIds), activity)
        activity.getGameManager().currentPlayerIndex = savedInstanceState.getInt(ValuesData.currentPlayerIndex)
        restoreMoney(savedInstanceState.getIntegerArrayList(ValuesData.playerMoney), activity)
        restoreCells(savedInstanceState, activity)
        restoreActionState(savedInstanceState.getInt(ValuesData.actionState), activity)
        restoreCurrentInfo(savedInstanceState.getInt(ValuesData.currentInfo), activity)
    }

    fun restoreFromViewModel(activity: GameActivity) {
        activity.getGameManager().resetPlayersPositions(getPlayersPositionsData())
        restoreSuicidePlayers(savedGameManager.suicidePlayers, activity)
        activity.getGameManager().currentPlayerIndex = savedGameManager.currentPlayerIndex
        restoreMoney(getPlayersMoneyData(), activity)
        restoreCells(activity)
        restoreActionState(savedGameManager.actionState.state, activity)
        restoreCurrentInfo(savedGameManager.currentInfo, activity)
    }

    private fun restoreSuicidePlayers(playersToSuicide: ArrayList<Int>, activity: GameActivity) {
        activity.getGameManager().suicidePlayers.clear()
        for (i in 0 until playersToSuicide.size)
            activity.suicideAction(activity.getGameManager().getPlayerById(playersToSuicide[i]), false)
    }

    private fun restoreMoney(pMoney: ArrayList<Int>, activity: GameActivity) {
        for (i in 0 until pMoney.size) {
            activity.getGameManager().players[i].money = pMoney[i]
            activity.updPlayerMoney(activity.getGameManager().players[i])
        }
    }

    private fun restoreCells(savedInstanceState: Bundle, activity: GameActivity) {
        for (i in 0 until activity.getGameManager().players.size) {
            activity.getGameManager().players[i].restoreOwnedCells(
                    savedInstanceState.getIntegerArrayList(ValuesData.playerCells + i.toString()))
        }
    }

    private fun restoreCells(activity: GameActivity) {
        for (i in 0 until activity.getGameManager().players.size) {
            activity.getGameManager().players[i].restoreOwnedCells(getPlayersCellsData(i))
        }
    }

    private fun restoreActionState(state: Int, activity: GameActivity) {
        when (state) {
            GameManager.ActionState.BUY.state -> {
                activity.activateThrowButton(false)
                activity.reactivateBuyChoice()
            }
            GameManager.ActionState.SELL.state -> activity.reactivateSellChoice()
            GameManager.ActionState.BUY_SELL.state -> {
                activity.activateThrowButton(false)
                activity.reactivateBuyChoice()
                activity.reactivateSellChoice()
            }
        }
    }

    private fun restoreCurrentInfo(index: Int, activity: GameActivity) {
        activity.showInfo(index)
    }

    private fun getPlayersMoneyData(): ArrayList<Int> {
        val playersMoney = ArrayList<Int>(savedGameManager.players.size)
        for (p in savedGameManager.players) {
            playersMoney.add(p.money)
        }
        return playersMoney
    }

    private fun getPlayersPositionsData(): ArrayList<Int> {
        val playersPositions = ArrayList<Int>(savedGameManager.players.size)
        for (p in savedGameManager.players) {
            playersPositions.add(p.currentPosition)
        }
        return playersPositions
    }

    private fun getPlayersCellsData(index: Int): ArrayList<Int> {
        val playersCells = ArrayList<Int>(savedGameManager.players.size)
        for (i in 0 until savedGameManager.players[index].cells.size)
            playersCells.add(savedGameManager.players[index].cells[i].id.toInt())
        return playersCells
    }
}