package com.dreamteam.monopoly

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintLayout.LayoutParams
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.GameData.bottomSideIndexesModifier
import com.dreamteam.monopoly.game.GameData.leftSideIndexesModifier
import com.dreamteam.monopoly.game.GameData.numberOfCommonCellsPerSide
import com.dreamteam.monopoly.game.GameData.numberOfCornersPerSide
import com.dreamteam.monopoly.game.GameData.numberOfSides
import com.dreamteam.monopoly.game.GameData.rightSideIndexesModifier
import com.dreamteam.monopoly.game.GameData.upSideIndexesModifier
import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.game.board.cell.GameCellType
import com.dreamteam.monopoly.game.player.*
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_game.*
import maes.tech.intentanim.CustomIntent


class GameActivity : AppCompatActivity() {

    private var buttonThrowDices: Button? = null
    private var buttonSuicide: Button? = null
    private var yesButton: Button? = null
    private var noButton: Button? = null
    private var sellButton: Button? = null
    private var question: Button? = null

    private var gameManager: GameManager = GameManager(this)
    private var cellButtons: ArrayList<ImageButton> = ArrayList(gameManager.mainBoard.gameWayLength)
    private var suicidePlayers: ArrayList<Int> = ArrayList()

    private var actionState: ActionState = ActionState.IDLE
    private var currentInfo: Int = 0
    private var indexForBoard: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        hideTopBar()

        buttonThrowDices = findViewById(R.id.buttonThrowCubes)
        yesButton = findViewById(R.id.YesButton)
        noButton = findViewById(R.id.NoButton)
        question = findViewById(R.id.DialogView)
        sellButton = findViewById(R.id.sellButton)
        buttonSuicide = findViewById(R.id.buttonSuicide)

        init()
        if (savedInstanceState != null) {
            dataRestore(savedInstanceState)
        }

        if (gameManager.getCurrentPlayer().type == PlayerType.AI)
            playerStartMoveAction()
        buttonThrowDices!!.setOnClickListener {
            playerStartMoveAction()
        }
    }

    @Override
    override fun finish() {
        super.finish()
        CustomIntent.customType(this, getString(R.string.up_to_bottom))
    }

    override fun onResume() {
        super.onResume()
        hideTopBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideTopBar()
    }

    private fun hideTopBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    private fun init() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.ConstraintLayout)
        val underTopLineGuideline = findViewById<Guideline>(R.id.UnderTopPartGuideline)
        val horizontalGuideline = findViewById<Guideline>(R.id.HorizontalGuideline)
        val verticalGuideline = findViewById<Guideline>(R.id.VerticalGuideline)

        val intent = this.intent
        val bundle: Bundle = intent.extras
        val playersNames: HashMap<PlayerType, ArrayList<String>> = bundle.getSerializable(getString(R.string.playersMap)) as HashMap<PlayerType, ArrayList<String>>

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        val boardSize: Int =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    metrics.widthPixels
                else
                    metrics.heightPixels
        val cellWidth: Int = (boardSize / ((gameManager.mainBoard.gameWayLength / numberOfSides - 1) +
                numberOfCornersPerSide * GameData.cellSidesModifier)).toInt()
        val cellHeight: Int = (cellWidth * GameData.cellSidesModifier).toInt()

        val fullSideLength = numberOfCornersPerSide * cellHeight.toFloat() +
                numberOfCommonCellsPerSide * cellWidth.toFloat()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            horizontalGuideline.setGuidelinePercent(fullSideLength / metrics.heightPixels)
            underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
        } else {
            underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / boardSize)
            horizontalGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
            verticalGuideline.setGuidelinePercent(fullSideLength / metrics.widthPixels)
            val verticalGuideline2 = findViewById<Guideline>(R.id.VerticalGuideline2)
            verticalGuideline2.setGuidelinePercent((fullSideLength / 2) / metrics.widthPixels)
        }
        createBoard(constraintLayout, cellHeight, cellWidth)
        startAssignment(playersNames)

        val namesize: Int
        namesize = when {
            playersNames[PlayerType.PERSON]?.size == null -> playersNames[PlayerType.AI]!!.size
            playersNames[PlayerType.AI]?.size == null -> playersNames[PlayerType.PERSON]!!.size
            else -> playersNames[PlayerType.AI]!!.size + playersNames[PlayerType.PERSON]!!.size
        }
        for (i in 1..namesize) {
            val myPlayerID = resources.getIdentifier(getString(R.string.Player) + i.toString(), getString(R.string.id), packageName)
            val playerImage = resources.getDrawable(resources
                    .getIdentifier(getString(R.string.player) + i.toString(), getString(R.string.drawable), packageName), null)
            val player = ImageView(this)
            player.layoutParams = LayoutParams(cellWidth / 2, cellWidth / 2)
            player.id = (myPlayerID)
            player.setImageDrawable(playerImage)
            constraintLayout.addView(player)
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            val myId = resources.getIdentifier(getString(R.string.cell) + Order.FIRST.value.toString(), getString(R.string.id), packageName)
            if (i == Order.FIRST.value || i == Order.THIRD.value) {
                constraintSet.connect(player.id, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
                constraintSet.connect(player.id, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
                if (i == Order.FIRST.value) constraintSet.connect(player.id, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
                else constraintSet.connect(player.id, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
            }
            if (i == Order.SECOND.value || i == Order.FOURTH.value) {
                constraintSet.connect(player.id, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
                constraintSet.connect(player.id, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
                if (i == Order.SECOND.value) constraintSet.connect(player.id, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
                else constraintSet.connect(player.id, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
            }
            constraintSet.applyTo(constraintLayout)
        }
    }

    private fun playerStartMoveAction() {
        playerStartMove()
        if (gameManager.getCurrentPlayer().analyze() == PlayerMoveCondition.COMPLETED) {
            val handler = Handler()
            handler.postDelayed({
                playersSwap()
            }, GameData.swapDicesDelay)
        } else playerActionRequest()
    }

    private fun playerActionRequest() {
        //высвечивать купить/нет только если есть возможность купить
        if (gameManager.mainBoard.gameWay[gameManager.getCurrentPlayer().currentPosition].info.cellType == GameCellType.COMPANY &&
                gameManager.mainBoard.gameWay[gameManager.getCurrentPlayer().currentPosition].owner == null) {
            actionState = if (actionState == ActionState.SELL) ActionState.BUY_SELL else ActionState.BUY
            activateBuyChoice(true)
        } else {
            playersSwap()
        }
    }

    private fun activateBuyChoice(value: Boolean) {
        val visibility = if (value) View.VISIBLE else View.INVISIBLE
        yesButton!!.visibility = visibility
        noButton!!.visibility = visibility
        question!!.visibility = visibility

        yesButton!!.isClickable = value
        noButton!!.isClickable = value

        if (value) {
            yesButton!!.setOnClickListener {
                performBuyAction()
                yesNoButtonListener()
            }
            noButton!!.setOnClickListener {
                performStayAction()
                yesNoButtonListener()
            }
        }
    }

    private fun yesNoButtonListener() {
        activateBuyChoice(false)
    }

    private fun activateSellChoice(value: Boolean, index: Int = -1) {
        sellButton!!.visibility = if (value) View.VISIBLE else View.INVISIBLE
        sellButton!!.isClickable = value
        if (value) {
            sellButton!!.setOnClickListener {
                gameManager.mainBoard.gameWay[index].sell()
                activateSellChoice(false)
                updPlayerMoney(gameManager.getCurrentPlayer())
            }
        }
    }

    private fun reactivateBuyChoice() {
        activateBuyChoice(false)
        activateBuyChoice(true)
    }

    private fun reactivateSellChoice() {
        activateSellChoice(false)
        activateSellChoice(true, currentInfo)
    }

    private fun performBuyAction() {
        gameManager.getCurrentPlayer().decision(PlayerActions.BUY)
        playersSwap()
    }

    private fun performStayAction() {
        gameManager.getCurrentPlayer().decision(PlayerActions.STAY)
        playersSwap()
    }

    private fun playerStartMove() {
        val dices: Pair<Int, Int> = gameManager.getCurrentPlayer().throwDices()
        val cube1: ImageView = findViewById(R.id.cube1)
        val cube2: ImageView = findViewById(R.id.cube2)
        //getting first cube image
        val drawCube1 = resources.getDrawable(resources
                .getIdentifier(getString(R.string.dice) + (dices.first).toString(),
                        getString(R.string.drawable), packageName), null)
        //getting second cube image
        val drawCube2 = resources.getDrawable(resources
                .getIdentifier(getString(R.string.dice) + (dices.second).toString(),
                        getString(R.string.drawable), packageName), null)
        cube1.setImageDrawable(drawCube1)  //draw this pics
        cube2.setImageDrawable(drawCube2)

        activateThrowButton(false)
    }

    private fun activateThrowButton(value: Boolean) {
        buttonThrowDices!!.visibility = if (value) View.VISIBLE else View.INVISIBLE
        buttonThrowDices!!.isEnabled = value
    }

    private fun playersSwap(withMoneyUpdate: Boolean = true) {
        actionState = ActionState.IDLE
        if (sellButton!!.visibility == View.VISIBLE && sellButton!!.isClickable) {
            activateSellChoice(false)
        }
        //delete images dices pics
        cube1.setImageDrawable(null)
        cube2.setImageDrawable(null)
        if (withMoneyUpdate) updPlayerMoney(gameManager.getCurrentPlayer())
        gameManager.nextPlayerMove()
        activateThrowButton(true)
        if (gameManager.getCurrentPlayer().type == PlayerType.AI) {
            playerStartMoveAction()
        }
        showCurrentPlayerName()
    }

    fun playerSetCellMark(index: Int, player: Player) {
        val shape = cellButtons[index].background as LayerDrawable
        val gradientDrawable = shape
                .findDrawableByLayerId(R.id.backgroundColor) as GradientDrawable
        when (player.id) {
            Order.FIRST.value -> gradientDrawable.setColor(ContextCompat.getColor(this, R.color.Player1BackgroundColor))
            Order.SECOND.value -> gradientDrawable.setColor(ContextCompat.getColor(this, R.color.Player2BackgroundColor))
            Order.THIRD.value -> gradientDrawable.setColor(ContextCompat.getColor(this, R.color.Player3BackgroundColor))
            Order.FOURTH.value -> gradientDrawable.setColor(ContextCompat.getColor(this, R.color.Player4BackgroundColor))
        }
        cellButtons[index].background = shape
    }

    fun playerRemoveCellMark(index: Int) {
        val shape = cellButtons[index].background as LayerDrawable
        val gradientDrawable = shape
                .findDrawableByLayerId(R.id.backgroundColor) as GradientDrawable
        gradientDrawable.setColor(ContextCompat.getColor(this, R.color.cellBackground))
        cellButtons[index].background = shape
    }

    fun getGameManager(): GameManager = gameManager

    private fun startAssignment(playersNames: HashMap<PlayerType, ArrayList<String>>) //adding text/players on map
    {
        if (playersNames[PlayerType.AI] != null) {
            for (string in playersNames[PlayerType.AI]!!) {
                gameManager.addPlayer(string, PlayerType.AI)
                val playerStatsId = resources.getIdentifier(getString(R.string.playerStats) +
                        gameManager.players.size.toString(), getString(R.string.id), packageName)
                val playerStats: TextView = findViewById(playerStatsId)
                playerStats.text = string
                gameManager.getPlayerByName(string)!!.setPlayerID(gameManager.players.size)
                updPlayerMoney(gameManager.getPlayerByName(string)!!)
            }
        }
        if (playersNames[PlayerType.PERSON] != null) {
            for (string in playersNames[PlayerType.PERSON]!!) {
                gameManager.addPlayer(string, PlayerType.PERSON)
                val playerStatsId = resources.getIdentifier(getString(R.string.playerStats) +
                        gameManager.players.size.toString(), getString(R.string.id), packageName)
                val playerStats: TextView = findViewById(playerStatsId)
                playerStats.text = string
                gameManager.getPlayerByName(string)!!.setPlayerID(gameManager.players.size)
                updPlayerMoney(gameManager.getPlayerByName(string)!!)
            }
        }

        buttonSuicide!!.setOnClickListener {
            suicideAction(gameManager.getCurrentPlayer())
        }
    }

    private fun suicideAction(playerToSuicide: Player, withPlayerSwap: Boolean = true) {
        val myPlayerID = resources.getIdentifier(getString(R.string.Player) + playerToSuicide.id.toString(),
                getString(R.string.id), packageName)
        val player = findViewById<ImageView>(myPlayerID)
        player.visibility = View.INVISIBLE
        suicidePlayers.add(playerToSuicide.id)
        playerToSuicide.decision(PlayerActions.RETREAT)
        if (withPlayerSwap) {
            gameManager.currentPlayerIndex--
            playersSwap(false)
        }
        showCurrentPlayerName()
    }

    private fun createBoard(constraintLayout: ConstraintLayout, cellHeight: Int, cellWidth: Int) {
        createCell(cellHeight, cellHeight, constraintLayout, Direction.TOP.value, Direction.TOP.value, Direction.START.value, Direction.START.value, Direction.TOP.value, Direction.TOP.value, true)

        // top side creation
        while (indexForBoard < gameManager.mainBoard.gameWayLength / upSideIndexesModifier) {
            createCell(cellWidth, cellHeight, constraintLayout,
                    Direction.START.value, Direction.END.value, Direction.TOP.value,
                    Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)
        }
        createCell(cellHeight, cellHeight, constraintLayout,
                Direction.START.value, Direction.END.value, Direction.TOP.value,
                Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)

        // right side creation
        while (indexForBoard < gameManager.mainBoard.gameWayLength / rightSideIndexesModifier) {
            createCell(cellHeight, cellWidth, constraintLayout,
                    Direction.START.value, Direction.START.value, Direction.TOP.value,
                    Direction.BOTTOM.value, Direction.END.value, Direction.END.value)
        }
        createCell(cellHeight, cellHeight, constraintLayout,
                Direction.START.value, Direction.START.value, Direction.TOP.value,
                Direction.BOTTOM.value, Direction.END.value, Direction.END.value)

        // bottom side creation
        while (indexForBoard < gameManager.mainBoard.gameWayLength / bottomSideIndexesModifier) {
            createCell(cellWidth, cellHeight, constraintLayout,
                    Direction.END.value, Direction.START.value, Direction.TOP.value,
                    Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)
        }
        Log.d("board", indexForBoard.toString())
        createCell(cellHeight, cellHeight, constraintLayout,
                Direction.END.value, Direction.START.value, Direction.TOP.value,
                Direction.TOP.value, Direction.BOTTOM.value, Direction.BOTTOM.value)

        // left side creation
        while (indexForBoard < gameManager.mainBoard.gameWayLength / leftSideIndexesModifier) {
            createCell(cellHeight, cellWidth, constraintLayout,
                    Direction.START.value, Direction.START.value, Direction.BOTTOM.value,
                    Direction.TOP.value, Direction.END.value, Direction.END.value)
        }
    }

    private fun createCell(width: Int, height: Int, layout: ConstraintLayout, From1: Int, To1: Int, From2: Int, To2: Int, From3: Int, To3: Int, start: Boolean = false) {
        val thisButtonID = resources.getIdentifier(getString(R.string.cell) + (indexForBoard + 1).toString(), getString(R.string.id), packageName)
        val previousButtonID = resources.getIdentifier(getString(R.string.cell) + indexForBoard.toString(), getString(R.string.id), packageName)
        val button = ImageButton(this)
        button.layoutParams = LayoutParams(width, height)
        button.id = (thisButtonID)
        button.setOnClickListener(showInfoClick)
        val shape = resources.getDrawable(resources
                .getIdentifier(getString(R.string.cellBackgroundLayer), getString(R.string.drawable), packageName), null) as LayerDrawable
        val bitmap = resources.getDrawable(resources.getIdentifier(getString(R.string.cellImage) +
                (indexForBoard + 1).toString(), getString(R.string.drawable), packageName), null)
                as BitmapDrawable
        shape.setDrawableByLayerId(R.id.cellLogo, bitmap)
        button.background = shape
        layout.addView(button)
        cellButtons.add(button)
        val constraintSet = ConstraintSet()
        constraintSet.clone(layout)
        if (start) {
            constraintSet.connect(R.id.UnderTopPartGuideline, From1, layout.id, To1, height)
            constraintSet.connect(button.id, From2, layout.id, To2, 0)
            constraintSet.connect(button.id, From3, layout.id, To3, 0)

        } else {
            constraintSet.connect(button.id, From1, previousButtonID, To1, 0)
            constraintSet.connect(button.id, From2, previousButtonID, To2, 0)
            constraintSet.connect(button.id, From3, previousButtonID, To3, 0)
        }
        constraintSet.applyTo(layout)
        playerRemoveCellMark(indexForBoard)
        indexForBoard++
    }

    private val showInfoClick = View.OnClickListener { view ->
        val i = cellButtons.indexOf(view)
        showInfo(i)

        if (gameManager.mainBoard.gameWay[i].owner == gameManager.getCurrentPlayer()) {
            actionState = if (actionState == ActionState.BUY) ActionState.BUY_SELL else ActionState.SELL
            activateSellChoice(true, i)
        } else if (sellButton!!.visibility == View.VISIBLE && sellButton!!.isClickable) {
            activateSellChoice(false)
        }
    }

    private fun showInfo(i: Int) {
        currentInfo = i
        val name: String = gameManager.mainBoard.gameWay[i].info.name
        val costBuy = gameManager.mainBoard.gameWay[i].info.cost.costBuy
        val costSell = gameManager.mainBoard.gameWay[i].info.cost.costSell
        val charge = gameManager.mainBoard.gameWay[i].info.cost.costCharge
        val nameSpace: TextView = findViewById(R.id.cellName)
        val buySpace: TextView = findViewById(R.id.cellCost)
        val sellSpace: TextView = findViewById(R.id.cellSell)
        val chargeSpace: TextView = findViewById(R.id.cellCharge)

        nameSpace.text = String.format(resources.getString(R.string.namespace), name)
        buySpace.text = String.format(resources.getString(R.string.buyspace), costBuy)
        sellSpace.text = String.format(resources.getString(R.string.sellspace), costSell)
        chargeSpace.text = String.format(resources.getString(R.string.chargespace), charge)
    }

    private fun dataRestore(savedInstanceState: Bundle) {
        restoreSuicidePlayers(savedInstanceState)
        gameManager.currentPlayerIndex = savedInstanceState.getInt(getString(R.string.currentPlayerIndex))
        restoreMoney(savedInstanceState)
        restoreCells(savedInstanceState)
        restoreActionState(savedInstanceState)
        restoreCurrentInfo(savedInstanceState)
    }

    private fun restoreSuicidePlayers(savedInstanceState: Bundle) {
        suicidePlayers.clear()
        val playersToSuicide: ArrayList<Int> = savedInstanceState.getIntegerArrayList(getString(R.string.playersSuicideIds))
        for (i in 0 until playersToSuicide.size)
            suicideAction(gameManager.getPlayerById(playersToSuicide[i]), false)
    }

    private fun restoreMoney(savedInstanceState: Bundle) {
        val pMoney = savedInstanceState.getIntegerArrayList(getString(R.string.playerMoney))
        for (i in 0 until pMoney.size) {
            gameManager.players[i].money = pMoney[i]
            updPlayerMoney(gameManager.players[i])
        }
    }

    private fun restoreCells(savedInstanceState: Bundle) {
        for (i in 0 until gameManager.players.size) {
            gameManager.players[i].restoreGameCells(
                    savedInstanceState.getIntegerArrayList(getString(R.string.playerCells) + i.toString()))
        }
    }

    private fun restoreActionState(savedInstanceState: Bundle) {
        when (savedInstanceState.getInt(getString(R.string.actionState))) {
            ActionState.BUY.state -> {
                activateThrowButton(false)
                reactivateBuyChoice()
            }
            ActionState.SELL.state -> reactivateSellChoice()
            ActionState.BUY_SELL.state -> {
                activateThrowButton(false)
                reactivateBuyChoice()
                reactivateSellChoice()
            }
        }
    }

    private fun restoreCurrentInfo(savedInstanceState: Bundle) {
        showInfo(savedInstanceState.getInt(getString(R.string.currentInfo)))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        gameManager.resetSaveData(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val playersNum: Int = gameManager.players.size
        val playersPos = ArrayList<Int>(playersNum)
        val playersMoney = ArrayList<Int>(playersNum)
        val playersOwnedCells: ArrayList<ArrayList<Int>> = arrayListOf(
                ArrayList(), ArrayList(), ArrayList(), ArrayList())
        for (i in 0 until playersNum) {
            val player = gameManager.players[i]
            playersPos.add(player.currentPosition)
            playersMoney.add(player.money)
            for (j in 0 until player.cells.size)
                playersOwnedCells[i].add(player.cells[j].id.toInt())
        }
        outState?.putIntegerArrayList(getString(R.string.playersSuicideIds), suicidePlayers)
        outState?.putIntegerArrayList(getString(R.string.playersPositions), playersPos)
        outState?.putIntegerArrayList(getString(R.string.playerMoney), playersMoney)
        outState?.putInt(getString(R.string.currentPlayerIndex), gameManager.currentPlayerIndex)
        outState?.putInt(getString(R.string.actionState), actionState.state)
        outState?.putInt(getString(R.string.currentInfo), currentInfo)
        for (i in 0 until playersNum) {
            outState?.putIntegerArrayList(getString(R.string.playerCells) + i.toString(), playersOwnedCells[i])
        }
        super.onSaveInstanceState(outState)
    }

    private fun updPlayerMoney(player: Player) {
        val playerMoneyId = resources.getIdentifier(getString(R.string.playerMoney) +
                gameManager.getPlayerByName(player.name)!!.id, getString(R.string.id), packageName)
        val playerMoney: TextView = findViewById(playerMoneyId)
        playerMoney.text = gameManager.getPlayerByName(player.name)!!.money.toString()
    }

    fun endGameAction(winner: Player) {
        intent = Intent(this, WinScreenActivity::class.java)
        intent.putExtra(getString(R.string.winnerName), winner.name)
        startActivity(intent)
        CustomIntent.customType(this, getString(R.string.bottom_to_up))
    }

    private fun showCurrentPlayerName() {
        Toasty.info(this, gameManager.getCurrentPlayer().name +
                getString(R.string.move), Toast.LENGTH_SHORT, true).show()
    }

    private enum class ActionState(val state: Int) {
        IDLE(0), BUY(1),
        SELL(2), BUY_SELL(3)
    }

    private enum class Direction(val value: Int) {
        LEFT(1), RIGHT(2),
        TOP(3), BOTTOM(4),
        START(6), END(7)
    }
}
