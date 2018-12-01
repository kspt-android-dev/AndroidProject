package com.dreamteam.monopoly.game

import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellInfo

object GameData {
    const val startMoney: Int = 15000
    const val loopMoney: Int = 2000
    const val startEnterMoney: Int = 1000

    val boardGameCells: ArrayList<GameCell> = arrayListOf(
            GameCell(info = GameCellInfo.START),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.BANK),
            GameCell(info = GameCellInfo.BANK),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.OTHER),
            GameCell(info = GameCellInfo.START),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.BANK),
            GameCell(info = GameCellInfo.BANK),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.OTHER),
            GameCell(info = GameCellInfo.START),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.BANK),
            GameCell(info = GameCellInfo.BANK),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.OTHER),
            GameCell(info = GameCellInfo.CHANCE),
            GameCell(info = GameCellInfo.EDSACK),
            GameCell(info = GameCellInfo.QUARTUS),
            GameCell(info = GameCellInfo.OTHER))
}