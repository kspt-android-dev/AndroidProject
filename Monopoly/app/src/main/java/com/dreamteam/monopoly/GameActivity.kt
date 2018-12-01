package com.dreamteam.monopoly

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.dreamteam.monopoly.game.GameManager
import com.dreamteam.monopoly.helpers.showToast


class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var buttonThrowDices: Button? = null
    private var gameManager: GameManager = GameManager()

    //image button
    private var cellButtons: ArrayList<Button> = ArrayList<Button>(44)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameManager.AddPlayer("Alesha")

        buttonThrowDices = findViewById(R.id.buttonThrowCubes)
        //adding a click listener
        buttonThrowDices!!.setOnClickListener { v ->
            val dices: Pair<Int, Int> = gameManager.getCurrentPlayer().throwCubiks()
            val cube1: ImageView = findViewById(R.id.cube1)
            val cube2: ImageView = findViewById(R.id.cube2)
            val drawCube1 = resources.getDrawable(resources
                    .getIdentifier("dice${dices.first}", "drawable", packageName))
            val drawCube2 = resources.getDrawable(resources
                    .getIdentifier("dice${dices.second}", "drawable", packageName))
            cube1.setImageDrawable(drawCube1)
            cube2.setImageDrawable(drawCube2)

            showToast(v!!, "Piu")
        }

    }

    @Override
    override fun onClick(v: View) {
        //starting game activity
        intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
