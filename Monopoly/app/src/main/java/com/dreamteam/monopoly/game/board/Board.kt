package com.dreamteam.monopoly.game.board

import android.app.Activity
import android.os.Handler
import android.util.Log
import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList
import android.support.constraint.ConstraintSet
import com.dreamteam.monopoly.R
import android.support.constraint.ConstraintLayout
import android.util.DisplayMetrics
import com.dreamteam.monopoly.GameActivity
import com.dreamteam.monopoly.game.GameManager


class Board(var gameWay: ArrayList<GameCell>, private val activity: Activity) {

    private val gameWayLength: Int = gameWay.size

    fun initBoard() {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val boardWidth: Int = (metrics.widthPixels * GameData.boardSizeModifier).toInt()
        val boardHeight: Int = boardWidth
        val cellWidth: Int = (boardWidth / ((gameWayLength / 4 - 2) + 2 * GameData.boardSizeModifier)).toInt()
        val cellHeight: Int = (cellWidth * GameData.boardSizeModifier).toInt()
        var index = 0

        //create corner TODO
        index++
        while (index < gameWayLength / 4 - 2) {
            // up cell create TODO
            index++
        }
        //create corner TODO
        index++
        while (index < gameWayLength / 2 - 2) {
            // right cell create TODO
            index++
        }
        //create corner TODO
        index++
        while (index < 3 * gameWayLength / 2 - 2) {
            // down cell create TODO
            index++
        }
        //create corner TODO
        index++
        while (index < gameWayLength - 2) {
            // left cell create TODO
            index++
        }
    }

    fun movePlayer(newPositionIndex: Int, player: Player): Cell {
        player.targetPosition = newPositionIndex
        while (player.targetPosition > gameWayLength - 1) {
            player.targetPosition -= gameWayLength
            loopPassEvents(player)
        }
        changeImagePlace(player)
        return gameWay[player.targetPosition]
    }

    private fun loopPassEvents(player: Player) {
        player.earnMoney(GameData.loopMoney)
    }

    private fun changeImagePlace(player: Player) {
        val gameAct: GameActivity = activity as GameActivity // return current player's cell
        val currentPlayerIndex = gameAct.getGameManager().currentPlayerIndex;
        val constraintSet = ConstraintSet()
        val constraintLayout: ConstraintLayout = activity.findViewById(R.id.ConstraintLayout)
        constraintSet.clone(constraintLayout)


        val myPlayer = activity.getResources().getIdentifier("Player${currentPlayerIndex + 1}", "id", activity.packageName)
        while (player.currentPosition != player.targetPosition) {
            val handler = Handler()
            handler.postDelayed({
                player.currentPosition++
                val myId = activity.getResources().getIdentifier("button${player.currentPosition}", "id", activity.packageName)
                if (currentPlayerIndex == 0 || currentPlayerIndex == 2) {
                    constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
                    constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
                    if (currentPlayerIndex == 0) constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
                    else constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
                }
                if (currentPlayerIndex == 1 || currentPlayerIndex == 3) {
                    constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
                    constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
                    if (currentPlayerIndex == 1) constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
                    else constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
                }
                constraintSet.applyTo(constraintLayout)
            }, 200)
        }
    }
}

