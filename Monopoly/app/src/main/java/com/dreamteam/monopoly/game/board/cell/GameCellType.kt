package com.dreamteam.monopoly.game.board.cell

enum class GameCellType(type: CompanyType, cost: Cost) {

    QUARTOS(CompanyType.SOFT, Cost(100, 75, 20)),
    EDSACK(CompanyType.SOFT, Cost(9999, 9999, 9999))
}