package com.dreamteam.monopoly.game.board

import android.util.Log
import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList
import android.support.constraint.ConstraintSet
import com.dreamteam.monopoly.R
import android.support.constraint.ConstraintLayout
import com.dreamteam.monopoly.GameActivity


class Board(var gameWay: ArrayList<GameCell>, val activity: GameActivity) {

    val gameWayLength: Int = gameWay.size


    fun movePlayer(newPositionIndex: Int, player: Player): Cell {
        player.currentPosition = newPositionIndex
        while (player.currentPosition > gameWayLength - 1) {
            player.currentPosition -= gameWayLength
            loopPassEvents(player)
        }
        changeImagePlace(player)
        return gameWay[player.currentPosition]
    }

    private fun loopPassEvents(player: Player) {
        player.earnMoney(GameData.loopMoney)
    }

    fun resetField(savedPositions: ArrayList<Int>) {
        if (savedPositions.size == activity.getGameManager().players.size)
            for (i in 0 until activity.getGameManager().players.size) {
                activity.getGameManager().players[i].currentPosition = savedPositions[i]
                changeImagePlace(activity.getGameManager().players[i])
            }
        else
            Log.d("[Save]", " players setup position error")

    }

    private fun changeImagePlace(player: Player) {
        val currentPlayerID = player.id
        val constraintSet = ConstraintSet()
        val constraintLayout: ConstraintLayout = activity.findViewById(R.id.ConstraintLayout)
        constraintSet.clone(constraintLayout)

        val myPlayer = activity.resources.getIdentifier("Player$currentPlayerID", "id", activity.packageName)
        // while (player.currentPosition != player.targetPosition &&  player.currentPosition <= player.targetPosition) {
        //player.currentPosition++
        Log.d("CURR POS", player.currentPosition.toString())
        val myId = activity.resources.getIdentifier("cell${player.currentPosition + 1}", "id", activity.packageName)
        if (currentPlayerID == 1 || currentPlayerID == 3) {
            constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
            constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
            if (currentPlayerID == 1) constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
            else constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
        }
        if (currentPlayerID == 2 || currentPlayerID == 4) {
            constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
            constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
            if (currentPlayerID == 2) constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
            else constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
        }
        constraintSet.applyTo(constraintLayout)
    }
    // }
}

