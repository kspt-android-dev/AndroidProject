package com.dreamteam.monopoly.game.data

import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellInfo

object GameData {
    // Economy settings
    const val startMoney: Int = 15000
    const val loopMoney: Int = 2000

    // Players settings settings
    const val minPlayers: Int = 2
    const val maxPlayers: Int = 4
    const val maxNameLength = 15

    // Board settings
    const val numberOfSides: Int = 4
    const val upSideIndexesModifier: Int = numberOfSides
    const val rightSideIndexesModifier: Int = numberOfSides / 2
    const val bottomSideIndexesModifier: Float = numberOfSides / 3f
    const val leftSideIndexesModifier: Int = numberOfSides / 4
    const val numberOfCornersPerSide = 2
    const val swapDicesDelay: Long = 2000
    const val cellSidesModifier: Float = 1.75f
    const val numberOfCommonCellsPerSide = 9

    /// Special cells settings
    const val minChanceMoney: Int = 1
    const val maxChanceMoney: Int = 2500

    // Effects settings
    const val alertDuration: Long = 1500
    const val shakeEffectDuration: Long = 500
    const val shakeRepeatCount: Int = 0
    const val splashScreenDuration: Int = 4000

    // All board cells list
    val boardGameCells: ArrayList<GameCell> = arrayListOf(
            GameCell("0", info = GameCellInfo.START),
            GameCell("1", info = GameCellInfo.YOUTUBE),
            GameCell("2", info = GameCellInfo.OTHER),
            GameCell("3", info = GameCellInfo.TWTICH),
            GameCell("4", info = GameCellInfo.TAX),
            GameCell("5", info = GameCellInfo.AUDI),
            GameCell("6", info = GameCellInfo.HUAWEI),
            GameCell("7", info = GameCellInfo.CHANCE),
            GameCell("8", info = GameCellInfo.SAMSUNG),
            GameCell("9", info = GameCellInfo.APPLE),
            GameCell("10", info = GameCellInfo.OTHER),  //PRISON STAY
            GameCell("11", info = GameCellInfo.BURGERKING),
            GameCell("12", info = GameCellInfo.ELECTRICITY),
            GameCell("13", info = GameCellInfo.MCDONALDS),
            GameCell("14", info = GameCellInfo.KFC),
            GameCell("15", info = GameCellInfo.BMW),
            GameCell("16", info = GameCellInfo.NEWYORKER),
            GameCell("17", info = GameCellInfo.OTHER),
            GameCell("18", info = GameCellInfo.VANS),
            GameCell("19", info = GameCellInfo.GUCCI),
            GameCell("20", info = GameCellInfo.OTHER),  //FREE STAY
            GameCell("21", info = GameCellInfo.DARKHORSE),
            GameCell("22", info = GameCellInfo.CHANCE),
            GameCell("23", info = GameCellInfo.DC),
            GameCell("24", info = GameCellInfo.MARVEL),
            GameCell("25", info = GameCellInfo.BENTLEY),
            GameCell("26", info = GameCellInfo.ROCKSTART),
            GameCell("27", info = GameCellInfo.BLIZZARD),
            GameCell("28", info = GameCellInfo.WATER),
            GameCell("29", info = GameCellInfo.VALVE),
            GameCell("30", info = GameCellInfo.PRISON),
            GameCell("31", info = GameCellInfo.QUARTUS),
            GameCell("32", info = GameCellInfo.KOTLIN),
            GameCell("33", info = GameCellInfo.OTHER),
            GameCell("34", info = GameCellInfo.EDSAC),
            GameCell("35", info = GameCellInfo.TESLA),
            GameCell("36", info = GameCellInfo.CHANCE),
            GameCell("37", info = GameCellInfo.VK),
            GameCell("38", info = GameCellInfo.BANK),
            GameCell("39", info = GameCellInfo.FACEBOOK))
}