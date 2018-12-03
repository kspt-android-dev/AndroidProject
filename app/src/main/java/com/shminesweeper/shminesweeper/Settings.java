package com.shminesweeper.shminesweeper;

public class Settings {

    static int cellsInWidth = 10;
    static int cellsInHeight = 10;
    static int numberOfMines = 30;

    enum FieldMode {SQUARE, HEXAGONAL}
    static FieldMode fieldMode = FieldMode.HEXAGONAL;

    public Settings(){

    }

}
