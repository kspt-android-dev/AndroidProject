package com.dreamteam.monopoly.game

import com.dreamteam.monopoly.game.GameData.startMoney
import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.player.Player

class GameManager {
    var mainBoard: Board = Board(GameData.boardGameCells)
    var players: ArrayList<Player> = ArrayList()
    var currentPlayerIndex: Int = 0

    fun NextMove() {
        players[currentPlayerIndex].isActive = false
        if (currentPlayerIndex < players.size - 1) currentPlayerIndex++
        else currentPlayerIndex = 0
        players[currentPlayerIndex].isActive = true
    }

    fun getCurrentPlayer(): Player = players[currentPlayerIndex]

    fun AddPlayer(name: String) {
        players.add(Player(name, startMoney, mainBoard))
    }

    fun AddPlayers(playersData: List<String>) {
        for (name: String in playersData)
            players.add(Player(name, startMoney, mainBoard))
    }

    fun AddPlayer(name: String, startMoney: Int) {
        players.add(Player(name, startMoney, mainBoard))
    }

    fun AddPlayersFullData(playersData: List<Pair<String, Int>>) {
        for (data: Pair<String, Int> in playersData)
            players.add(Player(data.first, data.second, mainBoard))
    }

    fun RemovePlayer(name: String) {
        players.remove(players.find { p -> p.name == name })
    }

    fun RemovePlayer(index: Int) {
        players.removeAt(index)
    }
}