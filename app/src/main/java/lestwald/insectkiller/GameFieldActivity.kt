package lestwald.insectkiller

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.*
import java.util.Random
import java.util.Timer
import java.util.TimerTask
import kotlin.math.*

class GameFieldActivity : AppCompatActivity() {
    private lateinit var gameFieldLayout: ConstraintLayout
    private lateinit var topBarLayout: ConstraintLayout
    private lateinit var scoreTextView: TextView
    private lateinit var healthBar: ProgressBar
    private lateinit var pizza: ImageView
    private lateinit var pauseButton: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var soundPool: SoundPool
    private val timerForUpdate = Timer()
    private var gameFieldWidth = 0
    private var gameFieldHeight = 0
    private var score = 0
    private var health = MAX_HEALTH
    private var soundId = 0
    private var pizzaRadius = 0
    private var isPaused = false
    private var sound = true
    private var difficulty = STANDARD_DIFFICULTY
    private var currentLadyBugCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_field)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sound = sharedPreferences.getBoolean("sound", true)
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        difficulty = sharedPreferences.getString("difficulty", "2").toInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = SoundPool.Builder().setMaxStreams(SOUND_POOL_MAX_STREAMS).build()
        } else {
            @Suppress("DEPRECATION")
            soundPool = SoundPool(SOUND_POOL_MAX_STREAMS, AudioManager.STREAM_MUSIC, 0)
        }
        soundId = soundPool.load(this, R.raw.smashing, 1)

        gameFieldLayout = findViewById(R.id.game_field)
        topBarLayout = findViewById(R.id.top_bar)
        scoreTextView = findViewById(R.id.score)
        scoreTextView.text = "$score"
        healthBar = findViewById(R.id.health_bar)
        pizza = findViewById(R.id.pizza)
        pizza.setImageLevel(health.toInt())
        pizzaRadius = resources.getDimension(R.dimen.pizza_size).toInt() / 2
        pauseButton = findViewById(R.id.pause_button)
        pauseButton.setOnClickListener {
            isPaused = if (!isPaused) {
                pauseButton.setBackgroundResource(R.drawable.resume_button)
                true
            } else {
                pauseButton.setBackgroundResource(R.drawable.pause_button)
                false
            }
        }

        gameFieldLayout.post {
            gameFieldWidth = gameFieldLayout.width
            gameFieldHeight = gameFieldLayout.height
            update()
        }
    }

    override fun onDestroy() {
        soundPool.release()
        timerForUpdate.cancel()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("score", score)
        outState.putFloat("health", health)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        score = savedInstanceState.getInt("score")
        scoreTextView.text = "$score"
        health = savedInstanceState.getFloat("health")
        pizza.setImageLevel(health.toInt())
    }

    @SuppressLint("ClickableViewAccessibility")
    fun addInsect(insect: Insect) {
        insect.setOnTouchListener { it, event ->
            if (!isPaused && event.action == MotionEvent.ACTION_DOWN) {
                if (sound) soundPool.play(soundId, SOUND_POOL_VOLUME, SOUND_POOL_VOLUME, 1, 0, 1F)
                if (it is Insect) {
                    gameFieldLayout.removeView(it)
                    score += it.tapCost
                    scoreTextView.text = "$score"
                    if (it.type == Insect.InsectType.LADYBUG) currentLadyBugCount--
                }
            }
            true
        }
        gameFieldLayout.addView(insect)
    }

    private fun generateInsects(amount: Int, type: Insect.InsectType) {
        val centerX = (gameFieldWidth - type.width) / 2
        val centerY = (gameFieldHeight - type.height) / 2
        val radius = Math.sqrt(Math.pow(centerX.toDouble(), 2.0) + Math.pow(centerY.toDouble(), 2.0)) + max(type.width, type.height) * 2
        for (i in 0 until amount) {
            val startX = centerX.toDouble() - radius + Math.random() * 2.0 * radius
            val startY = Math.pow(-1.0, (Random().nextInt(2) + 1).toDouble()) * Math.sqrt(Math.pow(radius, 2.0) - Math.pow(startX - centerX.toDouble(), 2.0)) + centerY.toDouble()
            val endX: Double
            val endY: Double
            if (type == Insect.InsectType.LADYBUG) {
                endX = centerX.toDouble() - radius + Math.random() * 2.0 * radius
                endY = Math.pow(-1.0, (Random().nextInt(2) + 1).toDouble()) * Math.sqrt(Math.pow(radius, 2.0) - Math.pow(endX - centerX.toDouble(), 2.0)) + centerY.toDouble()
            } else {
                val angle = atan2((startY - centerY), (centerX - startX))
                endX = centerX - (pizzaRadius + type.height * 0.25F) * cos(angle)
                endY = centerY + (pizzaRadius + type.height * 0.25F) * sin(angle)
            }
            val insect = Insect(this, startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), type)
            insect.speed *= 1 + (difficulty - 1).toFloat() / 4
            addInsect(insect)
            insect.animateInsect()
        }
    }

    private fun update() {
        val content = this
        val ladyBugCount = difficulty * LADY_BUG_COUNT_COEFFICIENT
        var div = DIFFICULTY_COEFFICIENT - difficulty
        timerForUpdate.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (isPaused) {
                    val childCount = gameFieldLayout.childCount
                    for (k in 0 until childCount) {
                        val child = gameFieldLayout.getChildAt(k)
                        if (child is Insect && child.isAnimated) child.pause()
                    }
                }
                if (!isPaused) {
                    runOnUiThread {
                        div--
                        if (div == 0) {
                            generateInsects(1, Insect.InsectType.BUG)
                            if (currentLadyBugCount < ladyBugCount) {
                                generateInsects(1, Insect.InsectType.LADYBUG)
                                currentLadyBugCount++
                            }
                            div = DIFFICULTY_COEFFICIENT - difficulty
                        }
                        val childCount = gameFieldLayout.childCount
                        for (k in 0 until childCount) {
                            val child = gameFieldLayout.getChildAt(k)
                            if (child is Insect && child.isAnimated) child.resume()
                        }
                        for (i in 0 until childCount) {
                            val child = gameFieldLayout.getChildAt(i)
                            if (child is Insect && child.type == Insect.InsectType.LADYBUG && (abs(child.x - child.endX) < 1) && (abs(child.y - child.endY) < 1)) {
                                val centerX = (gameFieldWidth - Insect.InsectType.LADYBUG.width) / 2
                                val centerY = (gameFieldHeight - Insect.InsectType.LADYBUG.height) / 2
                                val radius = Math.sqrt(Math.pow(centerX.toDouble(), 2.0) + Math.pow(centerY.toDouble(), 2.0)) + max(Insect.InsectType.LADYBUG.width, Insect.InsectType.LADYBUG.height) * 2
                                child.endX = (centerX.toDouble() - radius + Math.random() * 2.0 * radius).toFloat()
                                child.endY = (Math.pow(-1.0, (Random().nextInt(2) + 1).toDouble()) * Math.sqrt(Math.pow(radius, 2.0) - Math.pow(child.endX - centerX.toDouble(), 2.0)) + centerY.toDouble()).toFloat()
                                child.animateInsect()
                            } else if (child is Insect && child.type != Insect.InsectType.LADYBUG && (abs(child.x - child.endX) < 1) && (abs(child.y - child.endY) < 1)) {
                                if (health > 0F) {
                                    healthBar.progress = health.toInt()
                                    pizza.setImageLevel(health.toInt())
                                    health -= BITE_COST
                                }
                                if (health <= 0F) break
                            }
                        }
                        if (health <= 0F) {
                            timerForUpdate.cancel()
                            intent = Intent(content, GameOverActivity::class.java)
                            intent.putExtra("finalScore", score)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }, 0, TIMER_PERIOD)
    }

    companion object {
        const val MAX_HEALTH = 100F
        const val STANDARD_DIFFICULTY = 2
        const val SOUND_POOL_MAX_STREAMS = 6
        const val SOUND_POOL_VOLUME = 0.8F
        const val TIMER_PERIOD: Long = 200
        const val BITE_COST = 0.2F
        const val LADY_BUG_COUNT_COEFFICIENT = 10
        const val DIFFICULTY_COEFFICIENT = 4
    }
}
