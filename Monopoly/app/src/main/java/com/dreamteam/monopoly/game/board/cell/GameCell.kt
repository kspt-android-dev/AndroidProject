package com.dreamteam.monopoly.game.board.cell


import com.dreamteam.monopoly.game.player.Player

open class GameCell(id: String, val info: GameCellInfo) : Cell(id) {

    var state: CellState = CellState.FREE
    var owner: Player? = null

    fun buy(player: Player): Boolean {
        return if (player.loseMoney(info.cost.costBuy)) {
            setupOwner(player)
            true
        } else false
    }

    fun pay(player: Player): Boolean {
        return if (player.loseMoney(info.cost.costCharge)) {
            owner?.earnMoney(info.cost.costCharge)
            true
        } else false
    }

    fun sell() {
        state = CellState.FREE
        owner?.removeGameCell(this)
        owner?.earnMoney(info.cost.costSell)
        owner = null
    }

    fun reset() {
        state = CellState.FREE
        owner?.removeGameCell(this)
        owner = null
    }

    fun setupOwner(player: Player) {
        state = CellState.OWNED
        owner = player
        player.addGameCell(this)
    }

    fun changeOwner(newOwner: Player) {
        owner?.removeGameCell(this)
        setupOwner(newOwner)
    }

    fun checkBuyCost(money: Int): Boolean = money >= info.cost.costBuy
}