package com.dreamteam.monopoly.game

import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellInfo

object GameData {
    const val startMoney: Int = 15000
    const val loopMoney: Int = 2000
    const val startEnterMoney: Int = 1000

    const val swapDicesDelay: Long = 2000

    const val boardSizeModifier:Float = 0.95f
    const val cellSidesModifier:Float = 1.75f


    val boardGameCells: ArrayList<GameCell> = arrayListOf(
            GameCell(info = GameCellInfo.START),  //START
            GameCell(info = GameCellInfo.YOUTUBE),
            GameCell(info = GameCellInfo.OTHER),
            GameCell(info = GameCellInfo.TWTICH),
            GameCell(info = GameCellInfo.TAX),
            GameCell(info = GameCellInfo.AUDI),
            GameCell(info = GameCellInfo.HUAWEI),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.SAMSUNG),
            GameCell(info = GameCellInfo.APPLE),
            GameCell(info = GameCellInfo.OTHER),  //PRISON STAY
            GameCell(info = GameCellInfo.BURGERKING),
            GameCell(info = GameCellInfo.ELECTRICITY),
            GameCell(info = GameCellInfo.MCDONALDS),
            GameCell(info = GameCellInfo.KFC),
            GameCell(info = GameCellInfo.BMW),
            GameCell(info = GameCellInfo.NEWYORKER),
            GameCell(info = GameCellInfo.OTHER),
            GameCell(info = GameCellInfo.VANS),
            GameCell(info = GameCellInfo.GUCCI),
            GameCell(info = GameCellInfo.OTHER),  //FREE STAY
            GameCell(info = GameCellInfo.DARKHORSE),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.DC),
            GameCell(info = GameCellInfo.MARVEL),
            GameCell(info = GameCellInfo.BENTLEY),
            GameCell(info = GameCellInfo.ROCKSTART),
            GameCell(info = GameCellInfo.BLIZZARD),
            GameCell(info = GameCellInfo.WATER),
            GameCell(info = GameCellInfo.VALVE),
            GameCell(info = GameCellInfo.PRISON),  //PRISON
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.KOTLIN),
            GameCell(info = GameCellInfo.OTHER),
            GameCell(info = GameCellInfo.EDSAC),
            GameCell(info = GameCellInfo.TESLA),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.VK),
            GameCell(info = GameCellInfo.BANK),
            GameCell(info = GameCellInfo.FACEBOOk))
}