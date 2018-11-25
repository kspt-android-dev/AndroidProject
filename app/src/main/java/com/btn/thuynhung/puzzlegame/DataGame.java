package com.btn.thuynhung.puzzlegame;

import java.util.ArrayList;
import java.util.Random;

public class DataGame {
    private final int lengthGrid = 4, heightGrid = 4;
    private final int sizeGrid = lengthGrid * heightGrid;
    private static DataGame datagame;
    private ArrayList<Integer> arrNumbs = new ArrayList<>();
    private int[][] field = new int[heightGrid][lengthGrid];
    private int moveNumb = 0;

    static {
        datagame = new DataGame();
    }

    public static DataGame getDatagame() {
        return datagame;
    }

    public ArrayList<Integer> getArrNumbs() {
        return arrNumbs;
    }

    public void clearArrNumbs() {
        arrNumbs.clear();
    }

    public void resetMove() {
        moveNumb = 0;
    }

    public int getMove () {
        return moveNumb;
    }

    public void setNumbs() {
        for (int i = 0; i < heightGrid; i++) {
            for (int j = 0; j < lengthGrid; j++) {
                field[i][j] = 0;
                arrNumbs.add(0);
            }
        }
        setContent();
        changeArrNumbs();
    }

    private void setContent() {
        int numb = 1;
        Random random = new Random();
        int blankItemsNumbs = 0;

        for (int i = 0; i < 16; i++) {
            if (arrNumbs.get(i) == 0) blankItemsNumbs++;
        }

        if (blankItemsNumbs == 16) {
            while (blankItemsNumbs != 1) {
                int i = random.nextInt(4), j = random.nextInt(4);
                if (field[i][j] == 0) {
                    field[i][j] = numb;
                    numb++;
                    blankItemsNumbs--;
                }
            }
        }
    }

    private void setContentForCheck() {
        int numb = 0;
        int blankItemsNumbs = 0;

        for (int i = 0; i < 16; i++) {
            if (arrNumbs.get(i) == 0) blankItemsNumbs++;
        }

        if (blankItemsNumbs == 16) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    numb++;
                    field[i][j] = numb;
                }
            }
            field[3][0] = 13;
            field[3][1] = 14;
            field[3][2] = 0;
            field[3][3] = 15;

        }
    }

    private void changeArrNumbs() {
        clearArrNumbs();
        for (int i = 0; i < heightGrid; i++) {
            for (int j = 0; j < lengthGrid; j++) {
                arrNumbs.add(field[i][j]);
            }
        }
    }

    public void moveItems(int clickedItemPosition) {
        boolean flag = false;
        int times = 0;
        for (int i = 0; i < heightGrid; i++) {
            for (int j = 0; j < lengthGrid; j++) {
                times++;
                if (times == clickedItemPosition + 1) {
                    if (j > 0) {
                        if (field[i][j - 1] == 0) {
                            field[i][j - 1] = field[i][j];
                            field[i][j] = 0;
                            moveNumb++;
                        }
                    }
                    if (j < lengthGrid - 1) {
                        if (field[i][j + 1] == 0) {
                            field[i][j + 1] = field[i][j];
                            field[i][j] = 0;
                            moveNumb++;
                        }
                    }
                    if (i > 0) {
                        if (field[i - 1][j] == 0) {
                            field[i - 1][j] = field[i][j];
                            field[i][j] = 0;
                            moveNumb++;
                        }
                    }
                    if (i < heightGrid - 1) {
                        if (field[i + 1][j] == 0) {
                            field[i + 1][j] = field[i][j];
                            field[i][j] = 0;
                            moveNumb++;
                        }
                    }
                    flag = true;
                    break;
                }
            }
            if (flag) break;
        }

        changeArrNumbs();
    }

    public boolean checkForWin() {
        for (int i = 0; i < sizeGrid - 1; i++) {
            if (arrNumbs.get(i) != i + 1) return false;
        }
        return true;
    }

}
