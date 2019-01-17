package sample.avightclav.checkers

import org.junit.Assert.*
import org.junit.Test

class GameboardTest {
    @Test
    fun gameboardInitialTest() {
        val gameboard = Gameboard(FieldSize.RUSSIAN)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.MAN
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(5, 0), tp, st), listOf(Move(Coordinate(5, 0), Coordinate(4, 1), null))),
            Pair(Checker(clr, Coordinate(5, 2), tp, st), listOf(Move(Coordinate(5, 2), Coordinate(4, 1), null), Move(Coordinate(5, 2), Coordinate(4, 3), null))),
            Pair(Checker(clr, Coordinate(5, 4), tp, st), listOf(Move(Coordinate(5, 4), Coordinate(4, 3), null), Move(Coordinate(5, 4), Coordinate(4, 5), null))),
            Pair(Checker(clr, Coordinate(5, 6), tp, st), listOf(Move(Coordinate(5, 6), Coordinate(4, 5), null), Move(Coordinate(5, 6), Coordinate(4, 7), null))))
        assert(gameboard.currentColor == Color.WHITE)
        assert(!gameboard.strokeMove)
        assertEquals(possibleMovements, gameboard.possibleMovements)
    }

    @Test
    fun kingMoveTestLeft() {
        val board = arrayOf(
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, Checker(Color.BLACK, Coordinate(4, 7), CheckerType.MAN, CheckerState.ON_FIELD)),
            arrayOf<Checker?>(null, null, null, null, Checker(Color.WHITE, Coordinate(5, 4), CheckerType.KING, CheckerState.ON_FIELD), null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, Checker(Color.WHITE, Coordinate(7, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null))
        val gameboard = Gameboard(FieldSize.RUSSIAN, board)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.KING
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(5, 4), tp, st), listOf(
            Move(Coordinate(5, 4), Coordinate(4, 3), null),
            Move(Coordinate(5, 4), Coordinate(3, 2), null),
            Move(Coordinate(5, 4), Coordinate(6, 3), null),
            Move(Coordinate(5, 4), Coordinate(4, 5), null),
            Move(Coordinate(5, 4), Coordinate(3, 6), null),
            Move(Coordinate(5, 4), Coordinate(2, 7), null),
            Move(Coordinate(5, 4), Coordinate(6, 5), null),
            Move(Coordinate(5, 4), Coordinate(7, 6), null))))
    }

    @Test
    fun kingMoveTestRight() {
        val board = arrayOf(
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, Checker(Color.BLACK, Coordinate(4, 7), CheckerType.MAN, CheckerState.ON_FIELD)),
            arrayOf<Checker?>(null, null, null, null, Checker(Color.WHITE, Coordinate(5, 4), CheckerType.KING, CheckerState.ON_FIELD), null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, Checker(Color.WHITE, Coordinate(7, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null))
        val gameboard = Gameboard(FieldSize.RUSSIAN, board)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.KING
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(5, 4), tp, st), listOf(
            Move(Coordinate(5, 4), Coordinate(4, 3), null),
            Move(Coordinate(5, 4), Coordinate(3, 2), null),
            Move(Coordinate(5, 4), Coordinate(6, 3), null),
            Move(Coordinate(5, 4), Coordinate(6, 5), null),
            Move(Coordinate(5, 4), Coordinate(7, 6), null)
            )))
    }

    @Test
    fun kingStrokeTestLeftForward() {
        val board = arrayOf(
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, Checker(Color.BLACK, Coordinate(4, 3), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, Checker(Color.WHITE, Coordinate(5, 4), CheckerType.KING, CheckerState.ON_FIELD), null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, Checker(Color.WHITE, Coordinate(7, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null))
        val gameboard = Gameboard(FieldSize.RUSSIAN, board)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.KING
        val sch1 = Checker(clr.opposite(), Coordinate(4, 3), CheckerType.MAN, CheckerState.ON_FIELD)
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(5, 4), tp, st), listOf(
            Move(Coordinate(5, 4), Coordinate(3, 2), sch1)))
        )
        assert(gameboard.currentColor == Color.WHITE)
        assert(gameboard.strokeMove)
        assertEquals(possibleMovements, gameboard.possibleMovements)
    }

    @Test
    fun kingStrokeTestLeftBack() {
        val board = arrayOf(
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, Checker(Color.WHITE, Coordinate(3, 4), CheckerType.KING, CheckerState.ON_FIELD), null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, Checker(Color.BLACK, Coordinate(5, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, Checker(Color.WHITE, Coordinate(7, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null))
        val gameboard = Gameboard(FieldSize.RUSSIAN, board)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.KING
        val sch1 = Checker(clr.opposite(), Coordinate(5, 2), CheckerType.MAN, CheckerState.ON_FIELD)
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(3, 4), tp, st), listOf(
            Move(Coordinate(3, 4), Coordinate(6, 1), sch1),
            Move(Coordinate(3, 4), Coordinate(7, 0), sch1)))
        )
        assert(gameboard.currentColor == Color.WHITE)
        assert(gameboard.strokeMove)
        assertEquals(possibleMovements, gameboard.possibleMovements)
    }

    @Test
    fun kingStrokeTestRightForward() {
        val board = arrayOf(
            arrayOf<Checker?>(null, null, null, null, null, Checker(Color.BLACK, Coordinate(0, 5), CheckerType.MAN, CheckerState.ON_FIELD), null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, Checker(Color.BLACK, Coordinate(2, 3), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(4, 1), CheckerType.KING, CheckerState.ON_FIELD), null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, Checker(Color.WHITE, Coordinate(7, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null))
        val gameboard = Gameboard(FieldSize.RUSSIAN, board)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.KING
        val sch1 = Checker(clr.opposite(), Coordinate(2, 3), CheckerType.MAN, CheckerState.ON_FIELD)
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(4, 1), tp, st), listOf(
            Move(Coordinate(4, 1), Coordinate(1, 4), sch1)))
        )
        assert(gameboard.currentColor == Color.WHITE)
        assert(gameboard.strokeMove)
        assertEquals(possibleMovements, gameboard.possibleMovements)
    }

    @Test
    fun kingStrokeTestRightBack() {
        val board = arrayOf(
            arrayOf<Checker?>(null, null, null, null, null, Checker(Color.BLACK, Coordinate(0, 5), CheckerType.MAN, CheckerState.ON_FIELD), null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, Checker(Color.BLACK, Coordinate(2, 3), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, Checker(Color.WHITE, Coordinate(5, 6), CheckerType.KING, CheckerState.ON_FIELD), null),
            arrayOf<Checker?>(null, null, null, null, null, Checker(Color.BLACK, Coordinate(6, 5), CheckerType.MAN, CheckerState.ON_FIELD), null, null),
            arrayOf<Checker?>(null, null, Checker(Color.WHITE, Coordinate(7, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null))
        val gameboard = Gameboard(FieldSize.RUSSIAN, board)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.KING
        val sch1 = Checker(clr.opposite(), Coordinate(6, 5), CheckerType.MAN, CheckerState.ON_FIELD)
        val sch2 = Checker(clr.opposite(), Coordinate(2, 3), CheckerType.MAN, CheckerState.ON_FIELD)
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(5, 6), tp, st), listOf(
            Move(Coordinate(5, 6), Coordinate(1, 2), sch2),
            Move(Coordinate(5, 6), Coordinate(0, 1), sch2),
            Move(Coordinate(5, 6), Coordinate(7, 4), sch1)
            ))
        )
        assert(gameboard.currentColor == Color.WHITE)
        assert(gameboard.strokeMove)
        assertEquals(possibleMovements, gameboard.possibleMovements)
    }

    @Test
    fun kingStrokeTestLeftForwardObstacle() {
        val board = arrayOf(
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, Checker(Color.WHITE, Coordinate(2, 1), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, null, Checker(Color.BLACK, Coordinate(4, 3), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null),
            arrayOf<Checker?>(null, null, null, null, Checker(Color.WHITE, Coordinate(5, 4), CheckerType.KING, CheckerState.ON_FIELD), null, null, null),
            arrayOf<Checker?>(null, null, null, null, null, null, null, null),
            arrayOf<Checker?>(null, null, Checker(Color.WHITE, Coordinate(7, 2), CheckerType.MAN, CheckerState.ON_FIELD), null, null, null, null, null))
        val gameboard = Gameboard(FieldSize.RUSSIAN, board)
        val clr = Color.WHITE
        val st = CheckerState.ON_FIELD
        val tp = CheckerType.KING
        val sch1 = Checker(clr.opposite(), Coordinate(4, 3), CheckerType.MAN, CheckerState.ON_FIELD)
        val possibleMovements = hashMapOf(Pair(Checker(clr, Coordinate(5, 4), tp, st), listOf(
            Move(Coordinate(5, 4), Coordinate(3, 2), sch1)))
        )
        assert(gameboard.currentColor == Color.WHITE)
        assert(gameboard.strokeMove)
        assertEquals(possibleMovements, gameboard.possibleMovements)
    }

}