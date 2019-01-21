package com.example.lixir.nim.backend

import org.junit.Assert.*
import org.junit.Test

class GameProcessTest{

    @Test
    fun play() {
        GameProcess.newGame()
        assertEquals(GameProcess.rows, Constants.MAIN_LIST.toMutableList())
        val temp = Constants.MAIN_LIST.toMutableList()
        GameProcess.gameWithBot = false
        for (i in 0 until Constants.MAIN_LIST.size){
            assertFalse(GameProcess.endGame)
            GameProcess.play(i,0)
            temp[i] = 0
            assertEquals(GameProcess.rows, temp)
        }
        assertTrue(GameProcess.endGame)
    }
}