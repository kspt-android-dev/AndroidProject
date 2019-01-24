package sample.avightclav.checkers.gamelogic

import java.io.Serializable

enum class FieldSize(val size: Int) {
    WORLDWIDE(10),
    RUSSIAN(8),
    RESUME(-1)
}

data class Move(val from: Coordinate, val to: Coordinate, val strokedChecker: Checker?) : Serializable

class Gameboard(val fieldSize: FieldSize) : Serializable {

    var currentColor = Color.WHITE

    var board = Array(fieldSize.size) { row ->
        Array(fieldSize.size)
        { column ->
            if ((row + column) % 2 == 1)
                when (row) {
                    in 0..fieldSize.size / 2 - 2 -> Checker(
                        Color.BLACK,
                        Coordinate(row, column),
                        CheckerType.MAN,
                        CheckerState.ON_FIELD
                    )
                    in fieldSize.size - 1 downTo fieldSize.size / 2 + 1 -> Checker(
                        Color.WHITE,
                        Coordinate(row, column),
                        CheckerType.MAN,
                        CheckerState.ON_FIELD
                    )
                    fieldSize.size / 2 -> null
                    fieldSize.size / 2 - 1 -> null
                    else -> throw UnknownError("Field size doesn't exhaust its initializer")
                }
            else null
        }
    }

    var strokeMove = false
    var possibleMovements = makePossibleMovementsList()
    var moveHistory: Move? = null

    constructor(fieldSize: FieldSize, board: Array<Array<Checker?>>) : this(fieldSize) {
        this.board = board
        possibleMovements = makePossibleMovementsList()
    }

    fun move(from: Coordinate, to: Coordinate): Boolean {
        if (inBound(from) && inBound(to)) {
            val cell = board[from.y][from.x]
            if (cell is Checker && cell.color == currentColor) {
                val moveTo = possibleMovements.getValue(cell).find { move -> move.to == to && move.from == from }

                if (moveTo != null) {
                    cell.coordinate = to
                    board[from.y][from.x] = null
                    board[to.y][to.x] = cell

                    if (moveTo.strokedChecker != null) {
                        val checkerToStroke =
                            board[moveTo.strokedChecker.coordinate.y][moveTo.strokedChecker.coordinate.x]

                        if (checkerToStroke != null) {
                            checkerToStroke.state = CheckerState.STROKED
                            board[moveTo.strokedChecker.coordinate.y][moveTo.strokedChecker.coordinate.x] = null
                        } else
                            throw UnknownError() // TEMPORARY SOLUTION
                    }
                    addLastMove(moveTo)
                    possibleMovements = this.makePossibleMovementsList()
                    return true
                }
            }
        }
        return false
    }

    fun makeStrokeMoves(checker: Checker): List<Move> {
        val moves = mutableListOf<Move>()

        if (checker.type == CheckerType.MAN) {
            val rowForward2 = checker.coordinate.y + 2 * checker.color.shift
            val rowBack2 = checker.coordinate.y + 2 * -checker.color.shift
            val columnLeft2 = checker.coordinate.x - 2
            val columnRight2 = checker.coordinate.x + 2
            val rowForward1 = checker.coordinate.y + 1 * checker.color.shift
            val rowBack1 = checker.coordinate.y + 1 * -checker.color.shift

            val moveForward2InBounds = rowForward2 < fieldSize.size && rowForward2 >= 0
            val moveBack2InBounds = rowBack2 < fieldSize.size && rowBack2 >= 0
            val moveLeft2InBounds = columnLeft2 < fieldSize.size && columnLeft2 >= 0
            val moveRight2InBound = columnRight2 < fieldSize.size && columnRight2 >= 0

            if (moveLeft2InBounds) {
                val columnLeft1 = checker.coordinate.x - 1
                if (moveForward2InBounds && board[rowForward2][columnLeft2] == null) {
                    val cell = board[rowForward1][columnLeft1]
                    if (cell is Checker && cell.color == checker.color.opposite())
                        moves.add(
                            Move(
                                checker.coordinate,
                                Coordinate(rowForward2, columnLeft2),
                                cell
                            )
                        )
                }

                if (moveBack2InBounds && board[rowBack2][columnLeft2] == null) {
                    val cell = board[rowBack1][columnLeft1]
                    if (cell is Checker && cell.color == checker.color.opposite())
                        moves.add(
                            Move(
                                checker.coordinate,
                                Coordinate(rowBack2, columnLeft2),
                                cell
                            )
                        )
                }
            }

            if (moveRight2InBound) {
                val columnRight1 = checker.coordinate.x + 1
                if (moveForward2InBounds && board[rowForward2][columnRight2] == null) {
                    val cell = board[rowForward1][columnRight1]
                    if (cell is Checker && cell.color == checker.color.opposite())
                        moves.add(
                            Move(
                                checker.coordinate,
                                Coordinate(rowForward2, columnRight2),
                                cell
                            )
                        )
                }

                if (moveBack2InBounds && board[rowBack2][columnRight2] == null) {
                    val cell = board[rowBack1][columnRight1]
                    if (cell is Checker && cell.color == checker.color.opposite())
                        moves.add(
                            Move(
                                checker.coordinate,
                                Coordinate(rowBack2, columnRight2),
                                cell
                            )
                        )
                }
            }
        }

        if (checker.type == CheckerType.KING) {

            var row = checker.coordinate.y + 1 * checker.color.shift
            var column = checker.coordinate.x - 1
            var rowInBounds = row < fieldSize.size && row >= 0
            var columnInBounds = column < fieldSize.size && column >= 0
            var metOpposite = false
            var cell: Checker? = null
            var metFree: Boolean

            // Checking left forward diagonal
            while (rowInBounds && columnInBounds && !metOpposite) {
                cell = board[row][column]

                if (cell is Checker && cell.color == checker.color)
                    break

                if (cell is Checker && cell.color == checker.color.opposite())
                    metOpposite = true

                row += 1 * checker.color.shift
                column -= 1
                rowInBounds = row < fieldSize.size && row >= 0
                columnInBounds = column < fieldSize.size && column >= 0
            }

            if (metOpposite && rowInBounds && columnInBounds) {
                metFree = board[row][column] == null

                while (metFree && rowInBounds && columnInBounds) {
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            cell
                        )
                    )

                    row += 1 * checker.color.shift
                    column -= 1
                    rowInBounds = row < fieldSize.size && row >= 0
                    columnInBounds = column < fieldSize.size && column >= 0
                    if (rowInBounds && columnInBounds)
                        metFree = board[row][column] == null
                }
            }

            //Checking left back diagonal
            row = checker.coordinate.y - 1 * checker.color.shift
            column = checker.coordinate.x - 1
            rowInBounds = row < fieldSize.size && row >= 0
            columnInBounds = column < fieldSize.size && column >= 0
            metOpposite = false

            while (rowInBounds && columnInBounds && !metOpposite) {
                cell = board[row][column]

                if (cell is Checker && cell.color == checker.color)
                    break

                if (cell is Checker && cell.color == checker.color.opposite())
                    metOpposite = true

                row += 1 * -checker.color.shift
                column -= 1
                rowInBounds = row < fieldSize.size && row >= 0
                columnInBounds = column < fieldSize.size && column >= 0
            }

            if (metOpposite && rowInBounds && columnInBounds) {
                metFree = board[row][column] == null

                while (metFree && rowInBounds && columnInBounds) {
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            cell
                        )
                    )

                    row += 1 * -checker.color.shift
                    column -= 1
                    rowInBounds = row < fieldSize.size && row >= 0
                    columnInBounds = column < fieldSize.size && column >= 0
                    if (rowInBounds && columnInBounds)
                        metFree = board[row][column] == null
                }
            }

            // Checking right forward diagonal
            row = checker.coordinate.y + 1 * checker.color.shift
            column = checker.coordinate.x + 1
            rowInBounds = row < fieldSize.size && row >= 0
            columnInBounds = column < fieldSize.size && column >= 0
            metOpposite = false

            while (rowInBounds && columnInBounds && !metOpposite) {
                cell = board[row][column]

                if (cell is Checker && cell.color == checker.color)
                    break

                if (cell is Checker && cell.color == checker.color.opposite())
                    metOpposite = true

                row += 1 * checker.color.shift
                column += 1
                rowInBounds = row < fieldSize.size && row >= 0
                columnInBounds = column < fieldSize.size && column >= 0
            }

            if (metOpposite && rowInBounds && columnInBounds) {
                metFree = board[row][column] == null

                while (metFree && rowInBounds && columnInBounds) {
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            cell
                        )
                    )

                    row += 1 * checker.color.shift
                    column += 1
                    rowInBounds = row < fieldSize.size && row >= 0
                    columnInBounds = column < fieldSize.size && column >= 0
                    if (rowInBounds && columnInBounds)
                        metFree = board[row][column] == null
                }
            }

            // Cheking right back diagonal
            row = checker.coordinate.y - 1 * checker.color.shift
            column = checker.coordinate.x + 1
            rowInBounds = row < fieldSize.size && row >= 0
            columnInBounds = column < fieldSize.size && column >= 0
            metOpposite = false

            while (rowInBounds && columnInBounds && !metOpposite) {
                cell = board[row][column]

                if (cell is Checker && cell.color == checker.color)
                    break

                if (cell is Checker && cell.color == checker.color.opposite())
                    metOpposite = true

                row += 1 * -checker.color.shift
                column += 1
                rowInBounds = row < fieldSize.size && row >= 0
                columnInBounds = column < fieldSize.size && column >= 0
            }

            if (metOpposite && rowInBounds && columnInBounds) {
                metFree = board[row][column] == null

                while (metFree && rowInBounds && columnInBounds) {
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            cell
                        )
                    )

                    row += 1 * -checker.color.shift
                    column += 1
                    rowInBounds = row < fieldSize.size && row >= 0
                    columnInBounds = column < fieldSize.size && column >= 0
                    if (rowInBounds && columnInBounds)
                        metFree = board[row][column] == null
                }
            }
        }

        return moves
    }

    fun makeMoves(checker: Checker): List<Move> {
        val moves = mutableListOf<Move>()
        if (checker.type == CheckerType.MAN) {
            val rowForward1 = checker.coordinate.y + 1 * checker.color.shift
            val columnLeft1 = checker.coordinate.x - 1
            val columnRight1 = checker.coordinate.x + 1

            val moveLeft1InBounds = columnLeft1 < fieldSize.size && columnLeft1 >= 0
            val moveRight1InBounds = columnRight1 < fieldSize.size && columnRight1 >= 0

            if (moveLeft1InBounds && board[rowForward1][columnLeft1] == null)
                moves.add(
                    Move(
                        checker.coordinate,
                        Coordinate(rowForward1, columnLeft1),
                        null
                    )
                )

            if (moveRight1InBounds && board[rowForward1][columnRight1] == null)
                moves.add(
                    Move(
                        checker.coordinate,
                        Coordinate(rowForward1, columnRight1),
                        null
                    )
                )
        }

        if (checker.type == CheckerType.KING) {

            // Check left forward diagonal
            var row = checker.coordinate.y + 1 * checker.color.shift
            var column = checker.coordinate.x - 1
            var rowInBound = row < fieldSize.size && row >= 0
            var columnInBound = column < fieldSize.size && column >= 0
            var metFree = true

            while (metFree && rowInBound && columnInBound) {
                metFree = board[row][column] == null

                if (metFree)
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            null
                        )
                    )

                row += 1 * checker.color.shift
                column -= 1
                rowInBound = row < fieldSize.size && row >= 0
                columnInBound = column < fieldSize.size && column >= 0
            }

            // Check left back diagonal
            row = checker.coordinate.y - 1 * checker.color.shift
            column = checker.coordinate.x + 1
            rowInBound = row < fieldSize.size && row >= 0
            columnInBound = column < fieldSize.size && column >= 0
            metFree = true

            while (metFree && rowInBound && columnInBound) {
                metFree = board[row][column] == null

                if (metFree)
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            null
                        )
                    )

                row -= 1 * checker.color.shift
                column += 1
                rowInBound = row < fieldSize.size && row >= 0
                columnInBound = column < fieldSize.size && column >= 0
            }

            // Check right forward diagonal
            row = checker.coordinate.y + 1 * checker.color.shift
            column = checker.coordinate.x + 1
            rowInBound = row < fieldSize.size && row >= 0
            columnInBound = column < fieldSize.size && column >= 0
            metFree = true

            while (metFree && rowInBound && columnInBound) {
                metFree = board[row][column] == null

                if (metFree)
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            null
                        )
                    )

                row += 1 * checker.color.shift
                column += 1
                rowInBound = row < fieldSize.size && row >= 0
                columnInBound = column < fieldSize.size && column >= 0
            }

            // Check right back diagonal
            row = checker.coordinate.y - 1 * checker.color.shift
            column = checker.coordinate.x - 1
            rowInBound = row < fieldSize.size && row >= 0
            columnInBound = column < fieldSize.size && column >= 0
            metFree = true

            while (metFree && rowInBound && columnInBound) {
                metFree = board[row][column] == null

                if (metFree)
                    moves.add(
                        Move(
                            checker.coordinate,
                            Coordinate(row, column),
                            null
                        )
                    )

                row -= 1 * checker.color.shift
                column -= 1
                rowInBound = row < fieldSize.size && row >= 0
                columnInBound = column < fieldSize.size && column >= 0
            }

        }
        return moves
    }

    fun makePossibleMovementsList(): Map<Checker, List<Move>> {
        checkForKings()
        val lastMove = getLastMove()

        if (lastMove != null) {
            if (lastMove.strokedChecker != null) {
                val row = lastMove.to.y
                val column = lastMove.to.x
                val cell = board[row][column]
                if (cell is Checker) {
                    val additionalCheck = makeStrokeMoves(cell)

                    if (additionalCheck.isNotEmpty())
                        return hashMapOf(Pair(cell, additionalCheck))

                } else
                    throw UnknownError("makePossibleMoves")
            }
            currentColor = currentColor.opposite()
        }

        val checkList = getAll(currentColor)
        val strokeMovesList = checkList.associate { checker -> Pair(checker, makeStrokeMoves(checker)) }
            .filterValues { it -> it.isNotEmpty() }

        if (strokeMovesList.isEmpty()) {
            strokeMove = false
            val movesList = checkList.associate { checker -> Pair(checker, makeMoves(checker)) }
                .filterValues { it -> it.isNotEmpty() }
            return movesList
        } else {
            strokeMove = true
            return strokeMovesList
        }

    }

    fun checkForKings() {
        val blackBound = 0
        for (j in 1 until fieldSize.size step 2) {
            val cell = board[blackBound][j]
            if (cell is Checker && cell.color == Color.WHITE)
                cell.type = CheckerType.KING
        }

        val whiteBound = fieldSize.size - 1
        for (j in 0 until fieldSize.size step 2) {
            val cell = board[whiteBound][j]
            if (cell is Checker && cell.color == Color.BLACK)
                cell.type = CheckerType.KING
        }
    }

    fun addLastMove(move: Move) {
        moveHistory = move
    }

    fun getLastMove() = moveHistory

    fun getAll(color: Color) = board.flatten().filterNotNull().filter { e -> e.color == color }

    fun inBound(row: Int, column: Int): Boolean =
        row >= 0 && column >= 0 && row < fieldSize.size && column < fieldSize.size

    fun inBound(coordinate: Coordinate): Boolean =
        coordinate.y >= 0 && coordinate.y >= 0 && coordinate.x < fieldSize.size && coordinate.x < fieldSize.size

    fun isFree(row: Int, column: Int): Boolean = if (inBound(row, column)) board[row][column] == null else false
}