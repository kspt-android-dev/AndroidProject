package com.dreamteam.monopoly

import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.game.board.cell.CellState
import com.dreamteam.monopoly.game.board.cell.GameCellType
import com.dreamteam.monopoly.game.player.PlayerActions
import com.dreamteam.monopoly.game.player.PlayerMoveCondition
import com.dreamteam.monopoly.game.player.PlayerType
import org.junit.Test
import org.junit.Assert.*

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

class MonopolyUnitTest {
    private val gameActivity = GameActivity()

    @Test
    fun addPlayer() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayer("Alexander")
        assertEquals(1, gameManager.players.size)
        gameManager.addPlayer("Andrei")
        gameManager.addPlayer("Aleksei")
        assertEquals(3, gameManager.players.size)
        gameManager.removePlayer("Andrei")
        assertEquals(2, gameManager.players.size)
    }

    @Test
    fun addMultiplePlayers() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei", "Andrei", "Artem", "Bot"))
        assertEquals(5, gameManager.players.size)
    }

    @Test
    fun removePlayer() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei", "Andrei", "Artem", "Bot"))
        gameManager.removePlayer("Bot")
        assertEquals(4, gameManager.players.size)
        gameManager.removePlayer(gameManager.players.last())
        assertEquals(3, gameManager.players.size)
        gameManager.removePlayer(gameManager.players.lastIndex)
        assertEquals(2, gameManager.players.size)
    }

    @Test
    fun getCurrentPlayer() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayer("Alexander")
        gameManager.addPlayer("Aleksei")
        gameManager.addPlayer("Bot")
        assertEquals("Alexander", gameManager.getCurrentPlayer().name)
    }

    @Test
    fun addFullDataPlayer() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayer("BestEverPlayer", Int.MAX_VALUE, PlayerType.AI)
        assertEquals("BestEverPlayer", gameManager.getCurrentPlayer().name)
        assertEquals(Int.MAX_VALUE, gameManager.getCurrentPlayer().money)
        assertEquals(PlayerType.AI, gameManager.getCurrentPlayer().type)
    }

    @Test
    fun swapPlayerIndex() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayer("Alexander")
        gameManager.addPlayer("Aleksei")
        gameManager.addPlayer("Bot")
        gameManager.nextPlayerMove()
        assertEquals(1, gameManager.currentPlayerIndex)
        gameManager.nextPlayerMove()
        assertEquals(2, gameManager.currentPlayerIndex)
        gameManager.nextPlayerMove()
        assertEquals(0, gameManager.currentPlayerIndex)
    }

    @Test
    fun playerStayAction() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        assertEquals(true, gameManager.getCurrentPlayer().decision(PlayerActions.STAY))
    }

    @Test
    fun playerEarnMoney() {
        val gameManager = GameManager(gameActivity)
        val p1Money = 1000
        val p2Money = 10

        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        gameManager.players[0].earnMoney(p1Money)
        gameManager.players[1].earnMoney(p2Money)
        assertEquals(GameData.startMoney + p1Money, gameManager.players[0].money)
        assertEquals(GameData.startMoney + p2Money, gameManager.players[1].money)
    }

    @Test
    fun playerLoseMoney() {
        val gameManager = GameManager(gameActivity)
        val p1Money = 1
        val p2Money = 99999

        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        assertEquals(true, gameManager.players[0].loseMoney(p1Money))
        assertEquals(false, gameManager.players[1].loseMoney(p2Money))
        assertEquals(GameData.startMoney - p1Money, gameManager.players[0].money)
        assertEquals(GameData.startMoney, gameManager.players[1].money)
    }

    @Test
    fun setupPlayerId() {
        val gameManager = GameManager(gameActivity)
        val p1Id = 4135
        val p2Id = 6014

        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        gameManager.players[0].setPlayerID(p1Id)
        gameManager.players[1].setPlayerID(p2Id)
        assertEquals(p1Id, gameManager.players[0].id)
        assertEquals(p2Id, gameManager.players[1].id)
    }

    @Test
    fun analyzeStartCell() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        assertEquals(PlayerMoveCondition.COMPLETED, gameManager.getCurrentPlayer().analyze())
    }

    @Test
    fun analyzeBuyCell() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        gameManager.getCurrentPlayer().currentPosition++
        assertEquals(PlayerMoveCondition.ACTION_EXPECTING, gameManager.getCurrentPlayer().analyze())
    }

    @Test
    fun analyzeBuyExpensiveCell() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        gameManager.getCurrentPlayer().currentPosition++
        gameManager.getCurrentPlayer().loseMoney(gameManager.getCurrentPlayer().money)
        assertEquals(PlayerMoveCondition.COMPLETED, gameManager.getCurrentPlayer().analyze())
    }

    @Test
    fun mainBoardSize() {
        val gameManager = GameManager(gameActivity)
        assertEquals(GameData.boardGameCells.size, gameManager.mainBoard.gameWay.size)
    }

    @Test
    fun gameCellCheckBuyCost() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        val currentPlayer = gameManager.getCurrentPlayer()
        currentPlayer.currentPosition++
        val currentPlayerCell = gameManager.mainBoard.gameWay[currentPlayer.currentPosition]
        assertEquals(true, currentPlayerCell.checkBuyCost(currentPlayer.money))
    }

    @Test
    fun gameCellSetupOwner() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        var currentPlayer = gameManager.getCurrentPlayer()
        currentPlayer.currentPosition++
        val currentPlayerCell = gameManager.mainBoard.gameWay[currentPlayer.currentPosition]
        currentPlayerCell.owner = currentPlayer
        gameManager.nextPlayerMove()
        currentPlayer = gameManager.getCurrentPlayer()
        gameManager.mainBoard.gameWay[3].owner = currentPlayer

        assertEquals(gameManager.players[0], gameManager.mainBoard.gameWay[1].owner)
        assertEquals(gameManager.players[1], gameManager.mainBoard.gameWay[3].owner)
    }

    @Test
    fun gameCellPay() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayers(arrayListOf("Alexander", "Aleksei"))
        var currentPlayer = gameManager.getCurrentPlayer()
        currentPlayer.currentPosition++
        var currentPlayerCell = gameManager.mainBoard.gameWay[currentPlayer.currentPosition]
        currentPlayerCell.owner = currentPlayer
        gameManager.nextPlayerMove()
        currentPlayer = gameManager.getCurrentPlayer()
        currentPlayer.currentPosition++
        currentPlayerCell = gameManager.mainBoard.gameWay[currentPlayer.currentPosition]

        assertEquals(true, currentPlayerCell.pay(currentPlayer))
        assertEquals(GameData.startMoney - currentPlayerCell.info.cost.costCharge, currentPlayer.money)
        gameManager.nextPlayerMove()
        currentPlayer = gameManager.getCurrentPlayer()
        assertEquals(GameData.startMoney + currentPlayerCell.info.cost.costCharge, currentPlayer.money)
    }

    @Test
    fun gameCellSetupState() {
        val gameManager = GameManager(gameActivity)
        gameManager.mainBoard.gameWay[37].state = CellState.OWNED
        gameManager.mainBoard.gameWay[39].state = CellState.SLEEPING
        assertEquals(CellState.OWNED, gameManager.mainBoard.gameWay[37].state)
        assertEquals(CellState.SLEEPING, gameManager.mainBoard.gameWay[39].state)
    }

    @Test
    fun findCellWithLowestSellCost() {
        val gameManager = GameManager(gameActivity)
        gameManager.addPlayer("BestEverPlayer")
        val player = gameManager.getCurrentPlayer()
        for (cell in gameManager.mainBoard.gameWay) {
            if (cell.info.cellType == GameCellType.COMPANY)
                player.cells.add(cell)
        }
        assertEquals(gameManager.mainBoard.gameWay[1], player.findCellWithLowestSellCost())
        assertEquals(gameManager.mainBoard.gameWay[1].info.cost.costSell, player.findCellWithLowestSellCost().info.cost.costSell)
    }
}
