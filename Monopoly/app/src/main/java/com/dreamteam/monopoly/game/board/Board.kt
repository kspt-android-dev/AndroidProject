package com.dreamteam.monopoly.game.board

import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList

class Board(var gameWay: ArrayList<Cell>) {

    private val gameWayLength: Int = gameWay.size

    fun MovePlayer(newPositionIndex: Int, player: Player): Cell {
        return if (newPositionIndex > gameWayLength - 1) {
            player.earnMoney(GameData.loopMoney)
            gameWay[newPositionIndex - gameWayLength]
        } else return gameWay[newPositionIndex]
    }
}