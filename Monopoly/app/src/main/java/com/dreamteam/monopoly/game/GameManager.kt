package com.dreamteam.monopoly.game

import com.dreamteam.monopoly.game.GameData.startMoney
import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.player.Player

class GameManager {
    var mainBoard: Board = Board(GameData.boardGameCells)
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
        players.add(Player(name, startMoney, mainBoard))
    }

    fun addPlayers(playersData: List<String>) {
        for (name: String in playersData)
            players.add(Player(name, startMoney, mainBoard))
    }

    fun addPlayer(name: String, startMoney: Int) {
        players.add(Player(name, startMoney, mainBoard))
    }

    fun addPlayersFullData(playersData: List<Pair<String, Int>>) {
        for (data: Pair<String, Int> in playersData)
            players.add(Player(data.first, data.second, mainBoard))
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