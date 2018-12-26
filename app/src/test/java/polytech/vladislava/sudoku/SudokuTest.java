package polytech.vladislava.sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuTest {

    private int[][] solution = {{1,2,3,4,5,6,7,8,9}, {2,3,4,5,6,7,8,9,1}, {3,4,5,6,7,8,9,1,2},
            {4,5,6,7,8,9,1,2,3}, {5,6,7,8,9,1,2,3,4}, {6,7,8,9,1,2,3,4,5}, {7,8,9,1,23,4,5,6},
            {8,9,1,2,3,4,5,6,7}, {9,1,2,3,4,5,6,7,8}};
    private int[][] game = {{1,2,3,0,0,6,7,8,9}, {0,3,4,5,6,7,8,9,1}, {3,0,5,6,7,8,0,1,2},
            {4,5,6,7,8,9,0,2,3}, {5,6,7,8,9,1,2,3,4}, {6,7,8,9,1,2,3,4,5}, {7,8,0,1,0,3,0,5,6},
            {8,9,1,0,3,4,5,6,7}, {9,0,2,3,4,5,6,7,0}};

    Sudoku sudoku = new Sudoku(solution, game);

    @Test
    public void isPossibleX() {
        assertEquals(sudoku.isPossibleX(sudoku.getGame(), 1, 3), false);
        assertEquals(sudoku.isPossibleX(sudoku.getGame(), 1, 2), true);
    }

    @Test
    public void isPossibleY() {
        assertEquals(sudoku.isPossibleY(sudoku.getGame(), 1, 4), true);
        assertEquals(sudoku.isPossibleY(sudoku.getGame(), 1, 3), false);
    }

    @Test
    public void isPossibleBlock() {
        assertEquals(sudoku.isPossibleBlock(sudoku.getGame(), 9, 9, 1), true);
        assertEquals(sudoku.isPossibleBlock(sudoku.getGame(), 9, 9, 7), false);
    }
}