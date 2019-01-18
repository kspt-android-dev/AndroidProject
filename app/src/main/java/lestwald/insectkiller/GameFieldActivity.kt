package lestwald.insectkiller

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.Random
import java.util.Timer
import java.util.TimerTask
import kotlin.math.*

class GameFieldActivity : AppCompatActivity() {
    private lateinit var gameFieldLayout: ConstraintLayout
    private lateinit var topBarLayout: ConstraintLayout
    private lateinit var scoreTextView: TextView
    private lateinit var healthBar: ImageView
    private lateinit var pizza: ImageView
    private lateinit var pauseButton: ImageButton
    private lateinit var bitmapForHealth: Bitmap
    private lateinit var bitmapForPizza: Bitmap
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var soundPool: SoundPool
    private val timerForUpdate = Timer()
    private var gameFieldWidth = 0
    private var gameFieldHeight = 0
    private var score = 0
    private var health = 100F
    private var soundId = 0
    private var pizzaRadius = 0
    private var pizzaRealRadius = 0
    private var isPaused = false
    private var sound = true
    private var difficulty = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_field)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sound = sharedPreferences.getBoolean("sound", true)
        difficulty = sharedPreferences.getString("difficulty", "2").toInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = SoundPool.Builder().setMaxStreams(6).build()
        } else {
            @Suppress("DEPRECATION")
            soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        }
        soundId = soundPool.load(this, R.raw.smashing, 1)

        gameFieldLayout = findViewById(R.id.game_field)
        topBarLayout = findViewById(R.id.top_bar)
        scoreTextView = findViewById(R.id.score)
        healthBar = findViewById(R.id.health_bar)
        pizza = findViewById(R.id.pizza)
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
            scoreTextView.text = "$score"

            bitmapForHealth = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.health_bar),
                    400, 2500, false)
            healthBar.setImageBitmap(Bitmap.createBitmap(bitmapForHealth, 0, 0, 400, 48))

            pizzaRadius = min(gameFieldWidth, gameFieldHeight) / 4
            pizzaRealRadius = pizzaRadius
            bitmapForPizza = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.pizza),
                    pizzaRadius * 16, pizzaRadius * 2, false)
            pizza.setImageBitmap(Bitmap.createBitmap(bitmapForPizza, 0, 0, pizzaRadius * 2, pizzaRadius * 2))

            generateInsects()
            update()
        }
    }

    override fun onDestroy() {
        soundPool.release()
        timerForUpdate.cancel()
        super.onDestroy()
        //test
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("score", score)
        outState.putFloat("health", health)
        val childCount = gameFieldLayout.childCount
        outState.putInt("child_count", childCount)
/*        for (i in 0 until childCount) {
            val child = gameFieldLayout.getChildAt(i)
            if (child is Insect) {
                outState.putFloat("insect${i}_x", child.x)
                outState.putFloat("insect${i}_y", child.y)
                outState.putFloat("insect${i}_end_x", child.endX)
                outState.putFloat("insect${i}_end_y", child.endY)
                outState.putString("insect${i}_type", child.type.name)
            }
        }*/
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        score = savedInstanceState.getInt("score")
        scoreTextView.text = "$score"
        health = savedInstanceState.getFloat("health")
/*        for (i in 0 until savedInstanceState.getInt("child_count")) {
            val y = savedInstanceState.getFloat("insect${i}_x")
            val x = savedInstanceState.getFloat("insect${i}_y")
            val endY = savedInstanceState.getFloat("insect${i}_end_x")
            val endX = savedInstanceState.getFloat("insect${i}_end_y")
            var type = Insect.InsectType.BUG
            if (savedInstanceState.getString("insect${i}_type") != null) {
                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                type = Insect.InsectType.valueOf(savedInstanceState.getString("insect${i}_type"))
            }
            val insect = Insect(this, x, y, endX, endY, type)
            addInsect(insect)
        }*/
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addInsect(insect: Insect) {
        insect.setOnTouchListener { it, event ->
            if (!isPaused && event.action == MotionEvent.ACTION_DOWN) {
                if (sound) soundPool.play(soundId, 0.8F, 0.8F, 1, 0, 1F)
                if (it is Insect) {
                    gameFieldLayout.removeView(it)
                    score += it.tapCost
                    scoreTextView.text = "$score"
                }
            }
            true
        }
        gameFieldLayout.addView(insect)
    }

    private fun generateInsects() {
        for (i in 1 until 50) {
            generateInsects(1, Insect.InsectType.BUG)
            generateInsects(1, Insect.InsectType.LADYBUG)
        }
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
                endX = centerX - (pizzaRealRadius + type.height * 0.25F) * cos(angle)
                endY = centerY + (pizzaRealRadius + type.height * 0.25F) * sin(angle)
            }
            val insect = Insect(this, startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), type)
            insect.speed *= 1 + (difficulty - 1).toFloat() / 2
            addInsect(insect)
        }
    }

    private fun updateHealthBar() {
        val y = (100 - health.roundToInt()) / 2 * 50 + 1
        if (y + 48 < 2500) healthBar.setImageBitmap(Bitmap.createBitmap(bitmapForHealth, 0, y, 400, 48))
    }

    private fun updatePizza() {
        val x = ((100 - health.roundToInt()) / 12.5).roundToInt() * pizzaRadius * 2
        if (x + pizzaRadius * 2 < pizzaRadius * 16) pizza.setImageBitmap(Bitmap.createBitmap(bitmapForPizza, x, 0, pizzaRadius * 2, pizzaRadius * 2))
        pizzaRealRadius = (pizzaRadius * (1 - (100 - health) / 115F)).roundToInt()
    }

    private fun update() {
        val content = this
        var div = 4 - difficulty
        var divGenerate = (4 - difficulty) * 50
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
                        var childCount = gameFieldLayout.childCount
                        childCount = gameFieldLayout.childCount
                        for (k in 0 until childCount) {
                            val child = gameFieldLayout.getChildAt(k)
                            if (child is Insect && child.isAnimated) child.resume()
                        }
                        div--
                        if (div == 0) {
                            for (k in 0 until childCount) {
                                val child = gameFieldLayout.getChildAt(k)
                                if (child is Insect && !child.isAnimated && ((abs(child.x - child.endX) > 1) || (abs(child.y - child.endY) > 1))) {
                                    child.animateInsect()
                                    break
                                }
                            }
                            div = 4 - difficulty
                        }
                        divGenerate--
                        if (divGenerate == 0) {
                            generateInsects()
                            divGenerate = (4 - difficulty) * 50
                        }
                        for (i in 0 until childCount) {
                            val child = gameFieldLayout.getChildAt(i)
                            if (child is Insect && child.type != Insect.InsectType.LADYBUG && (abs(child.x - child.endX) < 1) && (abs(child.y - child.endY) < 1)) {
                                if (health > 0F) {
                                    updateHealthBar()
                                    updatePizza()
                                    for (j in 0 until childCount) {
                                        val child1 = gameFieldLayout.getChildAt(j)
                                        if (child1 is Insect && child1.type != Insect.InsectType.LADYBUG && (abs(child1.x - child1.endX) < 1) && (abs(child1.y - child1.endY) < 1)) {
                                            val centerX1 = gameFieldWidth / 2 - child1.type.width / 2
                                            val centerY1 = gameFieldHeight / 2 - child1.type.height / 2
                                            val angle = atan2((child1.y - centerY1), (centerX1 - child1.x))
                                            child1.endX = (centerX1 - (pizzaRealRadius + child1.type.height * 0.25F) * cos(angle))
                                            child1.endY = (centerY1 + (pizzaRealRadius + child1.type.height * 0.25F) * sin(angle))
                                            child1.animateInsect()
                                        } else if (child1 is Insect && child1.type == Insect.InsectType.LADYBUG && (abs(child1.x - child1.endX) < 1) && (abs(child1.y - child1.endY) < 1)) {
                                            gameFieldLayout.removeViewAt(j)
                                        }
                                    }
                                    health -= 0.1F
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
        }, 0, 100)
    }
}
