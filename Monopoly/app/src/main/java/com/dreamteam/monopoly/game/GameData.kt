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
            GameCell(id = "0", info = GameCellInfo.EDSACK, type = GameCellType.COMPANY))

}