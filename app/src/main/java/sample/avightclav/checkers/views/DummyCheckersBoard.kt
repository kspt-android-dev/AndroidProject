package sample.avightclav.checkers.views

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import sample.avightclav.checkers.R
import sample.avightclav.checkers.gamelogic.Coordinate

class DummyCheckersBoard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var fieldSize = 0
        set(value) {
            when {
                this.childCount < value * value -> for (i in this.childCount..(value * value))
                    this.addView(ConstraintLayout(context))
                this.childCount > value * value -> for (i in (this.childCount - 1)..(value * value))
                    this.removeViewAt(i)
                else -> return
            }
            field = value
            this.cleanBoard()
            this.invalidateListeners()
        }

    var startPos: Int
    var cellClickedListener: CellClickedListener? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CheckersBoard,
            0, 0
        ).apply {
            try {
                startPos = getInteger(R.styleable.CheckersBoard_start_line, -1)
                fieldSize = getInteger(R.styleable.CheckersBoard_field_size, 8)
            } finally {
                recycle()
            }
        }

    }

    interface CellClickedListener {
        fun onCellClicked(coordinate: Coordinate)
    }

    private fun invalidateListeners() {
        for (i in 0 until this.childCount) {
            this.getChildAt(i).setOnClickListener { _ ->
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

    fun cleanBoard() {
        Log.d(resources.getString(R.string.dummyCheckersBoardTAG), "Cleaning board")
        for (i in 0 until this.childCount) {
            val cell = this.getChildAt(i)
            if (isPlaceable(indexToCoordinate(i)))
                cell.setBackgroundResource(R.drawable.cell_black)
            else
                cell.setBackgroundResource(R.drawable.cell_white)
        }
    }

    fun placeChecker(coordinate: Coordinate, color: sample.avightclav.checkers.gamelogic.Color) {
        this.getChildAt(coordinateToIndex(coordinate))
            .setBackgroundResource(if (color == sample.avightclav.checkers.gamelogic.Color.WHITE) R.drawable.checker_man_white else R.drawable.checker_man_black)
    }

    fun clean(coordinate: Coordinate) {
        val cell = this.getChildAt(coordinateToIndex(coordinate))
        if (isPlaceable(coordinate))
            cell.setBackgroundResource(R.drawable.cell_black)
        else
            cell.setBackgroundResource(R.drawable.cell_white)
    }

    fun activate(coordinate: Coordinate) {
        this.getChildAt(coordinateToIndex(coordinate)).isActivated = true
    }

    fun inactivateAll() {
        for (i in 0 until this.childCount)
            this.getChildAt(i).isActivated = false
    }

    private fun isPlaceable(coordinate: Coordinate): Boolean = ((coordinate.x + coordinate.y) % 2 == 1)
    private fun indexToCoordinate(index: Int): Coordinate = Coordinate(index / fieldSize, index % fieldSize)

    fun coordinateToIndex(coordinate: Coordinate) = coordinate.y * fieldSize + coordinate.x
}
