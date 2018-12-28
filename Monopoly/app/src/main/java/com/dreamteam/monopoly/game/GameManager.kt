package com.dreamteam.monopoly.game

import android.content.res.Configuration
import android.support.constraint.ConstraintLayout
import android.support.constraint.Guideline
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.dreamteam.monopoly.GameActivity
import com.dreamteam.monopoly.R
import com.dreamteam.monopoly.game.data.GameData
import com.dreamteam.monopoly.game.data.GameData.startMoney
import com.dreamteam.monopoly.game.board.Board
import com.dreamteam.monopoly.game.data.ValuesData
import com.dreamteam.monopoly.game.player.Player
import com.dreamteam.monopoly.game.player.PlayerType

class GameManager(val activity: GameActivity) {
    var mainBoard: Board = Board(GameData.boardGameCells, activity)
    var players: ArrayList<Player> = ArrayList()
    var suicidePlayers: ArrayList<Int> = ArrayList()
    var currentPlayerIndex: Int = 0
    var actionState: ActionState = ActionState.IDLE
    var currentInfo: Int = 0

    fun resetPlayersPositions(savedPositions: ArrayList<Int>) {
        if (savedPositions.size == players.size)
            for (i in 0 until players.size) {
                players[i].currentPosition = savedPositions[i]
                mainBoard.changeImagePlace(players[i])
            }
    }

    fun updateAllPositions() {
        for (p in players)
            mainBoard.changeImagePlace(p)
    }

    fun nextPlayerMove() {
        if (currentPlayerIndex < players.size - 1) currentPlayerIndex++
        else currentPlayerIndex = 0
    }

    fun getCurrentPlayer(): Player = players[currentPlayerIndex]

    fun getPlayerById(id: Int): Player {
        for (p in players)
            if (p.id == id)
                return p
        return getCurrentPlayer()
    }

    fun addPlayer(name: String) {
        if (checkExistingPlayer(name))
            players.add(Player(name, startMoney, PlayerType.PERSON, mainBoard))
    }

    fun addPlayers(playersData: List<String>) {
        for (name: String in playersData)
            if (checkExistingPlayer(name))
                players.add(Player(name, startMoney, PlayerType.PERSON, mainBoard))
    }

    private fun addPlayer(name: String, type: PlayerType) {
        if (checkExistingPlayer(name))
            players.add(Player(name, startMoney, type, mainBoard))
    }

    fun addPlayer(name: String, startMoney: Int, type: PlayerType) {
        if (checkExistingPlayer(name))
            players.add(Player(name, startMoney, type, mainBoard))
    }

    private fun checkExistingPlayer(name: String): Boolean {
        for (p in players)
            if (p.name == name) return false
        return true
    }

    fun removePlayer(player: Player) {
        players.remove(player)
        checkEndGame()
    }

    fun removePlayer(name: String) {
        players.remove(players.find { p -> p.name == name })
        checkEndGame()
    }

    fun removePlayer(index: Int) {
        players.removeAt(index)
        checkEndGame()
    }

    private fun checkEndGame() {
        if (players.size == 1) {
            activity.endGameAction(players[0])
        }
    }

    private fun getPlayerByName(name: String): Player? {
        return players.find { p -> p.name == name }
    }

    fun startAssignment(playersNames: HashMap<PlayerType, ArrayList<String>>) {
        val cellWidth: Int
        val cellHeight: Int
        val constraintLayout = activity.findViewById<ConstraintLayout>(R.id.ConstraintLayoutScroll)
        val underTopLineGuideline = activity.findViewById<Guideline>(R.id.UnderTopPartGuideline)
        val horizontalGuideline = activity.findViewById<Guideline>(R.id.HorizontalGuideline)
        val verticalGuideline = activity.findViewById<Guideline>(R.id.VerticalGuideline)
        val metrics = DisplayMetrics()

        activity.windowManager.defaultDisplay.getMetrics(metrics)

        var screenLayout = activity.resources.configuration.screenLayout
        screenLayout = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        Log.d("LAYOUT", activity.packageName)
        if (screenLayout == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            val boardSize: Int =
                    if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        metrics.widthPixels * 2
                    else
                        metrics.heightPixels * 2
            cellWidth = (boardSize / ((mainBoard.gameWayLength / GameData.numberOfSides - 1) +
                    GameData.numberOfCornersPerSide * GameData.cellSidesModifier)).toInt()
            cellHeight = (cellWidth * GameData.cellSidesModifier).toInt()

            if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                horizontalGuideline.setGuidelinePercent(activity.resources.getDimension(R.dimen.scrollViewNatural) /
                        activity.resources.displayMetrics.density / (metrics.heightPixels / activity.resources.displayMetrics.density))
                underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
            } else {
                underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
                verticalGuideline.setGuidelinePercent((activity.resources.getDimension(R.dimen.scrollViewNatural) /
                        activity.resources.displayMetrics.density) / (metrics.widthPixels / activity.resources.displayMetrics.density))
                val verticalGuideline2 = activity.findViewById<Guideline>(R.id.VerticalGuideline2)
                verticalGuideline2.setGuidelinePercent(((activity.resources.getDimension(R.dimen.scrollViewNatural) /
                        activity.resources.displayMetrics.density) / (metrics.widthPixels / activity.resources.displayMetrics.density)) / 2)
            }
            mainBoard.createBoard(constraintLayout, cellHeight, cellWidth)
        } else {
            val boardSize: Int =
                    if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        metrics.widthPixels
                    else
                        metrics.heightPixels
            cellWidth = (boardSize / ((mainBoard.gameWayLength / GameData.numberOfSides - 1) +
                    GameData.numberOfCornersPerSide * GameData.cellSidesModifier)).toInt()
            cellHeight = (cellWidth * GameData.cellSidesModifier).toInt()

            val fullSideLength = GameData.numberOfCornersPerSide * cellHeight.toFloat() +
                    GameData.numberOfCommonCellsPerSide * cellWidth.toFloat()
            if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                horizontalGuideline.setGuidelinePercent(fullSideLength / metrics.heightPixels)
                underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
            } else {
                underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / boardSize)
                horizontalGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
                verticalGuideline.setGuidelinePercent(fullSideLength / metrics.widthPixels)
                val verticalGuideline2 = activity.findViewById<Guideline>(R.id.VerticalGuideline2)
                verticalGuideline2.setGuidelinePercent((fullSideLength / 2) / metrics.widthPixels)
            }
            mainBoard.createBoard(constraintLayout, cellHeight, cellWidth)
        }
        if (playersNames[PlayerType.AI] != null) {
            for (string in playersNames[PlayerType.AI]!!) {
                addPlayer(string, PlayerType.AI)
                val playerStatsId = activity.resources.getIdentifier(activity.getString(R.string.playerStats) +
                        players.size.toString(), ValuesData.id, activity.packageName)
                val playerStats: TextView = activity.findViewById(playerStatsId)
                playerStats.text = string
                getPlayerByName(string)!!.setPlayerID(
                        players.indexOf(getPlayerByName(string)!!) + 1)
                activity.updPlayerMoney(getPlayerByName(string)!!)
            }
        }
        if (playersNames[PlayerType.PERSON] != null) {
            for (string in playersNames[PlayerType.PERSON]!!) {
                addPlayer(string, PlayerType.PERSON)
                val playerStatsId = activity.resources.getIdentifier(activity.getString(R.string.playerStats) +
                        players.size.toString(), ValuesData.id, activity.packageName)
                val playerStats: TextView = activity.findViewById(playerStatsId)
                playerStats.text = string
                getPlayerByName(string)!!.setPlayerID(
                        players.indexOf(getPlayerByName(string)!!) + 1)
                activity.updPlayerMoney(getPlayerByName(string)!!)
            }
        }

        val namesize: Int = when {
            playersNames[PlayerType.PERSON]?.size == null -> playersNames[PlayerType.AI]!!.size
            playersNames[PlayerType.AI]?.size == null -> playersNames[PlayerType.PERSON]!!.size
            else -> playersNames[PlayerType.AI]!!.size + playersNames[PlayerType.PERSON]!!.size
        }

        for (i in 1..namesize) {
            val myPlayerID = activity.resources.getIdentifier(
                    activity.getString(R.string.Player) + i.toString(), ValuesData.id, activity.packageName)
            val playerImage = activity.resources.getDrawable(activity.resources
                    .getIdentifier(activity.getString(R.string.player) + i.toString(),
                            activity.getString(R.string.drawable), activity.packageName), null)
            val player = ImageView(activity)
            player.layoutParams = ConstraintLayout.LayoutParams(cellWidth / 2, cellWidth / 2)
            player.id = (myPlayerID)
            player.setImageDrawable(playerImage)
            constraintLayout.addView(player)
        }
    }

    enum class ActionState(val state: Int) {
        IDLE(0), BUY(1),
        SELL(2), BUY_SELL(3)
    }
}