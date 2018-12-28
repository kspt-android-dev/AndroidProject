package com.example.pk.game15;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Logic implements Parcelable {
    private final int side;
    private final int numTiles;
    private Random rand;
    private final int[] tiles;
    private int blankPos;
    private boolean gameOver;

    Logic (int side) {
        this.side = side;
        numTiles = side * side - 1;
        rand = new Random();
        tiles = new int[numTiles + 1];
        newGame();
    }

    private Logic(Parcel in) {
        side = in.readInt();
        numTiles = in.readInt();
        tiles = in.createIntArray();
        blankPos = in.readInt();
        gameOver = in.readByte() != 0;
    }
    //инициализация новой игры
    public void newGame() {
        do {
            reset();
            shuffle();
        } while (!isSolvable());
        gameOver = false;
    }
    //reset клеток
    private void reset() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (i + 1) % tiles.length;
        }
        blankPos = tiles.length - 1;
    }
    //замешивание клеток
    private void shuffle() {
        int n = numTiles;
        while (n > 1) {
            int r = rand.nextInt(n--);
            int tmp = tiles[r];
            tiles[r] = tiles[n];
            tiles[n] = tmp;
        }
    }
    //проверка решается ли данная пятнашка :)
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
    //проверка решена ли игра
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
    //реализация хода
    public void move(int position){
        int k;
        if(position != blankPos) {
            if (blankPos % side < 3 && position == blankPos + 1) {
                k = tiles[blankPos];
                tiles[blankPos] = tiles[position];
                tiles[position] = k;
                blankPos = position;
            }
            if (blankPos % side > 0 && position == blankPos - 1) {
                k = tiles[blankPos];
                tiles[blankPos] = tiles[position];
                tiles[position] = k;
                blankPos = position;
            }
            if (blankPos / side < 3 && position == blankPos + 4) {
                k = tiles[blankPos];
                tiles[blankPos] = tiles[position];
                tiles[position] = k;
                blankPos = position;
            }
            if (blankPos / side > 0 && position == blankPos - 4) {
                k = tiles[blankPos];
                tiles[blankPos] = tiles[position];
                tiles[position] = k;
                blankPos = position;
            }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(side);
        dest.writeInt(numTiles);
        dest.writeIntArray(tiles);
        dest.writeInt(blankPos);
        dest.writeByte((byte) (gameOver ? 1 : 0));
    }

    public static final Creator<Logic> CREATOR = new Creator<Logic>() {
        @Override
        public Logic createFromParcel(Parcel in) {
            return new Logic(in);
        }

        @Override
        public Logic[] newArray(int size) {
            return new Logic[size];
        }
    };
}
