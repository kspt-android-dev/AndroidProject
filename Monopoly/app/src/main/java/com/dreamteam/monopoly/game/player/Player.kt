package com.dreamteam.monopoly.game.player

import com.dreamteam.monopoly.game.board.Board

class Player(val name: String, private val startMoney: Int, val board: Board) {

    private var money: Int = startMoney
    private var currentPosition: Int = 0
    private var currentCell = board.gameWay[0]


    fun Go(steps: Int) {
        currentCell = board.MovePlayer(currentPosition + steps, this)
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