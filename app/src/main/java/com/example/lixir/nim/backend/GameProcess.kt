package com.example.lixir.nim.backend

import com.example.lixir.nim.GameFragment

object GameProcess {
    val rows = Constants.MAIN_LIST.toMutableList()
    val player1 = Player(Constants.PLAYER_1_NAME)
    val player2 = Player(Constants.PLAYER_2_NAME)
    var currentPlayer = player1
        private set
    val bot = Bot(Constants.BOT_NAME)
    var gameWithBot = true
    var endGame = false
        private set
    var winner = player1
        private set

    private fun take(row: Int, matches: Int) {
        if (matches > rows[row]) throw IllegalArgumentException()
        rows[row] = matches
    }

    private fun take(pair: Pair<Int, Int>) {
        take(pair.first, pair.second)
    }

    private fun isCurrentPlayer1() = currentPlayer == player1

    private fun endGame(isBot: Boolean) {
        endGame = true
        winner = when {
            isBot -> bot
            isCurrentPlayer1() -> player1
            else -> player2
        }
    }

    fun newGame() {
        for (i in 0 until rows.size) rows[i] = Constants.MAX_COLUMNS - i * 2
        endGame = false
    }

    fun play(row: Int, column: Int) {
        take(row, column)
        if (rows == mutableListOf(0, 0, 0, 0)) {
            endGame(false)
            return
        }
        when {
            gameWithBot -> take(bot.step(rows))
            isCurrentPlayer1() -> currentPlayer = player2
            else -> currentPlayer = player1
        }
        if (rows == mutableListOf(0, 0, 0, 0)) endGame(true)
    }

}