package com.dreamteam.monopoly.game.board.cell

open class GameCell(name: String, type: GameCellType) : Cell(name) {

    var state: CellState = CellState.FREE

}