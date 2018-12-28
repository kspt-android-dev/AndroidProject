package polytech.vladislava.sudoku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sudoku implements Serializable {
    private int[][] solution;       // Generated solution.
    private int[][] game;           // Generated game with user input.
    private final boolean[][] helped;     // If cell is true, this one was a hint
    private final boolean[][] initial;    // If true, the cell was opened at begining
    private boolean[][] check;      // Holder for checking validity of game.
    private final boolean help;           // Help turned on or off.
    private final static int FIELD_SIZE = 9;
    private final static int HELP_SIZE = 3;
    private final static int BLOCK_SIZE1 = 3;
    private final static int BLOCK_SIZE2 = 6;

    public Sudoku() {
        check = new boolean[FIELD_SIZE][FIELD_SIZE];
        helped = new boolean[FIELD_SIZE][FIELD_SIZE];
        initial = new boolean[FIELD_SIZE][FIELD_SIZE];
        newGame();
        helped[HELP_SIZE][HELP_SIZE] = false;
        help = true;
    }

    public Sudoku(int[][] solution, int[][]game) {
        this.solution = solution;
        this.game = game;
        helped = new boolean[FIELD_SIZE][FIELD_SIZE];
        initial = new boolean[FIELD_SIZE][FIELD_SIZE];
        helped[HELP_SIZE][HELP_SIZE] = false;
        help = true;
    }

    private void newGame() {
        solution = generateSolution(new int[FIELD_SIZE][FIELD_SIZE], 0);
        game = generateGame(copy(solution));
        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE; i++){
            if (game[i % FIELD_SIZE][i / FIELD_SIZE] != 0)
                initial[i % FIELD_SIZE][i / FIELD_SIZE] = true;
        }
    }


    private void checkGame() {
        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 0; x < FIELD_SIZE; x++)
                check[y][x] = game[y][x] == solution[y][x];
        }
    }

    public void setNumber(int x, int y, int number) {
        game[y][x] = number;
        checkGame();
    }

    public int[][] getGame(){
        return game;
    }

    public int[][] getSolution(){
        return solution;
    }


    public int getNumber(int x, int y) {
        return game[y][x];
    }


    public boolean isCheckValid(int x, int y) {
        return check[y][x];
    }


    boolean isPossibleX(int[][] game, int y, int number) {
        for (int x = 0; x < FIELD_SIZE; x++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }


    boolean isPossibleY(int[][] game, int x, int number) {
        for (int y = 0; y < FIELD_SIZE; y++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }


    boolean isPossibleBlock(int[][] game, int x, int y, int number) {
        int x1 = x < BLOCK_SIZE1 ? 0 : x < BLOCK_SIZE2 ? BLOCK_SIZE1 : BLOCK_SIZE2;
        int y1 = y < BLOCK_SIZE1 ? 0 : y < BLOCK_SIZE2 ? BLOCK_SIZE1 : BLOCK_SIZE2;
        for (int yy = y1; yy < y1 + BLOCK_SIZE1; yy++) {
            for (int xx = x1; xx < x1 + BLOCK_SIZE1; xx++) {
                if (game[yy][xx] == number)
                    return false;
            }
        }
        return true;
    }


    private int getNextPossibleNumber(int[][] game, int x, int y, List<Integer> numbers) {
        while (numbers.size() > 0) {
            int number = numbers.remove(0);
            if (isPossibleX(game, y, number) && isPossibleY(game, x, number) && isPossibleBlock(game, x, y, number))
                return number;
        }
        return -1;
    }


    private int[][] generateSolution(int[][] game, int index) {
        if (index > FIELD_SIZE * FIELD_SIZE - 1)
            return game;

        int x = index % FIELD_SIZE;
        int y = index / FIELD_SIZE;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= FIELD_SIZE; i++) numbers.add(i);
        Collections.shuffle(numbers);

        while (numbers.size() > 0) {
            int number = getNextPossibleNumber(game, x, y, numbers);
            if (number == -1)
                return null;

            game[y][x] = number;
            int[][] tmpGame = generateSolution(game, index + 1);
            if (tmpGame != null)
                return tmpGame;
            game[y][x] = 0;
        }

        return null;
    }


    private int[][] generateGame(int[][] game) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE; i++)
            positions.add(i);
        Collections.shuffle(positions);
        return generateGame(game, positions);
    }


    private int[][] generateGame(int[][] game, List<Integer> positions) {
        while (positions.size() > 0) {
            int position = positions.remove(0);
            int x = position % FIELD_SIZE;
            int y = position / FIELD_SIZE;
            int temp = game[y][x];
            game[y][x] = 0;

            if (!isValid(game))
                game[y][x] = temp;
        }

        return game;
    }


    private boolean isValid(int[][] game) {
        return isValid(game, 0, new int[] { 0 });
    }


    private boolean isValid(int[][] game, int index, int[] numberOfSolutions) {
        if (index > FIELD_SIZE * FIELD_SIZE - 1)
            return ++numberOfSolutions[0] == 1;

        int x = index % FIELD_SIZE;
        int y = index / FIELD_SIZE;

        if (game[y][x] == 0) {
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= FIELD_SIZE; i++)
                numbers.add(i);

            while (numbers.size() > 0) {
                int number = getNextPossibleNumber(game, x, y, numbers);
                if (number == -1)
                    break;
                game[y][x] = number;

                if (!isValid(game, index + 1, numberOfSolutions)) {
                    game[y][x] = 0;
                    return false;
                }
                game[y][x] = 0;
            }
        } else return isValid(game, index + 1, numberOfSolutions);

        return true;
    }


    private int[][] copy(int[][] game) {
        int[][] copy = new int[FIELD_SIZE][FIELD_SIZE];
        for (int y = 0; y < 9; y++) {
            System.arraycopy(game[y], 0, copy[y], 0, FIELD_SIZE);
        }
        return copy;
    }

    public int getSolutionForHelp(int x, int y) {
        setNumber(x, y, solution[y][x]);
        helped[y][x] = true;
        return solution[y][x];
    }


    private void print(int[][] game) {
        System.out.println();
        for (int y = 0; y < FIELD_SIZE; y++) {
            for (int x = 0; x < FIELD_SIZE; x++)
                System.out.print(" " + game[y][x]);
            System.out.println();
        }
    }

    public boolean isHelped(int x, int y) {
        return helped[y][x];
    }

    public boolean isInitial(int x, int y) {
        return initial[y][x];
    }
}