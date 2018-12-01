package com.dreamteam.monopoly.game.board.cell

enum class GameCellInfo(val cellType: GameCellType, val companyType: CompanyType, val cost: Cost) {
    QUARTUS(GameCellType.COMPANY, CompanyType.SOFT, Cost(100, 75, 20)),
    EDSACK(GameCellType.COMPANY, CompanyType.SOFT, Cost(9999, 9999, 9999)),

    OTHER(GameCellType.DEFAULT, CompanyType.DEFAULT, Cost(0, 0, 0)),
    START(GameCellType.START, CompanyType.DEFAULT, Cost(0, 0, 0)),
    CHANCE(GameCellType.CHANCE, CompanyType.DEFAULT, Cost(0, 0, 0)),
    PRISON(GameCellType.PRISON, CompanyType.DEFAULT, Cost(0, 0, 500)),
    BANK(GameCellType.BANK, CompanyType.DEFAULT, Cost(0, 0, 2000))
}