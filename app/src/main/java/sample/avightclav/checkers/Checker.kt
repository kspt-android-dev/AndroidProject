package sample.avightclav.checkers

import java.io.Serializable

enum class Color(val shift: Int) {
    BLACK(1),
    WHITE(-1);

    companion object {
        fun fromString(color: String): Color {
            return when (color) {
                "BLACK" -> BLACK
                "WHITE" -> WHITE
                else -> throw IllegalArgumentException()
            }
        }
    }

    fun opposite() = if (this == WHITE) BLACK else WHITE
}

enum class CheckerType {
    MAN,
    KING;

    companion object {
        fun fromString(type: String): CheckerType {
            return when (type) {
                "MAN" -> MAN
                "KING" -> KING
                else -> throw IllegalArgumentException()
            }
        }
    }
}

enum class CheckerState {
    ON_FIELD,
    STROKED;

    companion object {
        fun fromString(state: String): CheckerState {
            return when (state) {
                "ON_FIELD" -> ON_FIELD
                "STROKED" -> STROKED
                else -> throw IllegalArgumentException()
            }
        }
    }
}

data class Coordinate(val y: Int, val x: Int) : Serializable

data class Checker(val color: Color, var coordinate: Coordinate, var type: CheckerType, var state: CheckerState) :
    Serializable