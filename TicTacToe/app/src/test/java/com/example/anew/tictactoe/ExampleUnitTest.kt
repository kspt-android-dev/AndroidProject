package com.example.anew.tictactoe

import org.junit.Test
import java.util.Random

import org.junit.Assert.*
import com.example.anew.tictactoe.Entity
import com.example.anew.tictactoe.GameController
import java.lang.IllegalStateException
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val random = Random()
    fun randomPos(): Pair<Int, Int> {
        return Pair(random.nextInt(3), random.nextInt(3))
    }

    @Test
    fun makeTurnTest() {
        val gc = GameController()
        val (x, y) = randomPos()
        var (x2, y2) = randomPos()

        gc.makeTurn(x, y)
        assertEquals(gc.grid[x][y], Entity.CROSSES)
        while (x == x2 && y == y2) {
            x2 = random.nextInt(3)
            y2 = random.nextInt(3)
        }
        gc.makeTurn(x2, y2)
        assertEquals(gc.grid[x2][y2], Entity.NOUGHTS)
    }

    @Test
    fun whoWonHorizontalTest() {
        val gc = GameController()
        gc.makeTurn(0, 0)
        gc.makeTurn(0, 1)
        gc.makeTurn(1, 0)
        gc.makeTurn(0, 2)
        gc.makeTurn(2, 0)
        assertEquals(gc.whoWon(), Entity.CROSSES)
    }

    @Test
    fun emptyTest() {
        val gc = GameController()
        assertEquals(gc.whoWon(), Entity.EMPTY)
    }

    @Test
    fun whoWonVerticalTest() {
        val gc = GameController()
        gc.makeTurn(1, 1)
        gc.makeTurn(0, 1)
        gc.makeTurn(1, 0)
        gc.makeTurn(0, 2)
        gc.makeTurn(1, 2)
        assertEquals(gc.whoWon(), Entity.CROSSES)
    }

    @Test
    fun whoWonCrossedTest() {
        val gc = GameController()
        gc.makeTurn(0, 0)
        gc.makeTurn(0, 1)
        gc.makeTurn(1, 1)
        gc.makeTurn(0, 2)
        gc.makeTurn(2, 2)
        assertEquals(gc.whoWon(), Entity.CROSSES)
    }

    @Test
    fun whoWonCrossedSecTest() {
        val gc = GameController()
        gc.makeTurn(2, 0)
        gc.makeTurn(0, 1)
        gc.makeTurn(1, 1)
        gc.makeTurn(0, 0)
        gc.makeTurn(0, 2)
        assertEquals(gc.whoWon(), Entity.CROSSES)
    }

    @Test (expected = IllegalStateException::class)
    fun excTest() {
        val gc = GameController()
        gc.makeTurn(2,0)
        gc.makeTurn(2,0)
    }

    @Test (expected = IndexOutOfBoundsException::class)
    fun excTest2() {
        val gc = GameController()
        gc.makeTurn(2,5)
    }

}
