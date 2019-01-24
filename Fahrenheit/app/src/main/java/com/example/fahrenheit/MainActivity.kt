package com.example.fahrenheit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CustomRecyclerAdapter

    var count = 0

    private lateinit var myTimerTask: MyTimerTask

    private lateinit var scheduledExecutorService: ScheduledExecutorService

    private lateinit var intentMusicService: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = CustomRecyclerAdapter(this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(baseContext)
        val stream = assets.open("game/game.txt")
        val game = parseFile(stream)
        intentMusicService = Intent(this, MusicService::class.java)
        scheduledExecutorService = Executors.newScheduledThreadPool(1)
        myTimerTask = MyTimerTask(game)
        scheduledExecutorService.schedule(myTimerTask, 0, TimeUnit.SECONDS)
    }

    fun bridgeStep(targetId: Int) {
        count = targetId - 1
        scheduledExecutorService.schedule(myTimerTask, 0, TimeUnit.SECONDS)
    }

    fun programLogic(programEvents: List<Int>) {
        count = programEvents.first()
        scheduledExecutorService.schedule(myTimerTask, 0, TimeUnit.SECONDS)
    }

    fun event(targetId: Int) {
        if (targetId < 0) {
            onBackPressed()
        } else {
            count = targetId - 1
            scheduledExecutorService.schedule(myTimerTask, 0, TimeUnit.SECONDS)
        }
    }

    fun getNext() {
        count++
        scheduledExecutorService.schedule(myTimerTask, 10, TimeUnit.SECONDS)
    }

    fun getFastNext() {
        count++
        scheduledExecutorService.schedule(myTimerTask, 0, TimeUnit.SECONDS)
    }

    override fun onResume() {
        super.onResume()
        startService(intentMusicService)
    }

    override fun onPause() {
        super.onPause()
        stopService(intentMusicService)
    }

    inner class MyTimerTask(private val game: List<GameCase?>) : TimerTask() {
        override fun run() {
            runOnUiThread {
                val gameCase = game[count]
                if (gameCase == null) getFastNext()
                else {
                    adapter.push(gameCase)
                    recycler_view.smoothScrollToPosition(adapter.itemCount - 1)
                    when (gameCase.type) {
                        TypeCase.BRIDGE -> {
                            val bridgeCase = gameCase as BridgeCase
                            bridgeStep(bridgeCase.targetId)
                        }
                        TypeCase.PROGRAM_LOGIC -> {
                            val programCase = gameCase as ProgramCase
                            programLogic(programCase.programEvents)
                        }
                        TypeCase.QUESTION -> {
                            getFastNext()
                        }
                        TypeCase.BUTTON_TEXT -> {

                        }
                        else -> getNext()
                    }
                }
            }
        }
    }
}
