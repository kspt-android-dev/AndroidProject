package com.example.lixir.nim.backend

import kotlin.IllegalArgumentException

class Bot (name: String) : Player(name) {

    fun step(list: MutableList<Int>) : Pair<Int, Int>{
        val temp = list.fold(0){total, next -> total xor next}
        val max = list.max() ?: throw IllegalArgumentException()
        if (temp == 0 || max xor temp > list.indexOf(max)) return Pair(list.indexOf(max), 0)
        return Pair(list.indexOf(max), max xor temp)
    }
}