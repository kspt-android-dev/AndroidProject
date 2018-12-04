package com.dreamteam.monopoly

import android.content.Intent
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.game.player.Player
import com.dreamteam.monopoly.helpers.makeTinyAlert
import com.dreamteam.monopoly.helpers.showToast
import com.tapadoo.alerter.Alerter
import maes.tech.intentanim.CustomIntent


class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var buttonThrowDices: Button? = null
    private var gameManager: GameManager = GameManager()

    //image button
    private var cellButtons: ArrayList<Button> = ArrayList<Button>(44)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val intent = intent
        val playersNames : ArrayList<String> = intent.getStringArrayListExtra("PlayersNames")
        for (string in playersNames)
        {
            gameManager.addPlayer(string)
            for (i in 1..playersNames.size)      //making visible only players images
            {
                val myPlayer = getResources().getIdentifier("Player${i}", "id", packageName)
                val PlayerImage : ImageView = findViewById(myPlayer)
                PlayerImage.visibility = View.VISIBLE
            }
        }

        buttonThrowDices = findViewById(R.id.buttonThrowCubes)
        //adding a click listener
        buttonThrowDices!!.setOnClickListener { v ->
            val dices: Pair<Int, Int> = gameManager.getCurrentPlayer().throwDices(this)
            val cube1: ImageView = findViewById(R.id.cube1)
            val cube2: ImageView = findViewById(R.id.cube2)
            val drawCube1 = resources.getDrawable(resources
                    .getIdentifier("dice${dices.first}", "drawable", packageName))
            val drawCube2 = resources.getDrawable(resources
                    .getIdentifier("dice${dices.second}", "drawable", packageName))
            cube1.setImageDrawable(drawCube1)
            cube2.setImageDrawable(drawCube2)

            buttonThrowDices!!.isEnabled = false;
            buttonThrowDices!!.visibility = View.INVISIBLE


            val handler = Handler()
            handler.postDelayed({
                cube1.setImageDrawable(null)
                cube2.setImageDrawable(null)
                Log.d("###CurrentPlayerIndex", gameManager.currentPlayerIndex.toString())
                Log.d("###SumOfDices", (dices.first + dices.second).toString())
                gameManager.nextPlayerMove() //this code should be after action after throwing dice #Player.decision
                buttonThrowDices!!.isEnabled = true
                buttonThrowDices!!.visibility = View.VISIBLE
                // Alerter.hide() // mb after in some seconds
            }, 2000)
            showToast(v!!, "Piu")
            makeTinyAlert(this, gameManager.getCurrentPlayer().name + " move")
        }
    }

    @Override
    override fun onClick(v: View) {
        //starting game activity
        intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    @Override
    override fun finish() {
        super.finish()
        Alerter.hide()
        CustomIntent.customType(this, "up-to-bottom")
    }

    fun getGameManager(): GameManager = gameManager
}
