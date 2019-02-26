package com.example.anew.tictactoe

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    var sym: String? = null
    var gc = GameController()
    var buttons:Array<Button> = emptyArray()
    var scoreString:TextView? = null
    var intenMusic: Intent? = null
    var flag = false

    fun onClick(x: Int, y: Int, btn: Button) {
        try {
            val player = gc.player
            gc.makeTurn(x, y)
            btn.text = if (player == Entity.CROSSES) "x" else "0"
            var winner = gc.whoWon()
            if (winner != Entity.EMPTY) {
                onGameEnd(winner)
            }
            if((winner == Entity.EMPTY) && (gc.countCelss == buttons.size)){
                endGameForDraw()
            }

        }catch (e: IllegalStateException){
            val toast = Toast.makeText(applicationContext, "The cell is already taken!!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 50)
            toast.view.setBackgroundColor(Color.GRAY)//"#616161".toInt())
            toast.show()
        }
    }

    var crosses = 0
    var noughts = 0

    fun endGameForDraw(){
        buttons.forEach { button -> button.text = sym  }
        val toast = Toast.makeText(applicationContext, "Draw", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 50)
        toast.view.setBackgroundColor(Color.GRAY)//"#616161".toInt())
        toast.show()
        scoreString?.setText("Crosses $crosses:$noughts Noughts ")
        gc = GameController()
    }
    fun onGameEnd(winner: Entity) {
        if (winner == Entity.CROSSES) {
            crosses += 1
        } else noughts += 1
        buttons.forEach { button -> button.text = sym  }
        val toast = Toast.makeText(applicationContext, if (winner == Entity.CROSSES) "Crosses win!" else "Noughts win!", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 50)
        toast.view.setBackgroundColor(Color.GRAY)//"#616161".toInt())
        toast.show()
        scoreString?.setText("Crosses $crosses:$noughts Noughts ")
        print("You won!")
        gc = GameController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        startMusic()
        buttons = arrayOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
        scoreString = score
        resetGame(savedInstanceState)
        buttons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    onClick(
                        index % 3,
                        index / 3,
                        button
                    )
                }
            }

    }

    fun resetGame (savedInstanceState: Bundle?){
        if (savedInstanceState != null){
            val array = savedInstanceState.getStringArray("arrayString")
            gc.countCelss = savedInstanceState.getInt("countCell")
            when(savedInstanceState.getInt("playerId")){
                1 -> gc.player = Entity.CROSSES
                2-> gc.player = Entity.NOUGHTS
            }
            for ( i in 0 until buttons.size){
                 buttons[i].text = array[i]
            }
            val arrayId1 = savedInstanceState.getIntegerArrayList("arrayId1")
            val arrayId2 = savedInstanceState.getIntegerArrayList("arrayId2")
            val arrayId3 = savedInstanceState.getIntegerArrayList("arrayId3")
            for (i in 0 until 3){
                when(arrayId1[i]){
                    0 -> gc.grid[0][i] = Entity.EMPTY
                    1 -> gc.grid[0][i] = Entity.CROSSES
                    2 -> gc.grid[0][i] = Entity.NOUGHTS
                }
            }
            for (i in 0 until 3){
                when(arrayId2[i]){
                    0 -> gc.grid[1][i] = Entity.EMPTY
                    1 -> gc.grid[1][i] = Entity.CROSSES
                    2 -> gc.grid[1][i] = Entity.NOUGHTS
                }
            }
            for (i in 0 until 3){
                when(arrayId3[i]){
                    0 -> gc.grid[2][i] = Entity.EMPTY
                    1 -> gc.grid[2][i] = Entity.CROSSES
                    2 -> gc.grid[2][i] = Entity.NOUGHTS
                }
            }
            crosses = savedInstanceState.getInt("crosses")
            noughts = savedInstanceState.getInt("noughts")
            scoreString?.setText("Crosses $crosses:$noughts Noughts ")
        }
    }

    override fun onSaveInstanceState(saveState: Bundle?) {
        super.onSaveInstanceState(saveState)
        var array : Array<String?> = arrayOfNulls(buttons.size)
        var arrayId1 : ArrayList<Int?> = arrayListOf(0,0,0)
        var arrayId2 : ArrayList<Int?> = arrayListOf(0,0,0)
        var arrayId3 : ArrayList<Int?> = arrayListOf(0,0,0)
        for ( i in 0 until buttons.size){
            array[i] = buttons[i].text.toString()
        }
        for (i in 0 until 3){
            arrayId1[i] = gc.grid[0][i].id
        }
        for (i in 0 until 3){
            arrayId2[i] = gc.grid[1][i].id
        }
        for (i in 0 until 3){
            arrayId3[i] = gc.grid[2][i].id
        }
        saveState?.putStringArray("arrayString", array)
        saveState?.putInt("countCell", gc.countCelss)
        saveState?.putInt("playerId", gc.player.id)
        saveState?.putIntegerArrayList("arrayId1", arrayId1)
        saveState?.putIntegerArrayList("arrayId2", arrayId2)
        saveState?.putIntegerArrayList("arrayId3", arrayId3)
        saveState?.putInt("crosses", crosses)
        saveState?.putInt("noughts", noughts)
    }

    fun startMusic(){
        if(intent.extras.getBoolean("Flag")){
            flag = true
            intenMusic = Intent(this, Sound::class.java)
            startService(intenMusic)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (flag)stopService(intenMusic)
    }
    override fun onPause() {
        super.onPause()
        if(flag)stopService(intenMusic)

    }
    override fun onRestart() {
        super.onRestart()
        if(flag)startService(intenMusic)
    }

}
