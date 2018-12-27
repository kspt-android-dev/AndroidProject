package com.dreamteam.monopoly.game.player

import android.util.Log
import android.widget.TextView
import com.dreamteam.monopoly.R
import com.dreamteam.monopoly.game.data.GameData.maxChanceMoney
import com.dreamteam.monopoly.game.data.GameData.minChanceMoney
import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.board.cell.CellState
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellType
import java.util.*
import kotlin.collections.ArrayList

class Player(val name: String, startMoney: Int, val type: PlayerType, private val board: Board) {

    var currentPosition: Int = 0
    var money: Int = startMoney
    var cells: ArrayList<GameCell> = ArrayList()
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
        when (currentCell.state) {
            CellState.FREE -> return if (currentCell.info.cellType == GameCellType.COMPANY &&
                    currentCell.checkBuyCost(money)) {
                buyOpportunity()
            } else if (currentCell.info.cellType == GameCellType.CHANCE)
                chanceResult()
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
        return when (action) {
            PlayerActions.BUY -> board.gameWay[currentPosition].info.cellType == GameCellType.COMPANY &&
                    board.gameWay[currentPosition].owner == null &&
                    board.gameWay[currentPosition].buy(this)
            PlayerActions.PAY -> if (board.gameWay[currentPosition].info.cellType == GameCellType.COMPANY) board.gameWay[currentPosition].pay(this)
            else loseMoney(board.gameWay[currentPosition].info.cost.costCharge)
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
        val playerMoneyId = board.activity.resources.getIdentifier("playerMoney${this.id}", "id", board.activity.packageName)
        val playerMoney: TextView = board.activity.findViewById(playerMoneyId)
        playerMoney.text = board.activity.getString(R.string.deathMoneyInfo)
        return true
    }

    private fun buyOpportunity(): PlayerMoveCondition {
        return if (type == PlayerType.PERSON) PlayerMoveCondition.ACTION_EXPECTING
        else {
            decision(PlayerActions.BUY)
            PlayerMoveCondition.COMPLETED
        }
    }

    private fun chanceResult(): PlayerMoveCondition {
        when ((0..8).random()) {
            0, 1, 2 -> if (!loseMoney((minChanceMoney..maxChanceMoney).random())) loseMoney(money)
            3, 4, 5 -> earnMoney((minChanceMoney..maxChanceMoney).random())
            6 -> if (!cells.isEmpty()) cells[(0 until cells.size).random()].reset()
            7 -> board.gameWay[(0 until board.gameWayLength).random()].changeOwner(this)
            8 -> {
                var charge = 0
                for (cell in cells)
                    charge += cell.info.cost.costCharge
                if (!loseMoney(charge)) loseMoney(money)
            }
            9 -> {
                var profit = 0
                for (cell in cells)
                    profit += cell.info.cost.costCharge
                earnMoney(profit)
            }
        }
        return PlayerMoveCondition.COMPLETED
    }

    private fun markOwnedCell(newCell: GameCell) {
        board.activity.playerSetCellMark(board.gameWay.indexOf(newCell), this)
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

    fun restoreOwnedCells(newCellIndexList: ArrayList<Int>) {
        for (index in newCellIndexList) {
            board.gameWay[index].setupOwner(this)
        }
    }

    fun updateOwnedCells() {
        for (cell in cells) {
            markOwnedCell(cell)
            Log.d("RESTORE", "player $name ($id) ${cell.info.name} marked")
        }
    }

    fun addGameCell(newCell: GameCell) {
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

    fun findCellWithLowestSellCost(): GameCell {
        var resCell: GameCell = cells[0]
        for (c in cells)
            if (c.info.cost.costSell < resCell.info.cost.costSell)
                resCell = c
        return resCell
    }

    private fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start
}