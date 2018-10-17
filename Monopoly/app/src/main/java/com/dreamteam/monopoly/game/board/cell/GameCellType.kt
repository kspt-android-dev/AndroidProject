package com.dreamteam.monopoly.game.board.cell

enum class GameCellType(val type: CompanyType, val cost: Cost) {

    QUARTUS(CompanyType.SOFT, Cost(100, 75, 20)),
    EDSACK(CompanyType.SOFT, Cost(9999, 9999, 9999))
}