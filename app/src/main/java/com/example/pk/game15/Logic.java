package com.example.pk.game15;

import java.util.Random;

public class Logic {
    private final int side;
    private final int numTiles;
    private Random rand;
    private int[] tiles;
    private int blankPos;
    private boolean gameOver;

    Logic (int side) {
        this.side = side;
        numTiles = side * side - 1;
        rand = new Random();
        tiles = new int[numTiles + 1];
        newGame();
    }

    private void newGame() {
        do {
            reset();
            shuffle();
        } while (!isSolvable());
        gameOver = false;
    }

    private void reset() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (i + 1) % tiles.length;
        }
        blankPos = tiles.length - 1;
    }

    private void shuffle() {
        int n = numTiles;
        while (n > 1) {
            int r = rand.nextInt(n--);
            int tmp = tiles[r];
            tiles[r] = tiles[n];
            tiles[n] = tmp;
        }
    }

    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i]) {
                    countInversions++;
                }
            }
        }
        return countInversions % 2 == 0;
    }

    private boolean isSolved() {
        if (tiles[tiles.length - 1] != 0) {
            return false;
        }
        for (int i = numTiles - 1; i >= 0; i--) {
            if (tiles[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    public void move(int position){
        int k;
        if (blankPos % 4 < 3 && position == blankPos + 1) {
            k = tiles[blankPos];
            tiles[blankPos] = tiles[position];
            tiles[position] = k;
            blankPos = position;
        }
        if (blankPos % 4 > 0 && position == blankPos - 1) {
            k = tiles[blankPos];
            tiles[blankPos] = tiles[position];
            tiles[position] = k;
            blankPos = position;
        }
        if (blankPos / 4 < 3 && position == blankPos + 4) {
            k = tiles[blankPos];
            tiles[blankPos] = tiles[position];
            tiles[position] = k;
            blankPos = position;
        }
        if (blankPos / 4 > 0 && position == blankPos - 4) {
            k = tiles[blankPos];
            tiles[blankPos] = tiles[position];
            tiles[position] = k;
            blankPos = position;
        }
        gameOver = isSolved();
    }

    public int[] getTiles() {
        return tiles;
    }

    public int getBlankPos() {
        return blankPos;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
