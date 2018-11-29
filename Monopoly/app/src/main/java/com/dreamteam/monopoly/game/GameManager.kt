package com.dreamteam.monopoly.game

import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.player.Player

class GameManager {
    var mainBoard: Board = Board(GameData.boardGameCells)
    var players: ArrayList<Player> = ArrayList()

    fun AddPlayer(name: String, startMoney: Int) {
        players.add(Player(name, startMoney, mainBoard))
    }

    fun RemovePlayer() {
        
    }
}