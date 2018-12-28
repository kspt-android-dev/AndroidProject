package com.dreamteam.monopoly

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintLayout.LayoutParams
import android.support.constraint.Guideline
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import com.dreamteam.monopoly.game.data.GameData
import com.dreamteam.monopoly.game.data.GameData.numberOfCornersPerSide
import com.dreamteam.monopoly.game.data.GameData.numberOfSides
import com.dreamteam.monopoly.game.data.ValuesData
import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.game.GameManager.ActionState
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
    private var scroll: HorizontalScrollView? = null

    private var gameManager: GameManager = GameManager(this)
    var cellButtons: ArrayList<ImageButton> = ArrayList(gameManager.mainBoard.gameWayLength)

    private val saveMode: SaveMode = SaveMode.FROM_VIEW_MODEL

    private lateinit var savedData: GameActivityData

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
        scroll = findViewById(R.id.scrollview1)

        dataRestore(savedInstanceState)

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
        val cellWidth: Int
        val cellHeight: Int
        val constraintLayout = findViewById<ConstraintLayout>(R.id.ConstraintLayoutScroll)
        val underTopLineGuideline = findViewById<Guideline>(R.id.UnderTopPartGuideline)
        val horizontalGuideline = findViewById<Guideline>(R.id.HorizontalGuideline)
        val verticalGuideline = findViewById<Guideline>(R.id.VerticalGuideline)

        val intent = this.intent
        val bundle: Bundle = intent.extras
        @Suppress("UNCHECKED_CAST")
        val playersNames: HashMap<PlayerType, ArrayList<String>> = bundle.getSerializable(ValuesData.playersMap) as HashMap<PlayerType, ArrayList<String>>

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        var screenLayout = getResources().getConfiguration().screenLayout
        screenLayout = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        Log.d("LAYOUT", this.packageName)
        if (screenLayout == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            val boardSize: Int =
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        metrics.widthPixels * 2
                    else
                        metrics.heightPixels * 2
            cellWidth = (boardSize / ((gameManager.mainBoard.gameWayLength / numberOfSides - 1) +
                    numberOfCornersPerSide * GameData.cellSidesModifier)).toInt()
            cellHeight = (cellWidth * GameData.cellSidesModifier).toInt()

            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                horizontalGuideline.setGuidelinePercent(resources.getDimension(R.dimen.scrollViewNatural) / resources.displayMetrics.density / (metrics.heightPixels / resources.displayMetrics.density))
                underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
            } else {
                underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
                verticalGuideline.setGuidelinePercent((resources.getDimension(R.dimen.scrollViewNatural) / resources.displayMetrics.density) / (metrics.widthPixels / resources.displayMetrics.density))
                val verticalGuideline2 = findViewById<Guideline>(R.id.VerticalGuideline2)
                verticalGuideline2.setGuidelinePercent(((resources.getDimension(R.dimen.scrollViewNatural) / resources.displayMetrics.density) / (metrics.widthPixels / resources.displayMetrics.density)) / 2)
            }
            gameManager.mainBoard.createBoard(constraintLayout, cellHeight, cellWidth)
        } else {
            val boardSize: Int =
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                        metrics.widthPixels
                    else
                        metrics.heightPixels
            cellWidth = (boardSize / ((gameManager.mainBoard.gameWayLength / numberOfSides - 1) +
                    numberOfCornersPerSide * GameData.cellSidesModifier)).toInt()
            cellHeight = (cellWidth * GameData.cellSidesModifier).toInt()

            val fullSideLength = numberOfCornersPerSide * cellHeight.toFloat() +
                    GameData.numberOfCommonCellsPerSide * cellWidth.toFloat()
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
            gameManager.mainBoard.createBoard(constraintLayout, cellHeight, cellWidth)
        }

        gameManager.startAssignment(playersNames)

        val namesize: Int = when {
            playersNames[PlayerType.PERSON]?.size == null -> playersNames[PlayerType.AI]!!.size
            playersNames[PlayerType.AI]?.size == null -> playersNames[PlayerType.PERSON]!!.size
            else -> playersNames[PlayerType.AI]!!.size + playersNames[PlayerType.PERSON]!!.size
        }

        for (i in 1..namesize) {
            val myPlayerID = resources.getIdentifier(getString(R.string.Player) + i.toString(), ValuesData.id, packageName)
            val playerImage = resources.getDrawable(resources
                    .getIdentifier(getString(R.string.player) + i.toString(), getString(R.string.drawable), packageName), null)
            val player = ImageView(this)
            player.layoutParams = LayoutParams(cellWidth / 2, cellWidth / 2)
            player.id = (myPlayerID)
            player.setImageDrawable(playerImage)
            constraintLayout.addView(player)
        }
        gameManager.updateAllPositions()

        buttonSuicide!!.setOnClickListener {
            suicideAction(gameManager.getCurrentPlayer())
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
            gameManager.actionState = if (gameManager.actionState == ActionState.SELL) ActionState.BUY_SELL else ActionState.BUY
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
                updPlayerMoney(gameManager.getCurrentPlayer()
                )
                if (gameManager.actionState == ActionState.SELL)
                    gameManager.actionState = ActionState.IDLE
                else
                    gameManager.actionState = ActionState.BUY
            }
        }
    }

    private fun reactivateBuyChoice() {
        activateBuyChoice(false)
        activateBuyChoice(true)
    }

    private fun reactivateSellChoice() {
        activateSellChoice(false)
        activateSellChoice(true, gameManager.currentInfo)
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
                .getIdentifier(ValuesData.dice + (dices.first).toString(),
                        getString(R.string.drawable), packageName), null)
        //getting second cube image
        val drawCube2 = resources.getDrawable(resources
                .getIdentifier(ValuesData.dice + (dices.second).toString(),
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
        gameManager.actionState = ActionState.IDLE
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
        Log.d("RESTORE", "player's id(${player.id} cell($index) color setup")
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

    private fun suicideAction(playerToSuicide: Player, withPlayerSwap: Boolean = true) {
        val myPlayerID = resources.getIdentifier(getString(R.string.Player) + playerToSuicide.id.toString(),
                ValuesData.id, packageName)
        val player = findViewById<ImageView>(myPlayerID)
        player.visibility = View.INVISIBLE
        gameManager.suicidePlayers.add(playerToSuicide.id)
        playerToSuicide.decision(PlayerActions.RETREAT)
        if (withPlayerSwap) {
            gameManager.currentPlayerIndex--
            playersSwap(false)
        }
        showCurrentPlayerName()
    }

    val showInfoClick = View.OnClickListener { view ->
        val i = cellButtons.indexOf(view)
        showInfo(i)

        if (gameManager.mainBoard.gameWay[i].owner == gameManager.getCurrentPlayer()) {
            gameManager.actionState = if (gameManager.actionState == ActionState.BUY) ActionState.BUY_SELL else ActionState.SELL
            activateSellChoice(true, i)
        } else if (sellButton!!.visibility == View.VISIBLE && sellButton!!.isClickable) {
            activateSellChoice(false)
        }
    }

    private fun showInfo(i: Int) {
        gameManager.currentInfo = i
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

    private fun dataRestore(savedInstanceState: Bundle? = null) {
        when (saveMode) {
            SaveMode.FROM_BUNDLE -> {
                init()
                if (savedInstanceState != null) restoreFromBundle(savedInstanceState)
            }
            SaveMode.FROM_VIEW_MODEL -> {
                savedData = ViewModelProviders.of(this).get(GameActivityData::class.java)
                init()
                if (savedData.isInited) restoreFromViewModel()
            }
        }
    }

    private fun restoreFromBundle(savedInstanceState: Bundle) {
        gameManager.resetPlayersPositions(savedInstanceState.getIntegerArrayList(ValuesData.playersPositions))
        restoreSuicidePlayers(savedInstanceState.getIntegerArrayList(ValuesData.playersSuicideIds))
        gameManager.currentPlayerIndex = savedInstanceState.getInt(ValuesData.currentPlayerIndex)
        restoreMoney(savedInstanceState.getIntegerArrayList(ValuesData.playerMoney))
        restoreCells(savedInstanceState)
        restoreActionState(savedInstanceState.getInt(ValuesData.actionState))
        restoreCurrentInfo(savedInstanceState.getInt(ValuesData.currentInfo))
    }

    private fun restoreFromViewModel() {
        gameManager.resetPlayersPositions(savedData.getPlayersPositionsData())
        restoreSuicidePlayers(savedData.gameManager.suicidePlayers)
        gameManager.currentPlayerIndex = savedData.gameManager.currentPlayerIndex
        restoreMoney(savedData.getPlayersMoneyData())
        restoreCells()
        restoreActionState(savedData.gameManager.actionState.state)
        restoreCurrentInfo(savedData.gameManager.currentInfo)
    }

    private fun restoreSuicidePlayers(playersToSuicide: ArrayList<Int>) {
        gameManager.suicidePlayers.clear()
        for (i in 0 until playersToSuicide.size)
            suicideAction(gameManager.getPlayerById(playersToSuicide[i]), false)
    }

    private fun restoreMoney(pMoney: ArrayList<Int>) {
        for (i in 0 until pMoney.size) {
            gameManager.players[i].money = pMoney[i]
            updPlayerMoney(gameManager.players[i])
        }
    }

    private fun restoreCells(savedInstanceState: Bundle) {
        for (i in 0 until gameManager.players.size) {
            gameManager.players[i].restoreOwnedCells(
                    savedInstanceState.getIntegerArrayList(ValuesData.playerCells + i.toString()))
        }
    }

    private fun restoreCells() {
        for (i in 0 until gameManager.players.size) {
            gameManager.players[i].restoreOwnedCells(savedData.getPlayersCellsData(i))
        }
    }

    private fun restoreActionState(state: Int) {
        when (state) {
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

    private fun restoreCurrentInfo(index: Int) {
        showInfo(index)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (saveMode == SaveMode.FROM_BUNDLE) {
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
            outState?.putIntegerArrayList(ValuesData.playersSuicideIds, gameManager.suicidePlayers)
            outState?.putIntegerArrayList(ValuesData.playersPositions, playersPos)
            outState?.putIntegerArrayList(ValuesData.playerMoney, playersMoney)
            outState?.putInt(ValuesData.currentPlayerIndex, gameManager.currentPlayerIndex)
            outState?.putInt(ValuesData.actionState, gameManager.actionState.state)
            outState?.putInt(ValuesData.currentInfo, gameManager.currentInfo)
            for (i in 0 until playersNum) {
                outState?.putIntegerArrayList(ValuesData.playerCells + i.toString(), playersOwnedCells[i])
            }
            super.onSaveInstanceState(outState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (saveMode == SaveMode.FROM_VIEW_MODEL) {
            savedData.save(gameManager)
        }
    }

    fun updPlayerMoney(player: Player) {
        val playerMoneyId = resources.getIdentifier(ValuesData.playerMoney +
                player.id, ValuesData.id, packageName)
        val playerMoney: TextView = findViewById(playerMoneyId)
        playerMoney.text = player.money.toString()
    }

    fun endGameAction(winner: Player) {
        intent = Intent(this, WinScreenActivity::class.java)
        intent.putExtra(ValuesData.winnerName, winner.name)
        startActivity(intent)
        CustomIntent.customType(this, getString(R.string.bottom_to_up))
    }

    private fun showCurrentPlayerName() {
        Toasty.info(this, gameManager.getCurrentPlayer().name +
                getString(R.string.move), Toast.LENGTH_SHORT, true).show()
    }

    private enum class SaveMode {
        FROM_BUNDLE, FROM_VIEW_MODEL
    }
}
