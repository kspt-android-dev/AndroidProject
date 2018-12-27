package ru.polytech.course.pashnik.lines;

import org.junit.Test;

import ru.polytech.course.pashnik.lines.Core.Board;
import ru.polytech.course.pashnik.lines.Core.Cell;
import ru.polytech.course.pashnik.lines.Core.ColorType;
import ru.polytech.course.pashnik.lines.Core.Intellect;

import static org.junit.Assert.*;

public class BusinessLogicTests {

    private static final int MIN_WIN_NUMBER = 5;

    @Test
    public void doArithmeticWithCells() {
        Cell start = new Cell(2, 1);
        final Cell[] direction = {
                new Cell(1, 0), // x-axis
                new Cell(0, 1), // y-axis
                new Cell(-1, 1), // main diagonal
                new Cell(1, 1)}; // secondary diagonal

        assertEquals(new Cell(3, 1), start.plus(direction[0]));
        assertEquals(new Cell(2, 0), start.minus(direction[1]));
        assertEquals(new Cell(1, 2), start.plus(direction[2]));
        assertEquals(new Cell(1, 0), start.minus(direction[3]));
    }

    @Test
    public void isWinnerCell() {
        final Board trueBoard = new Board();
        final Board falseBoard = new Board();
        final Board secondFalseBoard = new Board();
        final Board thirdFalseBoard = new Board();

        final Intellect intellect = new Intellect(secondFalseBoard);

        // testing one line
        for (int i = 0; i < MIN_WIN_NUMBER; i++) {
            trueBoard.addCell(new Cell(i, i), ColorType.GREEN);
        }
        assertTrue(trueBoard.isWin(new Cell(4, 4)));
        assertTrue(trueBoard.isWin(new Cell(5, 5)));

        // testing different colors
        for (int i = 0; i < MIN_WIN_NUMBER; i++) {
            falseBoard.addCell(new Cell(i, i), ColorType.getColorType(i));
        }
        assertFalse(falseBoard.isWin(new Cell(4, 4)));

        // testing five random cells
        for (int i = 0; i < MIN_WIN_NUMBER; i++) {
            secondFalseBoard.addCell(intellect.generateNextCell(),
                    intellect.generateNextColor());
        }
        assertFalse(secondFalseBoard.isWin(new Cell(4, 1)));

        // testing four this situation: x x x x _ x
        // where x - is a ball, _ - is a empty cell

        for (int i = 0; i < MIN_WIN_NUMBER - 1; i++) {
            thirdFalseBoard.addCell(new Cell(i, 1), ColorType.BLUE);
        }
        thirdFalseBoard.addCell(new Cell(5, 1), ColorType.BLUE);
        assertFalse(thirdFalseBoard.isWin(new Cell(5, 1)));

    }
}