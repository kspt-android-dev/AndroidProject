package com.dreamteam.monopoly.game

import android.graphics.Bitmap
import com.dreamteam.monopoly.game.board.cell.GameCell
import com.dreamteam.monopoly.game.board.cell.GameCellInfo
import com.dreamteam.monopoly.game.board.cell.GameCellType

object GameData {
    const val startMoney: Int = 15000
    const val loopMoney: Int = 2000

    val boardGameCells: ArrayList<GameCell> = arrayListOf(
            GameCell(id = "0", info = GameCellInfo.OTHER, type = GameCellType.START),
            GameCell(id = "1", info = GameCellInfo.EDSACK, type = GameCellType.COMPANY),
            GameCell(id = "2", info = GameCellInfo.OTHER, type = GameCellType.CHANCE),
            GameCell(id = "3", info = GameCellInfo.OTHER, type = GameCellType.START),
            GameCell(id = "4", info = GameCellInfo.EDSACK, type = GameCellType.COMPANY),
            GameCell(id = "5", info = GameCellInfo.OTHER, type = GameCellType.CHANCE),
            GameCell(id = "6", info = GameCellInfo.OTHER, type = GameCellType.START),
            GameCell(id = "7", info = GameCellInfo.EDSACK, type = GameCellType.COMPANY),
            GameCell(id = "8", info = GameCellInfo.OTHER, type = GameCellType.CHANCE),
            GameCell(id = "9", info = GameCellInfo.OTHER, type = GameCellType.START),
            GameCell(id = "10", info = GameCellInfo.EDSACK, type = GameCellType.COMPANY),
            GameCell(id = "11", info = GameCellInfo.OTHER, type = GameCellType.CHANCE))

}