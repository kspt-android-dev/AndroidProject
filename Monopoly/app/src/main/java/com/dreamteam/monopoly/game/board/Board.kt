package com.dreamteam.monopoly.game.board

import android.util.Log
import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList

class Board(var gameWay: ArrayList<GameCell>) {

    private val gameWayLength: Int = gameWay.size

    fun movePlayer(newPositionIndex: Int, player: Player): Cell { // return current player's cell
        player.currentPosition = newPositionIndex
        while (player.currentPosition > gameWayLength - 1) {
            player.currentPosition -=  gameWayLength
            loopPassEvents(player)
            Log.d("###IamGoingToCellNumber", (newPositionIndex - gameWayLength).toString())
        }
        Log.d("###IamGoingToCellNumber", (newPositionIndex).toString())
        return gameWay[player.currentPosition]
    }

    private fun loopPassEvents(player: Player){
        player.earnMoney(GameData.loopMoney)
    }
}