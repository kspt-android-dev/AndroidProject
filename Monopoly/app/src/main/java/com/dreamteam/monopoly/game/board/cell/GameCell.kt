package com.dreamteam.monopoly.game.board.cell

import android.util.Log
import com.dreamteam.monopoly.game.player.Player

open class GameCell(/*id: String, bitmap: Bitmap,*/ val info: GameCellInfo) : Cell(/*id, bitmap*/) {

    var state: CellState = CellState.FREE
    var owner: Player? = null

    fun buy(player: Player): Boolean {
        Log.d("FindError" , "im in GameCell/buy")
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

    fun reset() {
        state = CellState.FREE
        owner!!.removeGameCell(this)
        owner = null
    }

    fun checkBuyCost(money: Int): Boolean = money >= info.cost.costBuy

    fun checkChargeCost(money: Int): Boolean = money >= info.cost.costCharge
}