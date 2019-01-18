package sample.avightclav.checkers.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sample.avightclav.checkers.gamelogic.Coordinate
import sample.avightclav.checkers.gamelogic.FieldSize
import sample.avightclav.checkers.gamelogic.Gameboard
import sample.avightclav.checkers.views.DummyCheckersBoard

class CreateFieldFragment : Fragment() {

    lateinit var gameboard: Gameboard
    lateinit var dummyCheckersBoard: DummyCheckersBoard

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
                Log.d("FRAGMENT", "x: ${coordinate.x}, y: ${coordinate.y}")
            }
        })
    }
}