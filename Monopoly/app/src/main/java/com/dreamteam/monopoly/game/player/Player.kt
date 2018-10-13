package com.dreamteam.monopoly.game.player

import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.board.cell.GameCell

class Player(val name: String, startMoney: Int, private val board: Board) {

    var currentPosition: Int = 0

    private var money: Int = startMoney


    fun Go(steps: Int) {
        board.movePlayer(currentPosition + steps, this)
    }

    fun decision(action: PlayerActions) {
        when (action) {
            PlayerActions.BUY -> if(board.gameWay[currentPosition] is GameCell &&
                    (board.gameWay[currentPosition] as GameCell).buy(this)){
                return
            }
            else TODO()
            PlayerActions.PAY -> if(board.gameWay[currentPosition] is GameCell &&
                    (board.gameWay[currentPosition] as GameCell).pay(this))
                return
            else TODO()
            PlayerActions.STAY -> TODO()
        }
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