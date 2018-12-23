package com.dreamteam.monopoly.game.board.cell

enum class GameCellInfo(val cellType: GameCellType, val companyType: CompanyType, val cost: Cost) {
    QUARTUS(GameCellType.COMPANY, CompanyType.SOFT, Cost(4100, 1250, 600)),
    EDSAC(GameCellType.COMPANY, CompanyType.SOFT, Cost(4600, 2500, 680)),
    KOTLIN(GameCellType.COMPANY, CompanyType.SOFT, Cost(4300, 2000, 640)),

    AUDI(GameCellType.COMPANY, CompanyType.AUTO, Cost(2000, 900, 400)),
    BMW(GameCellType.COMPANY, CompanyType.AUTO, Cost(2000, 900, 400)),
    BENTLEY(GameCellType.COMPANY, CompanyType.AUTO, Cost(2000, 900, 400)),
    TESLA(GameCellType.COMPANY, CompanyType.AUTO, Cost(2000, 900, 400)),

    BLIZZARD(GameCellType.COMPANY, CompanyType.GAMEDEV, Cost(4000, 1250, 560)),
    VALVE(GameCellType.COMPANY, CompanyType.GAMEDEV, Cost(4200, 2500, 600)),
    ROCKSTART(GameCellType.COMPANY, CompanyType.GAMEDEV, Cost(3900, 2000, 520)),

    MCDONALDS(GameCellType.COMPANY, CompanyType.FOOD, Cost(2300, 1000,320 )),
    KFC(GameCellType.COMPANY, CompanyType.FOOD, Cost(2500, 1200, 350)),
    BURGERKING(GameCellType.COMPANY, CompanyType.FOOD, Cost(1900, 800, 290)),

    APPLE(GameCellType.COMPANY, CompanyType.PHONES, Cost(2000, 900, 200)),
    SAMSUNG(GameCellType.COMPANY, CompanyType.PHONES, Cost(1600, 700, 140)),
    HUAWEI(GameCellType.COMPANY, CompanyType.PHONES, Cost(1850, 750, 170)),

    MARVEL(GameCellType.COMPANY, CompanyType.COMICS, Cost(3900, 1850, 550)),
    DC(GameCellType.COMPANY, CompanyType.COMICS, Cost(3600, 1750, 510)),
    DARKHORSE(GameCellType.COMPANY, CompanyType.COMICS, Cost(3300, 1600, 480)),

    VANS(GameCellType.COMPANY, CompanyType.CLOTHES, Cost(2900, 1350, 420)),
    GUCCI(GameCellType.COMPANY, CompanyType.CLOTHES, Cost(3300, 1600, 450)),
    NEWYORKER(GameCellType.COMPANY, CompanyType.CLOTHES, Cost(2700, 1200, 380)),

    VK(GameCellType.COMPANY, CompanyType.SOCIALNETW, Cost(4700, 2500, 700)),
    FACEBOOK(GameCellType.COMPANY, CompanyType.SOCIALNETW, Cost(5000, 3000, 800)),

    YOUTUBE(GameCellType.COMPANY, CompanyType.VIDEO, Cost(1000, 450, 50)),
    TWTICH(GameCellType.COMPANY, CompanyType.VIDEO, Cost(1250, 500, 60)),

    WATER(GameCellType.COMPANY, CompanyType.SERVICE, Cost(2000, 1000, 400)), //set dice count * /something/ cost
    ELECTRICITY(GameCellType.COMPANY, CompanyType.SERVICE, Cost(1500, 750, 350)),

    OTHER(GameCellType.DEFAULT, CompanyType.DEFAULT, Cost(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)),
    START(GameCellType.START, CompanyType.DEFAULT, Cost(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)),
    CHANCE(GameCellType.CHANCE, CompanyType.DEFAULT, Cost(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)),
    PRISON(GameCellType.PRISON, CompanyType.DEFAULT, Cost(Int.MAX_VALUE, Int.MAX_VALUE, 500)),
    BANK(GameCellType.BANK, CompanyType.DEFAULT, Cost(Int.MAX_VALUE, Int.MAX_VALUE, 2000)),
    TAX(GameCellType.BANK, CompanyType.DEFAULT, Cost(Int.MAX_VALUE, Int.MAX_VALUE, 1500))
}