package polytech.vladislava.sudoku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sudoku implements Serializable {
    private int[][] solution;       // Generated solution.
    private int[][] game;           // Generated game with user input.
    private boolean[][] helped;     // If cell is true, this one was a hint
    private boolean[][] initial;    // If true, the cell was opened at begining
    private boolean[][] check;      // Holder for checking validity of game.
    private boolean help;           // Help turned on or off.


    public Sudoku() {
        check = new boolean[9][9];
        helped = new boolean[9][9];
        initial = new boolean[9][9];
        newGame();
        helped[3][3] = false;
        help = true;
    }

    public Sudoku(int[][] solution, int[][]game) {
        this.solution = solution;
        this.game = game;
        helped = new boolean[9][9];
        initial = new boolean[9][9];
        helped[3][3] = false;
        help = true;
    }

    public void newGame() {
        solution = generateSolution(new int[9][9], 0);
        game = generateGame(copy(solution));
        for (int i = 0; i < 81; i++){
            if (game[i % 9][i / 9] != 0)
                initial[i % 9][i / 9] = true;
        }
    }


    public void checkGame() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
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
        for (int x = 0; x < 9; x++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }


    boolean isPossibleY(int[][] game, int x, int number) {
        for (int y = 0; y < 9; y++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }


    boolean isPossibleBlock(int[][] game, int x, int y, int number) {
        int x1 = x < 3 ? 0 : x < 6 ? 3 : 6;
        int y1 = y < 3 ? 0 : y < 6 ? 3 : 6;
        for (int yy = y1; yy < y1 + 3; yy++) {
            for (int xx = x1; xx < x1 + 3; xx++) {
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
        if (index > 80)
            return game;

        int x = index % 9;
        int y = index / 9;

        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) numbers.add(i);
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
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++)
            positions.add(i);
        Collections.shuffle(positions);
        return generateGame(game, positions);
    }


    private int[][] generateGame(int[][] game, List<Integer> positions) {
        while (positions.size() > 0) {
            int position = positions.remove(0);
            int x = position % 9;
            int y = position / 9;
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
        if (index > 80)
            return ++numberOfSolutions[0] == 1;

        int x = index % 9;
        int y = index / 9;

        if (game[y][x] == 0) {
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 1; i <= 9; i++)
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
        } else if (!isValid(game, index + 1, numberOfSolutions))
            return false;

        return true;
    }


    private int[][] copy(int[][] game) {
        int[][] copy = new int[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                copy[y][x] = game[y][x];
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
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
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