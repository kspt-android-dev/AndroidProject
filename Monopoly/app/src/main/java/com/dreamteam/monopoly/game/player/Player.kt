package com.dreamteam.monopoly.game.player

import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellType

class Player(val name: String, startMoney: Int, private val board: Board) {

    var currentPosition: Int = 0
    var money: Int = startMoney
    var cells: ArrayList<GameCell> = ArrayList()    // ?


    fun Go(steps: Int) {
        board.movePlayer(currentPosition + steps, this)
    }

    fun decision(action: PlayerActions): Boolean {
        return when (action) {
            PlayerActions.BUY -> board.gameWay[currentPosition].type == GameCellType.COMPANY &&
                    board.gameWay[currentPosition].buy(this)        //TODO()
            PlayerActions.PAY -> board.gameWay[currentPosition].type == GameCellType.COMPANY &&
                    board.gameWay[currentPosition].pay(this)        //TODO()
            PlayerActions.STAY -> TODO()
            PlayerActions.RETREAT -> retreat()
        }
    }

    private fun retreat(): Boolean {
        return true
    }

    //-----Money-----
    fun earnMoney(amount: Int) {
        money += amount
    }

    fun loseMoney(amount: Int): Boolean {
        return if (money - amount >= 0) {
            money -= amount
            true
        } else false
    }
}