package com.example.myapp;

import java.util.ArrayList;

class Logic {

    static final ArrayList<Cell> cells = new ArrayList<>();

    static int winX = 0;
    static int winO = 0;
    static int flagTeamWin = 0; // 1-x, 2-o
    static int countStep = 0;
    final int[] arrWin = new int[1];



    /*              documentation:

3x3
    1) 1строка ооо
    2) 2строка ооо
    3) 3строка ооо

    4) 1столбец  о
                 о
                 о

    5) 2столбец  о
                 о
                 о

    6) 3столбец  о
                 о
                 о

    7) крест \   о
    8) крест /   о
    ______________

    11) 1строка ооо
    12) 2строка ооо
    13) 3строка ооо

    14) 1столбец о
                 о
                 о

    15) 2столбец о
                 о
                 о

    16) 3столбец о
                 о
                 о

    17) крест \  о
    18) крест /  о
    */


    boolean checkWin3x3() {


        /* Проверка на победу ноликов, горизонтальный ряд*/
        if (checkInGrid(new Cell(163, 163, 1)) && checkInGrid(new Cell(490, 163, 1)) && checkInGrid(new Cell(817, 163, 1))) {
            arrWin[0] = 1;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(163, 523, 1)) && checkInGrid(new Cell(490, 523, 1)) && checkInGrid(new Cell(817, 523, 1))) {
            arrWin[0] = 2;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(163, 861, 1)) && checkInGrid(new Cell(490, 861, 1)) && checkInGrid(new Cell(817, 861, 1))) {
            arrWin[0] = 3;
            flagTeamWin = 2;
            return true;
        }


        /* Проверка на победу ноликов, вертикальный ряд*/
        if (checkInGrid(new Cell(163, 163, 1)) && checkInGrid(new Cell(163, 523, 1)) && checkInGrid(new Cell(163, 861, 1))) {
            arrWin[0] = 4;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(490, 163, 1)) && checkInGrid(new Cell(490, 523, 1)) && checkInGrid(new Cell(490, 861, 1))) {
            arrWin[0] = 5;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(817, 163, 1)) && checkInGrid(new Cell(817, 523, 1)) && checkInGrid(new Cell(817, 861, 1))) {
            arrWin[0] = 6;
            flagTeamWin = 2;
            return true;
        }


        /* Проверка на победу ноликов, диагональный \ ряд */
        if (checkInGrid(new Cell(163, 163, 1)) && checkInGrid(new Cell(490, 523, 1)) && checkInGrid(new Cell(817, 861, 1))) {
            arrWin[0] = 7;
            flagTeamWin = 2;
            return true;
        }


        /* Проверка на победу ноликов, диагональный / ряд */
        if (checkInGrid(new Cell(817, 163, 1)) && checkInGrid(new Cell(490, 523, 1)) && checkInGrid(new Cell(163, 861, 1))) {
            arrWin[0] = 8;
            flagTeamWin = 2;
            return true;
        }





        /* Проверка на победу КРЕСТИКОВ, горизонтальный ряд*/
        if (checkInGrid(new Cell(163, 163, 0)) && checkInGrid(new Cell(490, 163, 0)) && checkInGrid(new Cell(817, 163, 0))) {
            arrWin[0] = 11;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(163, 523, 0)) && checkInGrid(new Cell(490, 523, 0)) && checkInGrid(new Cell(817, 523, 0))) {
            arrWin[0] = 12;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(163, 861, 0)) && checkInGrid(new Cell(490, 861, 0)) && checkInGrid(new Cell(817, 861, 0))) {
            arrWin[0] = 13;
            flagTeamWin = 1;
            return true;
        }


        /* Проверка на победу крестиков, вертикальный ряд*/
        if (checkInGrid(new Cell(163, 163, 0)) && checkInGrid(new Cell(163, 523, 0)) && checkInGrid(new Cell(163, 861, 0))) {
            arrWin[0] = 14;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(490, 163, 0)) && checkInGrid(new Cell(490, 523, 0)) && checkInGrid(new Cell(490, 861, 0))) {
            arrWin[0] = 15;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(817, 163, 0)) && checkInGrid(new Cell(817, 523, 0)) && checkInGrid(new Cell(817, 861, 0))) {
            arrWin[0] = 16;
            flagTeamWin = 1;
            return true;
        }


        /* Проверка на победу крестиков, диагональный \ ряд */
        if (checkInGrid(new Cell(163, 163, 0)) && checkInGrid(new Cell(490, 523, 0)) && checkInGrid(new Cell(817, 861, 0))) {
            arrWin[0] = 17;
            flagTeamWin = 1;
            return true;
        }


        /* Проверка на победу крестиков, диагональный / ряд */
        if (checkInGrid(new Cell(817, 163, 0)) && checkInGrid(new Cell(490, 523, 0)) && checkInGrid(new Cell(163, 861, 0))) {
            arrWin[0] = 18;
            flagTeamWin = 1;
            return true;
        }
        arrWin[0] = 0;
        return false;
    }




     /*              documentation:

5x5
    1) 1строка оооoo
    2) 2строка оооoo
    3) 3строка оооoo

    4) 1столбец  о
                 о
                 o
                 o
                 о

    5) 2столбец  о
                 o
                 o
                 о
                 о

    6) 3столбец  о
                 o
                 o
                 о
                 о

    7) крест \   о
    8) крест /   о
    ______________

    11) 1строка xxxxx
    12) 2строка xxxxx
    13) 3строка xxxxx

    14) 1столбец x
                 x
                 x
                 x
                 x

    15) 2столбец x
                 x
                 x
                 x
                 x

    16) 3столбец x
                 x
                 x
                 x
                 x

    17) крест \  x
    18) крест /  x
    */


    boolean checkWin5x5() {

        /* Проверка на победу ноликов, горизонтальный ряд*/
        if (checkInGrid(new Cell(102, 104, 1)) && checkInGrid(new Cell(297, 104, 1)) && checkInGrid(new Cell(496, 104, 1)) && checkInGrid(new Cell(695, 104, 1)) && checkInGrid(new Cell(886, 104, 1))) {
            arrWin[0] = 1;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(102, 312, 1)) && checkInGrid(new Cell(297, 312, 1)) && checkInGrid(new Cell(496, 312, 1)) && checkInGrid(new Cell(695, 312, 1)) && checkInGrid(new Cell(886, 312, 1))) {
            arrWin[0] = 2;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(102, 525, 1)) && checkInGrid(new Cell(297, 525, 1)) && checkInGrid(new Cell(496, 525, 1)) && checkInGrid(new Cell(695, 525, 1)) && checkInGrid(new Cell(886, 525, 1))) {
            arrWin[0] = 3;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(102, 735, 1)) && checkInGrid(new Cell(297, 735, 1)) && checkInGrid(new Cell(496, 735, 1)) && checkInGrid(new Cell(695, 735, 1)) && checkInGrid(new Cell(886, 735, 1))) {
            arrWin[0] = 31;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(102, 946, 1)) && checkInGrid(new Cell(297, 946, 1)) && checkInGrid(new Cell(496, 946, 1)) && checkInGrid(new Cell(695, 946, 1)) && checkInGrid(new Cell(886, 946, 1))) {
            arrWin[0] = 32;
            flagTeamWin = 2;
            return true;
        }

        /* Проверка на победу ноликов, вертикальный ряд*/
        if (checkInGrid(new Cell(102, 104, 1)) && checkInGrid(new Cell(102, 312, 1)) && checkInGrid(new Cell(102, 525, 1)) && checkInGrid(new Cell(102, 735, 1)) && checkInGrid(new Cell(102, 946, 1))) {
            arrWin[0] = 4;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(297, 104, 1)) && checkInGrid(new Cell(297, 312, 1)) && checkInGrid(new Cell(297, 525, 1)) && checkInGrid(new Cell(297, 735, 1)) && checkInGrid(new Cell(297, 946, 1))) {
            arrWin[0] = 5;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(496, 104, 1)) && checkInGrid(new Cell(496, 312, 1)) && checkInGrid(new Cell(496, 525, 1)) && checkInGrid(new Cell(496, 735, 1)) && checkInGrid(new Cell(496, 946, 1))) {
            arrWin[0] = 6;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(695, 104, 1)) && checkInGrid(new Cell(695, 312, 1)) && checkInGrid(new Cell(695, 525, 1)) && checkInGrid(new Cell(695, 735, 1)) && checkInGrid(new Cell(695, 946, 1))) {
            arrWin[0] = 61;
            flagTeamWin = 2;
            return true;
        }

        if (checkInGrid(new Cell(886, 104, 1)) && checkInGrid(new Cell(886, 312, 1)) && checkInGrid(new Cell(886, 525, 1)) && checkInGrid(new Cell(886, 735, 1)) && checkInGrid(new Cell(886, 946, 1))) {
            arrWin[0] = 62;
            flagTeamWin = 2;
            return true;
        }

        /* Проверка на победу ноликов, диагональный \ ряд */
        if (checkInGrid(new Cell(102, 104, 1)) && checkInGrid(new Cell(297, 312, 1)) && checkInGrid(new Cell(496, 525, 1)) && checkInGrid(new Cell(695, 735, 1)) && checkInGrid(new Cell(886, 946, 1))) {
            arrWin[0] = 7;
            flagTeamWin = 2;
            return true;
        }


        /* Проверка на победу ноликов, диагональный / ряд */
        if (checkInGrid(new Cell(886, 104, 1)) && checkInGrid(new Cell(695, 312, 1)) && checkInGrid(new Cell(496, 525, 1)) && checkInGrid(new Cell(297, 735, 1)) && checkInGrid(new Cell(102, 946, 1))) {
            arrWin[0] = 8;
            flagTeamWin = 2;
            return true;
        }

        /* Проверка на победу КРЕСТИКОВ, горизонтальный ряд*/
        if (checkInGrid(new Cell(102, 104, 0)) && checkInGrid(new Cell(297, 104, 0)) && checkInGrid(new Cell(496, 104, 0)) && checkInGrid(new Cell(695, 104, 0)) && checkInGrid(new Cell(886, 104, 0))) {
            arrWin[0] = 11;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(102, 312, 0)) && checkInGrid(new Cell(297, 312, 0)) && checkInGrid(new Cell(496, 312, 0)) && checkInGrid(new Cell(695, 312, 0)) && checkInGrid(new Cell(886, 312, 0))) {
            arrWin[0] = 12;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(102, 525, 0)) && checkInGrid(new Cell(297, 525, 0)) && checkInGrid(new Cell(496, 525, 0)) && checkInGrid(new Cell(695, 525, 0)) && checkInGrid(new Cell(886, 525, 0))) {
            arrWin[0] = 13;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(102, 735, 0)) && checkInGrid(new Cell(297, 735, 0)) && checkInGrid(new Cell(496, 735, 0)) && checkInGrid(new Cell(695, 735, 0)) && checkInGrid(new Cell(886, 735, 0))) {
            arrWin[0] = 131;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(102, 946, 0)) && checkInGrid(new Cell(297, 946, 0)) && checkInGrid(new Cell(496, 946, 0)) && checkInGrid(new Cell(695, 946, 0)) && checkInGrid(new Cell(886, 946, 0))) {
            arrWin[0] = 132;
            flagTeamWin = 1;
            return true;
        }

        /* Проверка на победу крестиков, вертикальный ряд*/
        if (checkInGrid(new Cell(102, 104, 0)) && checkInGrid(new Cell(102, 312, 0)) && checkInGrid(new Cell(102, 525, 0)) && checkInGrid(new Cell(102, 735, 0)) && checkInGrid(new Cell(102, 946, 0))) {
            arrWin[0] = 14;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(297, 104, 0)) && checkInGrid(new Cell(297, 312, 0)) && checkInGrid(new Cell(297, 525, 0)) && checkInGrid(new Cell(297, 735, 0)) && checkInGrid(new Cell(297, 946, 0))) {
            arrWin[0] = 15;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(496, 104, 0)) && checkInGrid(new Cell(496, 312, 0)) && checkInGrid(new Cell(496, 525, 0)) && checkInGrid(new Cell(496, 735, 0)) && checkInGrid(new Cell(496, 946, 0))) {
            arrWin[0] = 16;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(695, 104, 0)) && checkInGrid(new Cell(695, 312, 0)) && checkInGrid(new Cell(695, 525, 0)) && checkInGrid(new Cell(695, 735, 0)) && checkInGrid(new Cell(695, 946, 0))) {
            arrWin[0] = 161;
            flagTeamWin = 1;
            return true;
        }

        if (checkInGrid(new Cell(886, 104, 0)) && checkInGrid(new Cell(886, 312, 0)) && checkInGrid(new Cell(886, 525, 0)) && checkInGrid(new Cell(886, 735, 0)) && checkInGrid(new Cell(886, 946, 0))) {
            arrWin[0] = 162;
            flagTeamWin = 1;
            return true;
        }

        /* Проверка на победу крестиков, диагональный \ ряд */
        if (checkInGrid(new Cell(102, 104, 0)) && checkInGrid(new Cell(297, 312, 0)) && checkInGrid(new Cell(496, 525, 0)) && checkInGrid(new Cell(695, 735, 0)) && checkInGrid(new Cell(886, 946, 0))) {
            arrWin[0] = 17;
            flagTeamWin = 1;
            return true;
        }


        /* Проверка на победу крестиков, диагональный / ряд */
        if (checkInGrid(new Cell(886, 104, 0)) && checkInGrid(new Cell(695, 312, 0)) && checkInGrid(new Cell(496, 525, 0)) && checkInGrid(new Cell(297, 735, 0)) && checkInGrid(new Cell(102, 946, 0))) {
            arrWin[0] = 18;
            flagTeamWin = 1;
            return true;
        }
        arrWin[0] = 0;
        return false;
    }

    boolean checkInGrid(Cell cell) {
        for (int i = 0; i != Logic.cells.size(); i++) {
            if (Logic.cells.get(i) != null)
                if (Logic.cells.get(i).equals(cell))
                    return true;
        }
        return false;
    }

    boolean isEmptyOnCell(Cell cell) {
        for (int i = 0; i != Logic.cells.size(); i++) {
            if (Logic.cells.get(i) != null)
                if (Logic.cells.get(i).getX() == cell.getX() && Logic.cells.get(i).getY() == cell.getY())
                    return false;
        }
        return true;
    }

    void setFieldCell(Cell point) {
        cells.add(point);
    }


}