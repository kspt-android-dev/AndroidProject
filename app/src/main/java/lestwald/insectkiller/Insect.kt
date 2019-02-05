package lestwald.insectkiller

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import kotlin.math.sqrt

@SuppressLint("ViewConstructor")
class Insect(context: Context, startX: Float, startY: Float,
             var endX: Float, var endY: Float, val type: InsectType) : android.support.v7.widget.AppCompatImageView(context) {

    val tapCost: Int
        get() = type.tapCost
    var speed = type.speed
    var isAnimated = false
    private lateinit var animator: ObjectAnimator

    enum class InsectType(val speed: Float, val tapCost: Int, val width: Int, val height: Int, val image: Int) {
        BUG(BUG_SPEED, BUG_TAP_COST, BUG_WIDTH, BUG_HEIGHT, R.drawable.bug),
        LADYBUG(LADYBUG_SPEED, LADYBUG_TAP_COST, LADYBUG_WIDTH, LADYBUG_HEIGHT, R.drawable.ladybug)
    }

    init {
        this.x = startX
        this.y = startY
        this.setImageResource(type.image)
        this.layoutParams = ViewGroup.LayoutParams(type.width, type.height)
        this.rotation = (180 - Math.atan2((endX - x).toDouble(), (endY - y).toDouble()) * 180 / Math.PI).toFloat()
    }

    fun animateInsect() {
        rotation = (180 - Math.atan2((endX - x).toDouble(), (endY - y).toDouble()) * 180 / Math.PI).toFloat()
        val transX = PropertyValuesHolder.ofFloat("x", endX)
        val transY = PropertyValuesHolder.ofFloat("y", endY)
        animator = ObjectAnimator.ofPropertyValuesHolder(this, transX, transY)
        animator.duration = ((sqrt(Math.pow((this.x - endX).toDouble(), 2.0) + Math.pow((this.y - endY).toDouble(), 2.0))) / type.speed).toLong()
        animator.start()
        isAnimated = true
    }

    fun pause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animator.pause()
        }
    }

    fun resume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            animator.resume()
        }
    }

    companion object {
        const val BUG_SPEED = 0.2F
        const val LADYBUG_SPEED = 0.12F
        const val BUG_TAP_COST = 1
        const val LADYBUG_TAP_COST = -5
        const val BUG_WIDTH = 87
        const val LADYBUG_WIDTH = 81
        const val BUG_HEIGHT = 130
        const val LADYBUG_HEIGHT = 100
    }
}