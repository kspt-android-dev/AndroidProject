package com.dreamteam.monopoly.game.board

import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList

class Board(var gameWay: ArrayList<GameCell>) {

    private val gameWayLength: Int = gameWay.size

    fun movePlayer(newPositionIndex: Int, player: Player): Cell { // return current player's cell
        return if (newPositionIndex > gameWayLength - 1) {
            player.currentPosition = newPositionIndex - gameWayLength
            loopPassEvents(player)
            gameWay[newPositionIndex - gameWayLength]
        } else {
            player.currentPosition = newPositionIndex
            return gameWay[newPositionIndex]
        }
    }

    private fun loopPassEvents(player: Player){
        player.earnMoney(GameData.loopMoney)
    }
}