package com.dreamteam.monopoly.game

import android.app.Activity
import com.dreamteam.monopoly.GameActivity
import com.dreamteam.monopoly.game.GameData.startMoney
import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.player.Player
import com.dreamteam.monopoly.game.player.PlayerType

class GameManager(private val activity: Activity) {
    var mainBoard: Board = Board(GameData.boardGameCells, activity as GameActivity)
    var players: ArrayList<Player> = ArrayList()
    var currentPlayerIndex: Int = 0

    fun nextPlayerMove() {
        players[currentPlayerIndex].isActive = false
        if (currentPlayerIndex < players.size - 1) currentPlayerIndex++
        else currentPlayerIndex = 0
        players[currentPlayerIndex].isActive = true
    }

    fun getCurrentPlayer(): Player = players[currentPlayerIndex]

    fun addPlayer(name: String) {
        players.add(Player(name, startMoney, PlayerType.PERSON, mainBoard))
    }

    fun addPlayers(playersData: List<String>) {
        for (name: String in playersData)
            players.add(Player(name, startMoney,PlayerType.PERSON,  mainBoard))
    }

    fun addPlayer(name: String, startMoney: Int, type: PlayerType) {
        players.add(Player(name, startMoney, type, mainBoard))
    }

    fun addPlayersFullData(playersData: List<Pair<String, Int>>) {
        for (data: Pair<String, Int> in playersData)
            players.add(Player(data.first, data.second, PlayerType.AI, mainBoard))
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun removePlayer(name: String) {
        players.remove(players.find { p -> p.name == name })
    }

    fun removePlayer(index: Int) {
        players.removeAt(index)
    }

    fun getPlayerByName(name: String): Player? {
        return players.find { p -> p.name == name}
    }
}