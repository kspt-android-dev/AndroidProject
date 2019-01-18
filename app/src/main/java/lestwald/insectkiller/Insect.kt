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
        BUG(0.2F, 2, 87, 130, R.drawable.bug),
        LADYBUG(0.12F, -5, 81, 100, R.drawable.ladybug)
    }

    init {
        this.x = startX
        this.y = startY
        this.setImageResource(type.image)
        this.layoutParams = ViewGroup.LayoutParams(type.width, type.height)
        this.rotation = (180 - Math.atan2((endX - x).toDouble(), (endY - y).toDouble()) * 180 / Math.PI).toFloat()
    }

    fun animateInsect() {
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

}