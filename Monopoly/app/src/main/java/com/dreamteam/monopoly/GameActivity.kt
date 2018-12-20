package com.dreamteam.monopoly

import android.content.res.Configuration
import android.graphics.drawable.LayerDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintLayout.*
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.game.board.cell.GameCellType
import com.dreamteam.monopoly.game.player.Player
import com.dreamteam.monopoly.game.player.PlayerActions
import com.dreamteam.monopoly.game.player.PlayerMoveCondition
import com.tapadoo.alerter.Alerter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_game.*
import maes.tech.intentanim.CustomIntent
import android.graphics.drawable.GradientDrawable
import com.dreamteam.monopoly.game.GameData.boardSizeModifier
import com.dreamteam.monopoly.game.player.PlayerType


class GameActivity : AppCompatActivity() {

    private var buttonThrowDices: Button? = null
    private var yesButton: Button? = null
    private var noButton: Button? = null
    private var question: Button? = null
    private var gameManager: GameManager = GameManager(this)
    private var cellButtons: ArrayList<ImageButton> = ArrayList(gameManager.mainBoard.gameWayLength)

    private var indexForBoard: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        buttonThrowDices = findViewById(R.id.buttonThrowCubes)
        yesButton = findViewById(R.id.YesButton)
        noButton = findViewById(R.id.NoButton)
        question = findViewById(R.id.DialogView)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.ConstraintLayout)
        val underTopLineGuideline = findViewById<Guideline>(R.id.UnderTopPartGuideline)
        val horizontalGuideline = findViewById<Guideline>(R.id.HorizontalGuideline)
        val verticalGuideline = findViewById<Guideline>(R.id.VerticalGuideline)


        val intent = intent
        val playersNames: ArrayList<String> = intent.getStringArrayListExtra("PlayersNames") //get info from AmountOfPlayers Activity


        val metrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(metrics)

        val boardSize: Int = ((if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) metrics.widthPixels else metrics.heightPixels) * boardSizeModifier).toInt()
        val cellWidth: Int = (boardSize / ((gameManager.mainBoard.gameWayLength / 4 - 1) + 2 * GameData.cellSidesModifier)).toInt()
        val cellHeight: Int = (cellWidth * GameData.cellSidesModifier).toInt()


        underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / boardSize /*metrics.heightPixels*/)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            horizontalGuideline.setGuidelinePercent((2 * cellHeight.toFloat() + 9 * cellWidth.toFloat()) / metrics.heightPixels)
        else {
            horizontalGuideline.setGuidelinePercent((2 * cellHeight.toFloat() + 9 * cellWidth.toFloat()) / metrics.heightPixels / 2)   // TODO
            verticalGuideline.setGuidelinePercent((2 * cellHeight.toFloat() + 9 * cellWidth.toFloat()) / metrics.widthPixels / 2)
        }
        createBoard(constraintLayout, cellHeight, cellWidth)
        startAssignment(playersNames)

        // players pos and marks setup
        for (i in 1..playersNames.size) {
            val myPlayerID = resources.getIdentifier("Player$i", "id", packageName)
            val playerImage = resources.getDrawable(resources
                    .getIdentifier("player$i", "drawable", packageName))
            val player = ImageView(this)
            player.layoutParams = LayoutParams(cellWidth / 2, cellWidth / 2)
            player.id = (myPlayerID)
            player.setImageDrawable(playerImage)
            constraintLayout.addView(player)
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            val myId = resources.getIdentifier("cell${1}", "id", packageName)
            if (i == 1 || i == 3) {
                constraintSet.connect(player.id, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
                constraintSet.connect(player.id, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
                if (i == 1) constraintSet.connect(player.id, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
                else constraintSet.connect(player.id, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
            }
            if (i == 2 || i == 4) {
                constraintSet.connect(player.id, ConstraintSet.TOP, myId, ConstraintSet.TOP, 0)
                constraintSet.connect(player.id, ConstraintSet.BOTTOM, myId, ConstraintSet.BOTTOM, 0)
                if (i == 2) constraintSet.connect(player.id, ConstraintSet.RIGHT, myId, ConstraintSet.RIGHT, 0)
                else constraintSet.connect(player.id, ConstraintSet.LEFT, myId, ConstraintSet.LEFT, 0)
            }
            constraintSet.applyTo(constraintLayout)
        }

        if (gameManager.getCurrentPlayer().type == PlayerType.AI)
            playerStartMoveAction()
        buttonThrowDices!!.setOnClickListener {
            playerStartMoveAction()
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
        if (gameManager.mainBoard.gameWay[gameManager.getCurrentPlayer().currentPosition - 1].info.cellType == GameCellType.COMPANY && //высвечивать купить/нет только если есть возможность купить
                gameManager.mainBoard.gameWay[gameManager.getCurrentPlayer().currentPosition - 1].owner == null) {
            yesButton!!.visibility = View.VISIBLE
            noButton!!.visibility = View.VISIBLE
            question!!.visibility = View.VISIBLE

            yesButton!!.isClickable = true
            noButton!!.isClickable = true
        } else {
            playersSwap()
        }

        yesButton!!.setOnClickListener {
            performBuyAction()
            yesNoButtonListener()
            Log.d("FindError", "yesPressed")
        }

        noButton!!.setOnClickListener {
            performStayAction()
            yesNoButtonListener()
        }
    }

    private fun performBuyAction() {
        gameManager.getCurrentPlayer().decision(PlayerActions.BUY)
        Log.d("FindError", "im in gameAct/performBuy")
        playersSwap()
    }

    private fun performStayAction() {
        gameManager.getCurrentPlayer().decision(PlayerActions.STAY)
        Log.d("FindError", "im in gameAct/performStay")
        playersSwap()
    }

    private fun playerStartMove() {
        val dices: Pair<Int, Int> = gameManager.getCurrentPlayer().throwDices()
        val cube1: ImageView = findViewById(R.id.cube1)
        val cube2: ImageView = findViewById(R.id.cube2)
        val drawCube1 = resources.getDrawable(resources
                .getIdentifier("dice${dices.first}", "drawable", packageName), null)      //gettind first cube image
        val drawCube2 = resources.getDrawable(resources
                .getIdentifier("dice${dices.second}", "drawable", packageName), null)     //gettind second cube image
        cube1.setImageDrawable(drawCube1)  //draw this pics
        cube2.setImageDrawable(drawCube2)

        buttonThrowDices!!.isEnabled = false    //make button throw dices unusable
        buttonThrowDices!!.visibility = View.INVISIBLE
    }

    private fun playersSwap() {
        cube1.setImageDrawable(null) //delete images dices pics
        cube2.setImageDrawable(null)
        updPlayerMoney(gameManager.getCurrentPlayer())
        gameManager.nextPlayerMove() //this code should be after action after throwing dice #Player.decision
        buttonThrowDices!!.isEnabled = true
        buttonThrowDices!!.visibility = View.VISIBLE
        if (gameManager.getCurrentPlayer().type == PlayerType.AI) {
            playerStartMoveAction()
        }
        Toasty.info(this, gameManager.getCurrentPlayer().name + " move", Toast.LENGTH_SHORT, true).show()
    }

    fun playerSetCellMark(index: Int) {
        //val neededCellID = resources.getIdentifier("cell${index + 1}", "id", packageName)
        //val neededCell = findViewById<ImageButton>(neededCellID)
        Log.d("playerIndex", gameManager.getCurrentPlayer().id.toString())
        val shape = resources.getDrawable(R.drawable.cellbglayer) as LayerDrawable
        val gradientDrawable = shape
                .findDrawableByLayerId(R.id.backgroundColor) as GradientDrawable
        Log.d("COLOR", gradientDrawable.toString())
        when (gameManager.getCurrentPlayer().id) {
            1 -> gradientDrawable.setColor(resources.getColor(R.color.Player1BackgroundColor))//neededCell.setBackgroundResource(R.drawable.player1cell)
            2 -> gradientDrawable.setColor(resources.getColor(R.color.Player2BackgroundColor))
            3 -> gradientDrawable.setColor(resources.getColor(R.color.Player3BackgroundColor))
            4 -> gradientDrawable.setColor(resources.getColor(R.color.Player4BackgroundColor))
        }
        cellButtons[gameManager.getCurrentPlayer().currentPosition - 1].background = shape
        // TODO - setup unique color on owned cell (spawn image above old one)
    }

    fun playerRemoveCellMark(index: Int) {
        // TODO - remove unique color on owned cell
    }

    @Override
    override fun finish() {
        super.finish()
        Alerter.hide()
        CustomIntent.customType(this, "up-to-bottom")
    }

    fun getGameManager(): GameManager = gameManager

    private fun yesNoButtonListener() {
        yesButton!!.visibility = View.INVISIBLE
        noButton!!.visibility = View.INVISIBLE
        question!!.visibility = View.INVISIBLE
        yesButton!!.isClickable = false
        noButton!!.isClickable = false
    }

    private fun startAssignment(playersNames: ArrayList<String>) //adding text/players on map
    {
        for (string in playersNames) {
            gameManager.addPlayer(string, PlayerType.AI)
            val playerStatsId = resources.getIdentifier("playerStats${gameManager.players.size}", "id", packageName)
            val playerStats: TextView = findViewById(playerStatsId)
            playerStats.text = string
            gameManager.getPlayerByName(string)!!.setPlayerID(gameManager.players.size)
            updPlayerMoney(gameManager.getPlayerByName(string)!!)
        }
    }

    private fun initPlayersPositions() {

    }

    private fun createBoard(constraintLayout: ConstraintLayout, cellHeight: Int, cellWidth: Int) { // LEFT = 1 RIGHT = 2 TOP = 3 BOTTOM = 4 START = 6 END = 7
        createCell(cellHeight, cellHeight, constraintLayout, 3, 3, 6, 6, 3, 3, true)

        while (indexForBoard < gameManager.mainBoard.gameWayLength / 4) {
            createCell(cellWidth, cellHeight, constraintLayout, 6, 7, 3, 3, 4, 4)
        }
        createCell(cellHeight, cellHeight, constraintLayout, 6, 7, 3, 3, 4, 4)

        while (indexForBoard < gameManager.mainBoard.gameWayLength / 2) {
            createCell(cellHeight, cellWidth, constraintLayout, 6, 6, 3, 4, 7, 7)
        }
        createCell(cellHeight, cellHeight, constraintLayout, 6, 6, 3, 4, 7, 7)

        while (indexForBoard < 3 * gameManager.mainBoard.gameWayLength / 4) {
            createCell(cellWidth, cellHeight, constraintLayout, 7, 6, 3, 3, 4, 4)
        }
        createCell(cellHeight, cellHeight, constraintLayout, 7, 6, 3, 3, 4, 4)

        while (indexForBoard < gameManager.mainBoard.gameWayLength) {
            createCell(cellHeight, cellWidth, constraintLayout, 6, 6, 4, 3, 7, 7)
        }
    }

    private fun createCell(width: Int, height: Int, layout: ConstraintLayout, From1: Int, To1: Int, From2: Int, To2: Int, From3: Int, To3: Int, start: Boolean = false) {
        val thisButtonID = resources.getIdentifier("cell${indexForBoard + 1}", "id", packageName)
        val previousButtonID = resources.getIdentifier("cell$indexForBoard", "id", packageName)
        val button = ImageButton(this)
        button.layoutParams = LayoutParams(width, height)
        button.id = (thisButtonID)
        button.setOnClickListener(showInfoClick)
        button.background = resources.getDrawable(resources
                .getIdentifier("cellbglayer", "drawable", packageName), null)
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
        indexForBoard++
    }

    private val showInfoClick = View.OnClickListener { view ->
        val i = cellButtons.indexOf(view)
        val name: String = GameData.boardGameCells[i].info.name
        val costBuy = GameData.boardGameCells[i].info.cost.costBuy
        val costSell = GameData.boardGameCells[i].info.cost.costSell
        val charge = GameData.boardGameCells[i].info.cost.costCharge
        val nameSpace: TextView = findViewById(R.id.cellName)
        val buySpace: TextView = findViewById(R.id.cellCost)
        val sellSpace: TextView = findViewById(R.id.cellSell)
        val chargeSpace: TextView = findViewById(R.id.cellCharge)

        nameSpace.text = "Name: $name"
        buySpace.text = "Buy: $costBuy"
        sellSpace.text = "Sell: $costSell"
        chargeSpace.text = "Charge: $charge"
    }

    private fun updPlayerMoney(player: Player) {
        val playerMoneyId = resources.getIdentifier("playerMoney${gameManager.getPlayerByName(player.name)!!.id}", "id", packageName)
        val playerMoney: TextView = findViewById(playerMoneyId)
        playerMoney.text = gameManager.getPlayerByName(player.name)!!.money.toString()
    }

    fun endGameAction(winner: Player) {
        //TODO swap to results activity
    }
}
