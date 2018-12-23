package com.dreamteam.monopoly

import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.game.player.Player
import org.junit.Test
import org.junit.Assert.*
import android.support.constraint.ConstraintSet

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class MonopolyUnitTest(){
    val ga = GameActivity()
    val gm = GameManager(ga)

    @Test
    fun addremovePlayer()
    {
        gm.addPlayer("Alexander")
        assertEquals(1,gm.players.size)
        gm.addPlayer("Andrei")
        gm.addPlayer("Aleksei")
        assertEquals(3,gm.players.size)
        gm.removePlayer("Andrei")
        assertEquals(2,gm.players.size)

    }

    @Test
    fun Steps()
    {
        gm.addPlayer("Alexander")
        gm.addPlayer("Alexei")
        val (count1, count2) = gm.getCurrentPlayer().throwDices()
        assertEquals(count1+count2,gm.getCurrentPlayer().currentPosition)

    }


}
