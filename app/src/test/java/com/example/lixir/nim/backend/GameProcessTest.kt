package com.example.lixir.nim.backend

import org.junit.Assert.*
import org.junit.Test

class GameProcessTest{

    @Test
    fun botTest(){
        val list = mutableListOf(0, 0, 0, 0)
        val bot = Bot("name")
        lateinit var result: Pair<Int, Int>
        for (i in 0..Constants.MAIN_LIST[0])
            for (j in 0..Constants.MAIN_LIST[1])
                for (k in 0..Constants.MAIN_LIST[2])
                    for (m in 0..Constants.MAIN_LIST[3]) {
                        list[0] = i
                        list[1] = j
                        list[2] = k
                        list[3] = m
                        result = bot.step(list)
                        if (list.fold(0){total, next -> total xor next} != 0){
                            list[result.first] = result.second
                            assertEquals(0, list.fold(0){total, next -> total xor next})
                        } else {
                            assertEquals(list.indexOf(list.max()), result.first)
                            assertEquals(0, result.second)
                        }
                    }
    }

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