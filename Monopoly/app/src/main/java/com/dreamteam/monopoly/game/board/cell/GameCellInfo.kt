package com.dreamteam.monopoly.game.board.cell

enum class GameCellInfo(val type: CompanyType, val cost: Cost) {
    QUARTUS(CompanyType.SOFT, Cost(100, 75, 20)),
    EDSACK(CompanyType.SOFT, Cost(9999, 9999, 9999)),

    OTHER(CompanyType.DEFAULT, Cost(0, 0, 0))
}