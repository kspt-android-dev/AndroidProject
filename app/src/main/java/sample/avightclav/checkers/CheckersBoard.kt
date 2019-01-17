package sample.avightclav.checkers

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup

class CheckersBoard : ViewGroup{

    private var startPos: Int
    private var fieldSize: Int
    private var checkerboard: Gameboard
    private var from: Coordinate? = null

    private constructor(context: Context) : super(context) {
        this.checkerboard = Gameboard(fieldSize = FieldSize.RUSSIAN)
        startPos = -1
        fieldSize = 8
    }

    private constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.checkerboard = Gameboard(fieldSize = FieldSize.RUSSIAN)
        startPos = context.theme.obtainStyledAttributes(attrs, R.styleable.CheckersBoard, 0, 0)
            .getInt(R.styleable.CheckersBoard_start_line, -1)
        fieldSize = context.theme.obtainStyledAttributes(attrs, R.styleable.CheckersBoard, 0, 0)
            .getInteger(R.styleable.CheckersBoard_field_size, 8)
    }

    private constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.checkerboard = Gameboard(fieldSize = FieldSize.RUSSIAN)
        startPos = context.theme.obtainStyledAttributes(attrs, R.styleable.CheckersBoard, 0, 0)
            .getInt(R.styleable.CheckersBoard_start_line, -1)
        fieldSize = context.theme.obtainStyledAttributes(attrs, R.styleable.CheckersBoard, 0, 0)
            .getInteger(R.styleable.CheckersBoard_field_size, 8)
    }

    constructor(context: Context, startPos: Int, gameboard: Gameboard) : this(context) {
        this.checkerboard = gameboard
        this.startPos = startPos
        this.fieldSize = gameboard.fieldSize.size
    }


    fun start() {
        for (row in 0 until fieldSize)
            for (column in 0 until fieldSize) {
                val cell = ConstraintLayout(context)
                cell.id = row
                if ((row + column) % 2 == 1) {
                    cell.isClickable = true
                    cell.setOnClickListener { it ->
                        Log.d("mmd", "Clicked")
                        if (it is ConstraintLayout) onClickFun(it, row, column)
                    }
                }
                    this.addView(cell)
            }
        update()
    }

    fun update() {
        checkerboard.board.forEachIndexed { row, arrayOfCheckers ->
            arrayOfCheckers.forEachIndexed { column, checker ->
                val child = this.getChildAt(row * this.fieldSize + column)
                if (checker != null) {
                    if (child is ConstraintLayout) {
                        if (checker.color == sample.avightclav.checkers.Color.BLACK)
                            child.setBackgroundResource(R.drawable.checker_black)
                        else
                            child.setBackgroundResource(R.drawable.king_white)
                    }
                } else if ((row + column) % 2 == 1)
                    child.setBackgroundResource(R.drawable.cell_selected)
                else {
                    child.setBackgroundResource(R.drawable.ic_white_cell)
                }
            }
        }

        checkerboard.possibleMovements.keys.forEach { it ->
            val cell = this.getChildAt(it.coordinate.y * fieldSize + it.coordinate.x)
            if (cell is ConstraintLayout)
                cell.isActivated = true
        }
    }

    fun onClickFun(cell: ConstraintLayout, row: Int, column: Int) {

        for (i in 0 until this.childCount) {
            this.getChildAt(i).isActivated = false
        }

        val from = this.from
        if (from is Coordinate) {
            val possibleMoves = checkerboard.possibleMovements[checkerboard.board[from.y][from.x]]
            if (possibleMoves != null) {
                if (checkerboard.move(from, Coordinate(row, column)))
                    update()
            }
        }

        if (checkerboard.possibleMovements.containsKey(checkerboard.board[row][column])) {
            cell.isActivated = true
            this.from = Coordinate(row, column)
            val cellsToActivate = checkerboard.possibleMovements[checkerboard.board[row][column]]
            if (cellsToActivate != null)
                cellsToActivate.forEach { it ->
                    val toX = it.to.x
                    val toY = it.to.y
                    this.getChildAt(toY * fieldSize + toX).isActivated = true
                }
        } else {
            checkerboard.possibleMovements.keys.forEach { it ->
                val cell = this.getChildAt(it.coordinate.y * fieldSize + it.coordinate.x)
                if (cell is ConstraintLayout)
                    cell.isActivated = true
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentHeight = MeasureSpec.getSize(widthMeasureSpec)
        val parentWidth = MeasureSpec.getSize(heightMeasureSpec)
        Log.d("Gameboard", "Re-measuring parentHeight="+parentHeight+"parentWidth="+parentWidth+"\n")
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
            Log.d("Gameboard","Params: t: $t, l: $l, b: $b, r: $r")
            val child = getChildAt(i)
            child.layout(l, t, r, b)
        }
    }
}
