package sample.avightclav.checkers.views

import android.content.Context
import android.graphics.Canvas
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import sample.avightclav.checkers.R
import sample.avightclav.checkers.gamelogic.Coordinate

class DummyCheckersBoard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var fieldSize = 8
        set(value) {
            if (this.childCount <= value * value)
                for (i in this.childCount..(value * value))
                    this.addView(ConstraintLayout(context))
            else
                for (i in (this.childCount - 1)..(value * value))
                    this.removeViewAt(i)
            this.invalidateListeners()
            this.invalidate()
            field = value
        }

    var startPos: Int
    var cellClickedListener: CellClickedListener? = null

    init {
        this.setWillNotDraw(false)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CheckersBoard,
            0, 0
        ).apply {
            try {
                fieldSize = getInteger(R.styleable.CheckersBoard_field_size, 8)
                startPos = getInteger(R.styleable.CheckersBoard_start_line, -1)
            } finally {
                recycle()
            }
        }

        for (i in 0 until fieldSize * fieldSize) {
            this.addView(ConstraintLayout(context))
        }
        this.invalidateListeners()
    }

    interface CellClickedListener {
        fun onCellClicked(coordinate: Coordinate)
    }

    fun invalidateListeners() {
        for (i in 0 until this.childCount) {
            this.getChildAt(i).setOnClickListener {_ ->
                if (cellClickedListener != null)
                    cellClickedListener!!.onCellClicked(indexToCoordinate(i))
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentHeight = MeasureSpec.getSize(widthMeasureSpec)
        val parentWidth = MeasureSpec.getSize(heightMeasureSpec)
        Log.d("Gameboard", "Re-measuring parentHeight=" + parentHeight + "parentWidth=" + parentWidth + "\n")
        val minSpec = Math.min(parentHeight, parentWidth)
        val childCount = this.childCount
        for (i in 0 until childCount) {
            val child = this.getChildAt(i)
            val measureSpec = MeasureSpec.makeMeasureSpec(minSpec / fieldSize, MeasureSpec.EXACTLY)
            child.measure(measureSpec, measureSpec)
        }
        setMeasuredDimension(minSpec, minSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val fieldSideSize = Math.min(right - left, bottom - top)
        val childCount = this.childCount
        val squareSideSize = fieldSideSize / fieldSize
        val offset = fieldSideSize - squareSideSize * fieldSize
        for (i in 0 until childCount) {
            val t = offset / 2 + i / fieldSize * squareSideSize
            val l = offset / 2 + i % fieldSize * squareSideSize
            val b = t + squareSideSize
            val r = l + squareSideSize
            Log.d("Gameboard", "Params: t: $t, l: $l, b: $b, r: $r")
            val child = getChildAt(i)
            child.layout(l, t, r, b)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("Gameboard", "On Draw")
        for (i in 0 until this.childCount) {
            val cell = this.getChildAt(i)
            if (isPlaceable(indexToCoordinate(i)))
                cell.setBackgroundResource(R.drawable.cell_selected)
            else
                cell.setBackgroundResource(R.drawable.ic_white_cell)
        }
    }

    fun isPlaceable(coordinate: Coordinate): Boolean = ((coordinate.x + coordinate.y) % 2 == 1)

    fun indexToCoordinate(index: Int): Coordinate = Coordinate(index / fieldSize, index % fieldSize)
}
