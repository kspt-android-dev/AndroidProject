package com.dreamteam.monopoly.game.board

import com.dreamteam.monopoly.game.Data.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList
import android.support.constraint.ConstraintSet
import com.dreamteam.monopoly.R
import android.support.constraint.ConstraintLayout
import com.dreamteam.monopoly.GameActivity
import com.dreamteam.monopoly.game.Data.ValuesData
import com.dreamteam.monopoly.game.player.Order


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

    }

    private fun changeImagePlace(player: Player) {
        val currentPlayerID = player.id
        val constraintSet = ConstraintSet()
        val constraintLayout: ConstraintLayout = activity.findViewById(R.id.ConstraintLayoutScroll)
        constraintSet.clone(constraintLayout)

        val myPlayer = activity.resources.getIdentifier(activity.getString(R.string.Player) +
                currentPlayerID.toString(),  ValuesData.id, activity.packageName)
        val myId = activity.resources.getIdentifier(activity.getString(R.string.cell) +
                (player.currentPosition + 1).toString(),  ValuesData.id, activity.packageName)
        if (currentPlayerID == Order.FIRST.value || currentPlayerID == Order.THIRD.value) {
            constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
            constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
            if (currentPlayerID == Order.FIRST.value) constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
            else constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
        }
        if (currentPlayerID == Order.SECOND.value || currentPlayerID == Order.FOURTH.value) {
            constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
            constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
            if (currentPlayerID == Order.SECOND.value) constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
            else constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
        }
        constraintSet.applyTo(constraintLayout)
    }
}

