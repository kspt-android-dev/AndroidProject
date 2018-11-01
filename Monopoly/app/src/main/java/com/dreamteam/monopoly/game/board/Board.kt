package com.dreamteam.monopoly.game.board

import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList

class Board(var gameWay: ArrayList<GameCell>) {

    private val gameWayLength: Int = gameWay.size

    fun movePlayer(newPositionIndex: Int, player: Player): Cell {
        return if (newPositionIndex > gameWayLength - 1) {
            player.currentPosition = newPositionIndex - gameWayLength
            player.earnMoney(GameData.loopMoney)
            gameWay[newPositionIndex - gameWayLength]
        } else {
            player.currentPosition = newPositionIndex
            return gameWay[newPositionIndex]
        }
    }
}