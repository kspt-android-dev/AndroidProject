package com.dreamteam.monopoly.game.board

import android.app.Activity
import android.util.Log
import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList
import android.support.constraint.ConstraintSet
import com.dreamteam.monopoly.R
import android.support.constraint.ConstraintLayout


class Board(var gameWay: ArrayList<GameCell>) {

    private val gameWayLength: Int = gameWay.size

    fun movePlayer(newPositionIndex: Int, player: Player , activity: Activity): Cell { // return current player's cell
        player.currentPosition = newPositionIndex
        while (player.currentPosition > gameWayLength - 1) {
            player.currentPosition -=  gameWayLength
            loopPassEvents(player)
            Log.d("###IamGoingToCellNumber", (newPositionIndex - gameWayLength).toString())
        }
        val constraintSet = ConstraintSet()
        val constraintLayout : ConstraintLayout = activity.findViewById(R.id.ConstraintLayout)
        val myId = activity.getResources().getIdentifier("button${player.currentPosition}", "id", activity.packageName)
        constraintSet.clone(constraintLayout)
        constraintSet.connect(R.id.Player1, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
        constraintSet.connect(R.id.Player1, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
        constraintSet.connect(R.id.Player1, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
        constraintSet.applyTo(constraintLayout)
        Log.d("###IamGoingToCellNumber", (newPositionIndex).toString())
        return gameWay[player.currentPosition]
    }

    private fun loopPassEvents(player: Player){
        player.earnMoney(GameData.loopMoney)
    }
}