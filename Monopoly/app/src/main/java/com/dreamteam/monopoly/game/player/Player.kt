package com.dreamteam.monopoly.game.player

import android.util.Log
import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.board.cell.CellState
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellInfo
import com.dreamteam.monopoly.game.board.cell.GameCellType
import java.util.*

class Player(val name: String, startMoney: Int, val type: PlayerType, private val board: Board) {

    var currentPosition: Int = 0
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
        val currentCell: GameCell = board.gameWay[currentPosition]
        Log.d ("TAG",board.gameWay[currentPosition].info.name )
        Log.d ("TAG",currentPosition.toString() )
        when (currentCell.state) {
            CellState.FREE -> return if (currentCell.info.cellType == GameCellType.COMPANY &&
                    currentCell.checkBuyCost(money)) {
                buyOpportunity()
            } else if (currentCell.info.cellType == GameCellType.CHANCE)
                chanceResult(currentCell)
            else {
                if ((currentCell.info.cellType == GameCellType.BANK) &&
                        !decision(PlayerActions.PAY))
                    if (!tryToSellEarnedCell(currentCell.info.cost.costCharge)) decision(PlayerActions.RETREAT)
                    else decision(PlayerActions.PAY)
                PlayerMoveCondition.COMPLETED
            }
            CellState.OWNED -> {
                if (currentCell.owner != this)
                    if (!decision(PlayerActions.PAY))
                        if (!tryToSellEarnedCell(currentCell.info.cost.costCharge))
                            decision(PlayerActions.RETREAT)
                        else decision(PlayerActions.PAY)
                return PlayerMoveCondition.COMPLETED
            }
            CellState.SLEEPING -> {
                decision(PlayerActions.STAY)
                return PlayerMoveCondition.COMPLETED
            }
        }
    }

    fun decision(action: PlayerActions): Boolean {
        Log.d("FindError", board.gameWay[currentPosition ].info.name)
        return when (action) {
            PlayerActions.BUY -> board.gameWay[currentPosition ].info.cellType == GameCellType.COMPANY &&
                    board.gameWay[currentPosition ].owner == null &&
                    board.gameWay[currentPosition ].buy(this)
            PlayerActions.PAY -> if (board.gameWay[currentPosition ].info.cellType == GameCellType.COMPANY) board.gameWay[currentPosition ].pay(this)
                    else loseMoney(board.gameWay[currentPosition ].info.cost.costCharge)
            PlayerActions.STAY -> stay()
            PlayerActions.RETREAT -> retreat()
        }
    }

    private fun stay(): Boolean {
        return true
    }

    private fun retreat(): Boolean {
        while (cells.size > 0)
            cells.last().reset()
        board.activity.getGameManager().removePlayer(this)
        return true
    }

    private fun buyOpportunity(): PlayerMoveCondition {
        return if (type == PlayerType.PERSON) PlayerMoveCondition.ACTION_EXPECTING
        else {
            decision(PlayerActions.BUY)
            PlayerMoveCondition.COMPLETED
        }
    }

    private fun chanceResult(cell: GameCell): PlayerMoveCondition {
        when ((0..4).random()) {
            0, 1 -> if (!loseMoney((1..2500).random())) loseMoney(money)
            2, 3 -> earnMoney((1..2500).random())
            4 -> if (!cells.isEmpty()) cells[(0 until cells.size).random()].reset()
            //4 earn random cell
            //5 lose random cell
        }
        return PlayerMoveCondition.COMPLETED
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

    private fun tryToSellEarnedCell(moneyToGet: Int): Boolean {
        if (cells.isEmpty()) return false
        var uselessCell: GameCell = findCellWithLowestSellCost()
        for (i in 0 until cells.size) {
            val currentSellCost: Int = cells[i].info.cost.costSell
            if (currentSellCost > moneyToGet && currentSellCost < uselessCell.info.cost.costSell)
                uselessCell = cells[i]
        }
        uselessCell.sell()
        if (uselessCell.info.cost.costSell < moneyToGet)
            return tryToSellEarnedCell(moneyToGet - uselessCell.info.cost.costSell)
        return true
    }

    private fun findCellWithLowestSellCost(): GameCell {
        var resCell: GameCell = cells[0]
        for (c in cells)
            if (c.info.cost.costSell < resCell.info.cost.costSell)
                resCell = c
        return resCell
    }

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start
}