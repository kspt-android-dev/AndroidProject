package com.dreamteam.monopoly.game.player

import android.util.Log
import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.board.cell.CellState
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellInfo
import com.dreamteam.monopoly.game.board.cell.GameCellType
import java.util.*

class Player(val name: String, startMoney: Int, val type: PlayerType, private val board: Board) {

    var currentPosition: Int = 1
    var targetPosition: Int = currentPosition
    var money: Int = startMoney
    var cells: ArrayList<GameCell> = ArrayList()
    var isActive: Boolean = false
    var id: Int = 0

    fun throwDices(): Pair<Int, Int> {
        val dices: Pair<Int, Int> = Pair((1..6).random(), (1..6).random())
        go(dices.first + dices.second)
        return dices
    }

    private fun go(steps: Int) {
        board.movePlayer(currentPosition + steps, this)
    }

    fun analyze(): PlayerMoveCondition {
        val currentCell: GameCell = board.gameWay[targetPosition] // targetPosition ?
        when (currentCell.state) {
            CellState.FREE -> return if (currentCell.info.cellType == GameCellType.COMPANY &&
                    currentCell.checkBuyCost(money)) buyOpportunity()
            else {
                if (currentCell.info.cellType == GameCellType.BANK &&
                        !decision(PlayerActions.PAY)) decision(PlayerActions.RETREAT)
                else decision(PlayerActions.STAY)
                PlayerMoveCondition.COMPLETED
            }
            CellState.OWNED -> {
                if (currentCell.owner != this)
                    if (!decision(PlayerActions.PAY)) decision(PlayerActions.RETREAT)
                return PlayerMoveCondition.COMPLETED
            }
            CellState.SLEEPING -> {
                decision(PlayerActions.STAY)
                return PlayerMoveCondition.COMPLETED
            }
        }
    }

    fun decision(action: PlayerActions): Boolean {
        Log.d("FindError", board.gameWay[currentPosition - 1].info.name)
        return when (action) {
            PlayerActions.BUY -> board.gameWay[currentPosition - 1].info.cellType == GameCellType.COMPANY &&
                    board.gameWay[currentPosition - 1].owner == null &&
                    board.gameWay[currentPosition - 1].buy(this)
            PlayerActions.PAY -> board.gameWay[currentPosition - 1].info.cellType == GameCellType.COMPANY &&
                    board.gameWay[currentPosition - 1].pay(this)
            PlayerActions.STAY -> stay()
            PlayerActions.RETREAT -> retreat()
        }
    }

    private fun stay(): Boolean {
        return true
    }

    private fun retreat(): Boolean {
        for (cell: GameCell in cells)
            cell.reset()
        return true
    }

    private fun buyOpportunity(): PlayerMoveCondition {
        return if (type == PlayerType.PERSON) PlayerMoveCondition.ACTION_EXPECTING
        else {
            decision(PlayerActions.BUY)
            PlayerMoveCondition.COMPLETED
        }
    }

    private fun checkSpecialCell(cell:GameCell){
        /*when (cell.info){
            GameCellInfo.CHANCE ->{}
        }*/
    }

    private fun markOwnedCell(newCell: GameCell) {
        board.activity.playerSetCellMark(board.gameWay.indexOf(newCell))
    }

    private fun removeMark(rmCell: GameCell) {
        board.activity.playerRemoveCellMark(board.gameWay.indexOf(rmCell))
    }

    fun earnMoney(amount: Int) {
        money += amount
    }

    fun loseMoney(amount: Int): Boolean {
        return if (money - amount >= 0) {
            money -= amount
            true
        } else false
    }

    fun addGameCell(newCell: GameCell) {
        Log.d("FindError", "GameCell bought")
        cells.add(newCell)
        markOwnedCell(newCell)
    }

    fun removeGameCell(rmCell: GameCell) {
        cells.remove(rmCell)
        removeMark(rmCell)
    }

    fun setPlayerID(int: Int) {
        id = int
    }

    private fun tryToSellEarnedCell() {
        for (i in 0 until cells.size){
            // TODO find best
        }
    }

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start
}