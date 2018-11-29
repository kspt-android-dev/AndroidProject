package com.dreamteam.monopoly

import android.content.Context
import android.view.SurfaceView


class GameView(context: Context) : SurfaceView(context), Runnable {

    //boolean variable to track if the game is playing or not
    @Volatile
    internal var playing: Boolean = false

    //the game thread
    private var gameThread: Thread? = null

    override fun run() {
        while (playing) {
            //to update the frame
            update()

            //to draw the frame
            draw()

            //to control
            control()
        }
    }


    private fun update() {

    }

    private fun draw() {

    }

    private fun control() {
        try {
            //gameThread!!.sleep(17)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    fun pause() {
        //when the game is paused
        //setting the variable to false
        playing = false
        try {
            //stopping the thread
            gameThread!!.join()
        } catch (e: InterruptedException) {
        }

    }

    fun resume() {
        //when the game is resumed
        //starting the thread again
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }
}