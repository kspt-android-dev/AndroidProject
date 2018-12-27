package com.dreamteam.monopoly.game.board

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.LayerDrawable
import com.dreamteam.monopoly.game.data.GameData
import com.dreamteam.monopoly.game.board.cell.Cell
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.player.Player
import java.util.ArrayList
import android.support.constraint.ConstraintSet
import com.dreamteam.monopoly.R
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.widget.ImageButton
import com.dreamteam.monopoly.GameActivity
import com.dreamteam.monopoly.game.data.ValuesData
import com.dreamteam.monopoly.game.player.Order


class Board(var gameWay: ArrayList<GameCell>, val activity: GameActivity) {

    val gameWayLength: Int = gameWay.size
    private var indexOfCell: Int = 0


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

    fun changeImagePlace(player: Player) {
        val currentPlayerID = player.id
        val constraintSet = ConstraintSet()
        val constraintLayout: ConstraintLayout = activity.findViewById(R.id.ConstraintLayoutScroll)
        constraintSet.clone(constraintLayout)

        val myPlayer = activity.resources.getIdentifier(activity.getString(R.string.Player) +
                currentPlayerID.toString(), ValuesData.id, activity.packageName)
        Log.d("RESTARE", currentPlayerID.toString())
        val myId = activity.resources.getIdentifier(activity.getString(R.string.cell) +
                (player.currentPosition + 1).toString(), ValuesData.id, activity.packageName)
        Log.d("RESTARE",  (player.currentPosition + 1).toString())
        if (currentPlayerID == Order.FIRST.value || currentPlayerID == Order.THIRD.value) {
            Log.d("RESTARE", "IM IN FIRST IF")
            constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
            constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
            if (currentPlayerID == Order.FIRST.value) constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
            else constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
        }
        if (currentPlayerID == Order.SECOND.value || currentPlayerID == Order.FOURTH.value) {
            Log.d("RESTARE",  "IM IN SECOND IF")
            constraintSet.connect(myPlayer, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
            constraintSet.connect(myPlayer, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
            if (currentPlayerID == Order.SECOND.value) constraintSet.connect(myPlayer, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
            else constraintSet.connect(myPlayer, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
        }
        constraintSet.applyTo(constraintLayout)
    }

    fun createBoard(constraintLayout: ConstraintLayout, cellHeight: Int, cellWidth: Int) {
        activity.cellButtons.clear()
        indexOfCell = 0
        createCell(cellHeight, cellHeight, constraintLayout, Direction.TOP.value, Direction.TOP.value, Direction.START.value, Direction.START.value, Direction.TOP.value, Direction.TOP.value, true)

        // top side creation
        while (indexOfCell < gameWayLength / GameData.upSideIndexesModifier) {
            createCell(cellWidth, cellHeight, constraintLayout,
                    Direction.START.value, Direction.END.value, Direction.TOP.value,
                    Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)
        }
        createCell(cellHeight, cellHeight, constraintLayout,
                Direction.START.value, Direction.END.value, Direction.TOP.value,
                Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)

        // right side creation
        while (indexOfCell < gameWayLength / GameData.rightSideIndexesModifier) {
            createCell(cellHeight, cellWidth, constraintLayout,
                    Direction.START.value, Direction.START.value, Direction.TOP.value,
                    Direction.BOTTOM.value, Direction.END.value, Direction.END.value)
        }
        createCell(cellHeight, cellHeight, constraintLayout,
                Direction.START.value, Direction.START.value, Direction.TOP.value,
                Direction.BOTTOM.value, Direction.END.value, Direction.END.value)

        // bottom side creation
        while (indexOfCell < gameWayLength / GameData.bottomSideIndexesModifier) {
            createCell(cellWidth, cellHeight, constraintLayout,
                    Direction.END.value, Direction.START.value, Direction.TOP.value,
                    Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)
        }
        Log.d("board", indexOfCell.toString())
        createCell(cellHeight, cellHeight, constraintLayout,
                Direction.END.value, Direction.START.value, Direction.TOP.value,
                Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)

        // left side creation
        while (indexOfCell < gameWayLength / GameData.leftSideIndexesModifier) {
            createCell(cellHeight, cellWidth, constraintLayout,
                    Direction.START.value, Direction.START.value, Direction.BOTTOM.value,
                    Direction.TOP.value, Direction.END.value, Direction.END.value)
        }
    }

    private fun createCell(width: Int, height: Int, layout: ConstraintLayout, From1: Int, To1: Int, From2: Int, To2: Int, From3: Int, To3: Int, start: Boolean = false) {
        val ar = activity.resources.obtainTypedArray(R.array.cells)
        Log.d("CHECKCHECk", indexOfCell.toString())
        val thisButtonID = ar.getResourceId(indexOfCell, 0)

        val button = ImageButton(activity)
        button.layoutParams = ConstraintLayout.LayoutParams(width, height)
        button.id = (thisButtonID)
        button.setOnClickListener(activity.showInfoClick)
        val shape = activity.resources.getDrawable(activity.resources
                .getIdentifier(activity.getString(R.string.cellBackgroundLayer), activity.getString(R.string.drawable), activity.packageName), null) as LayerDrawable
        val bitmap = activity.resources.getDrawable(activity.resources.getIdentifier(activity.getString(R.string.cellImage) +
                (indexOfCell + 1).toString(), activity.getString(R.string.drawable), activity.packageName), null)
                as BitmapDrawable
        shape.setDrawableByLayerId(R.id.cellLogo, bitmap)
        button.background = shape
        layout.addView(button)
        activity.cellButtons.add(button)
        val constraintSet = ConstraintSet()
        constraintSet.clone(layout)
        if (start) {
            constraintSet.connect(R.id.UnderTopPartGuideline, From1, layout.id, To1, height)
            constraintSet.connect(button.id, From2, layout.id, To2, 0)
            constraintSet.connect(button.id, From3, layout.id, To3, 0)

        } else {
            val previousButtonID = ar.getResourceId(indexOfCell - 1, 0)
            constraintSet.connect(button.id, From1, previousButtonID, To1, 0)
            constraintSet.connect(button.id, From2, previousButtonID, To2, 0)
            constraintSet.connect(button.id, From3, previousButtonID, To3, 0)
        }
        constraintSet.applyTo(layout)
        activity.playerRemoveCellMark(indexOfCell)
        indexOfCell++
    }

    private enum class Direction(val value: Int) {
        LEFT(1), RIGHT(2),
        TOP(3), BOTTOM(4),
        START(6), END(7)
    }
}

