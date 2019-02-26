package com.example.anew.tictactoe

import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException

class GameController {
    val grid : Array<Array<Entity>> = arrayOf(arrayOf(Entity.EMPTY, Entity.EMPTY, Entity.EMPTY),
                                                arrayOf(Entity.EMPTY, Entity.EMPTY, Entity.EMPTY),
                                                arrayOf(Entity.EMPTY, Entity.EMPTY, Entity.EMPTY))
    var player = Entity.CROSSES
    var countCelss = 0
    fun makeTurn(x:Int, y:Int): Unit {
        if (x < 3 && x > -1 && y < 3 && y > -1) {
            if (isEmpty(x, y)) {
                grid[x][y] = player
                player = if (player == Entity.CROSSES) Entity.NOUGHTS else Entity.CROSSES
            } else throw IllegalStateException("This cell is already taken")
        } else throw IndexOutOfBoundsException("No such cell")
        countCelss++
    }

    fun isEmpty(x:Int, y: Int): Boolean = grid[x][y] == Entity.EMPTY

    fun whoWon(): Entity {
        //проверка по горизонтали
        for (i in 0..2) {
            val row = grid[i].groupBy {it.id}
            if (row.size == 1) {
                val type = row[row.keys.first()]!![0]
                    if (type != Entity.EMPTY)
                        return type
            }
        }
        //проверка по вертикали
        for (i in 0..2) {
            val column = grid.map { it[i] }.groupBy { it.id }
            if (column.size == 1) {
                val type = column[column.keys.first()]!![0]
                    if (type != Entity.EMPTY)
                        return type
            }
        }

        //проверка крест на крест
        val cross = grid.mapIndexed{index, arr -> grid[index][index] }.groupBy { it.id }
        val otherCross = grid.mapIndexed { index, arr -> grid[index][2-index] }.groupBy { it.id }
        if (cross.size == 1) {
            val type = cross[cross.keys.first()]!![0]
            if (type != Entity.EMPTY)
                return type
        }
        if (otherCross.size == 1) {
            val type = otherCross[otherCross.keys.first()]!![0]
            if (type != Entity.EMPTY)
                return type
        }
        return Entity.EMPTY
    }
}