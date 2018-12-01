package com.dreamteam.monopoly.game.board.cell

import android.graphics.Bitmap
import com.dreamteam.monopoly.game.player.Player

open class GameCell(id: String,/* bitmap: Bitmap,*/ val type: GameCellType, val info: GameCellInfo) : Cell(id/*, bitmap*/) {

    var state: CellState = CellState.FREE
    var owner: Player? = null

    fun buy(player: Player): Boolean {
        return if (player.loseMoney(info.cost.costBuy)) {
            state = CellState.OWNED
            owner = player
            player.addGameCell(this)
            true
        } else false
    }

    fun pay(player: Player): Boolean {
        return if (player.loseMoney(info.cost.costCharge)) {
            owner!!.earnMoney(info.cost.costCharge)
            true
        } else false
    }

    fun sell() {
        state = CellState.FREE
        owner!!.removeGameCell(this)
        owner!!.earnMoney(info.cost.costSell)
        owner = null
    }

    fun checkBuyCost(money: Int): Boolean = money >= info.cost.costBuy

    fun checkChargeCost(money: Int): Boolean = money >= info.cost.costCharge
}