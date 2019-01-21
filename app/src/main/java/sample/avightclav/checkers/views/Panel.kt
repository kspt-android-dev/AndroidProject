package sample.avightclav.checkers.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import sample.avightclav.checkers.R

class Panel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val buttonsRes = arrayOf(
        R.drawable.cell_clear,
        R.drawable.checker_man_white_inactive,
        R.drawable.king_white,
        R.drawable.checker_man_black,
        R.drawable.king_black
    )
    private val buttonsCount = buttonsRes.size
    private var isVertical = true

    init {
        for (i in 0 until buttonsCount) {
            val child = ConstraintLayout(context)
            child.setBackgroundResource(buttonsRes[i])
            this.addView(child)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        Log.d("Panel", "Re-measuring parentHeight="+parentHeight+"parentWidth="+parentWidth+"\n")
        isVertical = parentHeight > parentWidth
        val maxSpec = if (isVertical) parentHeight else parentWidth
        val buttonSize = maxSpec / buttonsCount

        for (i in 0 until childCount) {
            val child = this.getChildAt(i)
            val measureSpec = MeasureSpec.makeMeasureSpec(buttonSize, MeasureSpec.EXACTLY)
            child.measure(measureSpec, measureSpec)
        }

        if (!isVertical)
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(maxSpec, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(buttonSize, MeasureSpec.EXACTLY))
        else
            setMeasuredDimension(MeasureSpec.makeMeasureSpec(buttonSize, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(maxSpec, MeasureSpec.EXACTLY))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (isVertical) {
            val left = l
            val right = r
            var top = t
            var bottom = 0

            for (i in 0 until this.childCount) {
                val child = getChildAt(i)
                bottom = top + child.measuredHeight
                child.layout(left, top, right, bottom)
                Log.d("Panel", "Params: t: $top, l: $left, b: $bottom, r: $right")
                top = bottom
            }
        } else {
            var left = l
            var right = 0
            val top = t
            val bottom = b

            for (i in 0 until this.childCount) {

                val child = getChildAt(i)
                right = left + child.measuredWidth
                child.layout(left, top, right, bottom)
                Log.d("Panel", "Params: t: $top, l: $left, b: $bottom, r: $right")
                left = right
            }
        }
    }

}