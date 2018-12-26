package polytech.vladislava.sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuTest {

    private final int[][] solution = {{1,2,3,4,5,6,7,8,9}, {2,3,4,5,6,7,8,9,1}, {3,4,5,6,7,8,9,1,2},
            {4,5,6,7,8,9,1,2,3}, {5,6,7,8,9,1,2,3,4}, {6,7,8,9,1,2,3,4,5}, {7,8,9,1,23,4,5,6},
            {8,9,1,2,3,4,5,6,7}, {9,1,2,3,4,5,6,7,8}};
    private final int[][] game = {{1,2,3,0,0,6,7,8,9}, {0,3,4,5,6,7,8,9,1}, {3,0,5,6,7,8,0,1,2},
            {4,5,6,7,8,9,0,2,3}, {5,6,7,8,9,1,2,3,4}, {6,7,8,9,1,2,3,4,5}, {7,8,0,1,0,3,0,5,6},
            {8,9,1,0,3,4,5,6,7}, {9,0,2,3,4,5,6,7,0}};

    private final Sudoku sudoku = new Sudoku(solution, game);

    @Test
    public void isPossibleX() {
        assertFalse(sudoku.isPossibleX(sudoku.getGame(), 1, 3));
        assertTrue(sudoku.isPossibleX(sudoku.getGame(), 1, 2));
    }

    @Test
    public void isPossibleY() {
        assertTrue(sudoku.isPossibleY(sudoku.getGame(), 1, 4));
        assertFalse(sudoku.isPossibleY(sudoku.getGame(), 1, 3));
    }

    @Test
    public void isPossibleBlock() {
        assertTrue(sudoku.isPossibleBlock(sudoku.getGame(), 9, 9, 1));
        assertFalse(sudoku.isPossibleBlock(sudoku.getGame(), 9, 9, 7));
    }
}