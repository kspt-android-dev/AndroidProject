package com.dreamteam.monopoly.game.board.cell

import com.dreamteam.monopoly.game.player.Player

open class GameCell(name: String, val type: GameCellType) : Cell(name) {

    var state: CellState = CellState.FREE
    var owner: Player? = null

    fun buy(player: Player): Boolean {
        return if (player.loseMoney(type.cost.costBuy)) {
            state = CellState.OWNED
            owner = player
            true
        } else false
    }

    fun pay(player: Player): Boolean {
        return if (player.loseMoney(type.cost.costCharge)) {
            owner!!.earnMoney(type.cost.costCharge)
            true
        } else false
    }

    fun sell() {
        state = CellState.FREE
        owner!!.earnMoney(type.cost.costSell)
        owner = null
    }

    //fun checkBuyCost(money: Int): Boolean = money >= type.cost.costBuy

    //fun checkChargeCost(money: Int): Boolean = money >= type.cost.costCharge
}