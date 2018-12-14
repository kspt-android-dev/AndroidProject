package com.dreamteam.monopoly

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintLayout.*
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import com.dreamteam.monopoly.game.GameData
import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.game.player.Player
import com.dreamteam.monopoly.game.player.PlayerActions
import com.dreamteam.monopoly.game.player.PlayerMoveCondition
import com.tapadoo.alerter.Alerter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_game.*
import maes.tech.intentanim.CustomIntent



class GameActivity : AppCompatActivity() {

    private var buttonThrowDices: Button? = null
    private var yesButton: Button? = null
    private var noButton: Button? = null
    private var question: Button? = null
    private var gameManager: GameManager = GameManager(this)
    private var cellButtons: ArrayList<ImageButton> = ArrayList(gameManager.mainBoard.gameWayLength)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        buttonThrowDices = findViewById(R.id.buttonThrowCubes) as Button
        yesButton = findViewById(R.id.YesButton) as Button
        noButton = findViewById(R.id.NoButton) as Button
        question = findViewById(R.id.DialogView) as Button
        val constraintLayout = findViewById(R.id.ConstraintLayout) as ConstraintLayout
        val underTopLineGuideline = findViewById(R.id.UnderTopPartGuideline) as Guideline
        val horizotnalGuidleline = findViewById(R.id.HorizontalGuideline) as Guideline


        val intent = intent
        val playersNames: ArrayList<String> = intent.getStringArrayListExtra("PlayersNames") //get info from AmountOfPlayers Activity


        val metrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(metrics)
        val boardWidth: Int = (metrics.widthPixels * GameData.boardSizeModifier).toInt()
        val boardHeight: Int = boardWidth
        var cellWidth: Int = (boardWidth / ((gameManager.mainBoard.gameWayLength / 4 - 2) + 2 * GameData.cellSidesModifier)).toInt()
        var cellHeight: Int = (cellWidth * GameData.cellSidesModifier).toInt()


        underTopLineGuideline.setGuidelinePercent(cellHeight.toFloat() / metrics.heightPixels)
        horizotnalGuidleline.setGuidelinePercent((2*cellHeight.toFloat() + 9 * cellWidth.toFloat())/metrics.heightPixels)
        createBoard(constraintLayout , cellHeight , cellWidth)
        startAssignment(playersNames)

        for (i in 1..playersNames.size) {
            val myPlayerID = getResources().getIdentifier("Player$i", "id", packageName)
            val playerImage = resources.getDrawable(resources
                    .getIdentifier("player${i}", "drawable", packageName))
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




        buttonThrowDices!!.setOnClickListener {
            playerStartMove()
            if (gameManager.getCurrentPlayer().analyze() == PlayerMoveCondition.COMPLETED) {
                val handler = Handler()
                handler.postDelayed({
                    playersSwap()
                }, GameData.swapDicesDelay)
            } else playerActionRequest()
        }
    }

    private fun playerActionRequest() {
        yesButton!!.visibility = View.VISIBLE
        noButton!!.visibility = View.VISIBLE
        question!!.visibility = View.VISIBLE

        yesButton!!.isClickable = true
        noButton!!.isClickable = true

        yesButton!!.setOnClickListener {
            performBuyAction()
            yesNoButtonListner()
        }

        noButton!!.setOnClickListener {
            performStayAction()
            yesNoButtonListner()
        }


        // TODO add listeners to choice buttons
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
        val drawCube1 = resources.getDrawable(resources
                .getIdentifier("dice${dices.first}", "drawable", packageName),null)      //gettind first cube image
        val drawCube2 = resources.getDrawable(resources
                .getIdentifier("dice${dices.second}", "drawable", packageName),null)     //gettind second cube image
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
        Toasty.info(this, gameManager.getCurrentPlayer().name + " move", Toast.LENGTH_SHORT, true).show()
    }


    @Override
    override fun finish() {
        super.finish()
        Alerter.hide()
        CustomIntent.customType(this, "up-to-bottom")
    }

    fun getGameManager(): GameManager = gameManager

    private fun yesNoButtonListner()
    {
        yesButton!!.visibility = View.INVISIBLE
        noButton!!.visibility = View.INVISIBLE
        question!!.visibility = View.INVISIBLE
        yesButton!!.isClickable = false
        noButton!!.isClickable = false
    }

    private fun startAssignment(playersNames: ArrayList<String>) //adding text/players on map
    {
        for (string in playersNames) {
            gameManager.addPlayer(string)
            val playerStatsId = resources.getIdentifier("playerStats${gameManager.players.size}", "id", packageName)
            val playerStats: TextView = findViewById(playerStatsId)
            playerStats.text = string
            gameManager.getPlayerByName(string)!!.setPlayerID(gameManager.players.size)
            updPlayerMoney(gameManager.getPlayerByName(string)!!)
        }

    }

    private fun createBoard(constraintLayout : ConstraintLayout , cellHeight:Int, cellWidth:Int) {
        var index = 0
        var previousButtonID = getResources().getIdentifier("cell${index}", "id", packageName)
        var thisButtonID = getResources().getIdentifier("cell${index + 1}", "id", packageName)

        var button = ImageButton(this)
        button.layoutParams = LayoutParams(cellHeight, cellHeight)
        button.id = (thisButtonID)
        button.setOnClickListener(ShowInfoClick)
        button.setBackgroundResource(R.drawable.cell_borders_up)
        constraintLayout.addView(button)
        cellButtons.add(button)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(R.id.UnderTopPartGuideline, ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, cellHeight)
        constraintSet.connect(button.id, ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, 0)
        constraintSet.connect(button.id, ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 0)
        constraintSet.applyTo(constraintLayout)
        index++
        while (index < gameManager.mainBoard.gameWayLength / 4) {
            val button = ImageButton(this)
            button.layoutParams = LayoutParams(cellWidth, cellHeight)
            previousButtonID = getResources().getIdentifier("cell${index}", "id", packageName)
            thisButtonID = getResources().getIdentifier("cell${index + 1}", "id", packageName)
            button.id = (thisButtonID)
            button.setOnClickListener(ShowInfoClick)
            button.setBackgroundResource(R.drawable.cell_borders_up)
            constraintLayout.addView(button)
            cellButtons.add(button)
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(button.id, ConstraintSet.START, previousButtonID, ConstraintSet.END, 0)
            constraintSet.connect(button.id, ConstraintSet.TOP, previousButtonID, ConstraintSet.TOP, 0)
            constraintSet.connect(button.id, ConstraintSet.BOTTOM, previousButtonID, ConstraintSet.BOTTOM, 0)
            constraintSet.applyTo(constraintLayout)
            index++
        }
        //create corner TODO
        button = ImageButton(this)
        button.layoutParams = LayoutParams(cellHeight, cellHeight)
        previousButtonID = getResources().getIdentifier("cell${index}", "id", packageName)
        thisButtonID = getResources().getIdentifier("cell${index + 1}", "id", packageName)
        button.id = (thisButtonID)
        button.setOnClickListener(ShowInfoClick)
        button.setBackgroundResource(R.drawable.cell_borders_up)
        constraintLayout.addView(button)
        cellButtons.add(button)
        constraintSet.clone(constraintLayout)
        constraintSet.connect(button.id, ConstraintSet.START, previousButtonID, ConstraintSet.END, 0)
        constraintSet.connect(button.id, ConstraintSet.TOP, previousButtonID, ConstraintSet.TOP, 0)
        constraintSet.connect(button.id, ConstraintSet.BOTTOM, previousButtonID, ConstraintSet.BOTTOM, 0)
        constraintSet.applyTo(constraintLayout)
        index++
        while (index < gameManager.mainBoard.gameWayLength / 2) {
            val button = ImageButton(this)
            button.layoutParams = LayoutParams(cellHeight, cellWidth)
            previousButtonID = getResources().getIdentifier("cell${index}", "id", packageName)
            thisButtonID = getResources().getIdentifier("cell${index + 1}", "id", packageName)
            button.id = (thisButtonID)
            button.setOnClickListener(ShowInfoClick)
            button.setBackgroundResource(R.drawable.cell_borders_up)
            constraintLayout.addView(button)
            cellButtons.add(button)
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(button.id, ConstraintSet.START, previousButtonID, ConstraintSet.START, 0)
            constraintSet.connect(button.id, ConstraintSet.TOP, previousButtonID, ConstraintSet.BOTTOM, 0)
            constraintSet.connect(button.id, ConstraintSet.END, previousButtonID, ConstraintSet.END, 0)
            constraintSet.applyTo(constraintLayout)
            index++
        }
        //create corner TODO
        button = ImageButton(this)
        button.layoutParams = LayoutParams(cellHeight, cellHeight)
        previousButtonID = getResources().getIdentifier("cell${index}", "id", packageName)
        thisButtonID = getResources().getIdentifier("cell${index + 1}", "id", packageName)
        button.id = (thisButtonID)
        button.setOnClickListener(ShowInfoClick)
        button.setBackgroundResource(R.drawable.cell_borders_up)
        constraintLayout.addView(button)
        cellButtons.add(button)
        constraintSet.clone(constraintLayout)
        constraintSet.connect(button.id, ConstraintSet.START, previousButtonID, ConstraintSet.START, 0)
        constraintSet.connect(button.id, ConstraintSet.TOP, previousButtonID, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(button.id, ConstraintSet.END, previousButtonID, ConstraintSet.END, 0)
        constraintSet.applyTo(constraintLayout)
        index++
        while (index < 3 * gameManager.mainBoard.gameWayLength / 4) {
            // down cell create TODO
            val button = ImageButton(this)
            button.layoutParams = LayoutParams(cellWidth, cellHeight)
            previousButtonID = getResources().getIdentifier("cell${index}", "id", packageName)
            thisButtonID = getResources().getIdentifier("cell${index + 1}", "id", packageName)
            button.id = (thisButtonID)
            button.setOnClickListener(ShowInfoClick)
            button.setBackgroundResource(R.drawable.cell_borders_up)
            constraintLayout.addView(button)
            cellButtons.add(button)
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(button.id, ConstraintSet.END, previousButtonID, ConstraintSet.START, 0)
            constraintSet.connect(button.id, ConstraintSet.TOP, previousButtonID, ConstraintSet.TOP, 0)
            constraintSet.connect(button.id, ConstraintSet.BOTTOM, previousButtonID, ConstraintSet.BOTTOM, 0)
            constraintSet.applyTo(constraintLayout)
            index++
        }
        //create corner TODO
        button = ImageButton(this)
        button.layoutParams = LayoutParams(cellHeight, cellHeight)
        previousButtonID = resources.getIdentifier("cell$index", "id", packageName)
        thisButtonID = resources.getIdentifier("cell${index + 1}", "id", packageName)
        button.id = (thisButtonID)
        button.setOnClickListener(ShowInfoClick)
        button.setBackgroundResource(R.drawable.cell_borders_up)
        constraintLayout.addView(button)
        cellButtons.add(button)
        constraintSet.clone(constraintLayout)
        constraintSet.connect(button.id, ConstraintSet.END, previousButtonID, ConstraintSet.START, 0)
        constraintSet.connect(button.id, ConstraintSet.TOP, previousButtonID, ConstraintSet.TOP, 0)
        constraintSet.connect(button.id, ConstraintSet.BOTTOM, previousButtonID, ConstraintSet.BOTTOM, 0)
        constraintSet.applyTo(constraintLayout)
        index++
        while (index < gameManager.mainBoard.gameWayLength) {
            // left cell create TODO
            val button = ImageButton(this)
            button.layoutParams = LayoutParams(cellHeight, cellWidth)
            previousButtonID = resources.getIdentifier("cell$index", "id", packageName)
            thisButtonID = resources.getIdentifier("cell${index + 1}", "id", packageName)
            button.id = (thisButtonID)
            button.setOnClickListener(ShowInfoClick)
            button.setBackgroundResource(R.drawable.cell_borders_up)
            constraintLayout.addView(button)
            cellButtons.add(button)
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayout)
            constraintSet.connect(button.id, ConstraintSet.START, previousButtonID, ConstraintSet.START, 0)
            constraintSet.connect(button.id, ConstraintSet.BOTTOM, previousButtonID, ConstraintSet.TOP, 0)
            constraintSet.connect(button.id, ConstraintSet.END, previousButtonID, ConstraintSet.END, 0)
            constraintSet.applyTo(constraintLayout)
            index++
        }
    }

    val ShowInfoClick = View.OnClickListener { view ->
        val i = cellButtons.indexOf(view)
        Log.d("TAG", i.toString())
        val name : String = GameData.boardGameCells[i].info.name
        val costBuy = GameData.boardGameCells[i].info.cost.costBuy
        val costSell = GameData.boardGameCells[i].info.cost.costSell
        val charge = GameData.boardGameCells[i].info.cost.costCharge
        val nameSpace : TextView = findViewById(R.id.cellName)
        val buySpace : TextView = findViewById(R.id.cellCost)
        val sellSpace : TextView = findViewById(R.id.cellSell)
        val chargeSpace : TextView = findViewById(R.id.cellCharge)

        nameSpace.text = "Name: $name"
        buySpace.text = "Buy: $costBuy"
        sellSpace.text = "Sell: $costSell"
        chargeSpace.text = "Charge: $charge"


    }

    private fun updPlayerMoney(player:Player) {
        val playerMoneyId = resources.getIdentifier("playerMoney${gameManager.getPlayerByName(player.name)!!.id}", "id", packageName)
        val playerMoney: TextView = findViewById(playerMoneyId)
        playerMoney.text = gameManager.getPlayerByName(player.name)!!.money.toString()
    }
}
