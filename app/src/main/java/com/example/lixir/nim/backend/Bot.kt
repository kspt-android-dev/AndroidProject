package com.example.lixir.nim.backend

class Bot (name: String) : Player(name) {

    fun step(list: MutableList<Int>) : Pair<Int, Int>{
        val temp = list.fold(0){total, next -> total xor next}
        val max = list.max() ?: throw IllegalArgumentException()
        list.forEachIndexed { index, i ->  if (i xor temp < i) return Pair(index, i xor temp)}
        return Pair(list.indexOf(max), 0)
    }
}