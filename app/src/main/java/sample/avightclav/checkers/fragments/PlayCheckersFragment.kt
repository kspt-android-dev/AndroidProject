package sample.avightclav.checkers.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sample.avightclav.checkers.gamelogic.*
import sample.avightclav.checkers.views.DummyCheckersBoard

class PlayCheckersFragment : Fragment() {

    lateinit var gameboard: Gameboard
    lateinit var dummyCheckersBoard: DummyCheckersBoard
    var selectedChecker: Checker? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gameboard = Gameboard(FieldSize.RUSSIAN)
        dummyCheckersBoard = DummyCheckersBoard(activity!!.applicationContext)
        dummyCheckersBoard.fieldSize = FieldSize.RUSSIAN.size
        return dummyCheckersBoard
    }

    override fun onStart() {
        super.onStart()

        dummyCheckersBoard.cellClickedListener = (object : DummyCheckersBoard.CellClickedListener {
            override fun onCellClicked(coordinate: Coordinate) {
                val cell = gameboard.board[coordinate.y][coordinate.x]
                dummyCheckersBoard.inactivateAll()
                if (cell is Checker && cell.color == gameboard.currentColor && gameboard.possibleMovements.containsKey(
                        cell
                    )
                ) {
                    selectedChecker = cell
                    dummyCheckersBoard.inactivateAll()
                    dummyCheckersBoard.activate(coordinate)
                    activatePossibleMoves(cell)
                } else if (selectedChecker != null && gameboard.move(selectedChecker!!.coordinate, coordinate)) {
                    update()
                } else {
                    activateMovableCheckers()
                }

                Log.d("FRAGMENT", "x: ${coordinate.x}, y: ${coordinate.y}")
            }
        })

        this.placeCheckers()
        this.activateMovableCheckers()
    }

    fun placeCheckers() {
        dummyCheckersBoard.cleanBoard()
        gameboard.board.forEach { row ->
            row.forEach { column ->
                if (column is Checker) {
                    dummyCheckersBoard.placeChecker(
                        column.coordinate,
                        column.color
                    )
                }
            }
        }
    }

    fun update() {
        gameboard.board.forEachIndexed { row, arrayOfCheckers ->
            arrayOfCheckers.forEachIndexed { column, checker ->
                val coordinate = Coordinate(row, column)
                if (checker != null) {
                    if (checker.color == Color.BLACK)
                        dummyCheckersBoard.placeChecker(coordinate, Color.BLACK)
                    else
                        dummyCheckersBoard.placeChecker(coordinate, Color.WHITE)
                } else
                    dummyCheckersBoard.clean(coordinate)
            }
        }
        activateMovableCheckers()
    }

    fun activateMovableCheckers() =
        gameboard.possibleMovements.forEach { it -> dummyCheckersBoard.activate(it.key.coordinate) }

    fun activatePossibleMoves(checker: Checker) =
        gameboard.possibleMovements.getValue(checker).forEach { move -> dummyCheckersBoard.activate(move.to) }

}