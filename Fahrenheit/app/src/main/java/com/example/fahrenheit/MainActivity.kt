package com.example.fahrenheit

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
    var isLight = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = CustomRecyclerAdapter(this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(baseContext)
        intentMusicService = Intent(this, MusicService::class.java)
        intentMusicService.putExtra("MUSIC", R.raw.main_theme)
        createGame()
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
        scheduledExecutorService.schedule(myTimerTask, 7, TimeUnit.SECONDS)
    }

    fun getFastNext() {
        count++
        scheduledExecutorService.schedule(myTimerTask, 0, TimeUnit.SECONDS)
    }

    override fun onResume() {
        super.onResume()
        startService(intentMusicService)
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        isLight = pref.getBoolean("LIGHT", false)
        isLightMode()
    }

    private fun createGame() {
        val pref = getSharedPreferences("LIGHT_MOD", Context.MODE_PRIVATE)
        val isCans = pref.getBoolean("CENSORSHIP", false)
        val stream = if (isCans)
            assets.open("game/game_no_censure.txt")
        else
            assets.open("game/game.txt")
        val game = parseFile(stream)
        scheduledExecutorService = Executors.newScheduledThreadPool(1)
        myTimerTask = MyTimerTask(game)
        scheduledExecutorService.schedule(myTimerTask, 0, TimeUnit.SECONDS)
    }

    private fun isLightMode() {
        if (isLight) {
            recycler_view.setBackgroundColor(Color.WHITE)
        } else {
            recycler_view.setBackgroundColor(Color.BLACK)
        }
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
                    if (gameCase.type != TypeCase.PROGRAM_LOGIC && gameCase.type != TypeCase.BRIDGE) {
                        adapter.push(gameCase)
                        recycler_view.smoothScrollToPosition(adapter.itemCount - 1)
                    }
                    when (gameCase.type) {
                        TypeCase.BRIDGE -> {
                            val bridgeCase = gameCase as BridgeCase
                            bridgeStep(bridgeCase.targetId)
                        }
                        TypeCase.PROGRAM_LOGIC -> {
                            val programCase = gameCase as ProgramCase
                            when (programCase.eventType) {
                                ProgramType.QUIT -> {

                                }
                                ProgramType.MUSIC -> {
                                    stopService(intentMusicService)
                                    intentMusicService.putExtra("MUSIC", R.raw.anxious_theme)
                                    startService(intentMusicService)
                                    getFastNext()
                                }
                                else -> {
                                    programLogic(programCase.programEvents!!)
                                    getFastNext()
                                }
                            }
                        }
                        TypeCase.QUESTION -> {
                            getFastNext()
                        }
                        TypeCase.BUTTON_TEXT -> {
                            recycler_view.smoothScrollToPosition(adapter.itemCount)
                        }
                        else -> getNext()
                    }
                }
            }
        }
    }
}
