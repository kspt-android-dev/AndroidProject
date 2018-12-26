package com.example.danila.minerandroid;

import java.io.Serializable;
import java.util.Random;


class Logic implements Serializable {

    private LogicCell[] logicCells;
    private LogicCell[] bombs;
    private int minesDigit;
    private int levelWidth;
    private int levelHight;
    private int findedBombs = 0;
    private int gameCondition;


    final static int GAME_WIN = 1;
    final static int GAME_CONTINUES = 0;
    final static int GAME_LOSSED = -1;


    Logic(int width, int hight, int[] numbersOfMines) {
        levelWidth = width;
        levelHight = hight;
        this.minesDigit = numbersOfMines.length;
        logicCells = new LogicCell[width * hight];
        bombs = new LogicCell[numbersOfMines.length];
        common(width, hight, numbersOfMines);
    }

    Logic(int width, int hight, int minesDigit) {
        levelWidth = width;
        this.minesDigit = minesDigit;
        levelHight = hight;
        logicCells = new LogicCell[hight * width];
        bombs = new LogicCell[minesDigit];
        int[] numbersOfMines = generateNumbersOfMines(minesDigit);//массив, который хранит номера мин
        common(width, hight, numbersOfMines);
    }

    //Общий метод использующийся в конструкторах
    private void common(int weight, int hight, int[] numbersOfMines) {

        for (int i = 0; i < logicCells.length; i++)
            logicCells[i] = new LogicCell(0, i);

        int bombIndex = 0;
        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    logicCells[i * weight + j].setConditon(9);
                    bombs[bombIndex] = logicCells[i * weight + j];//тут лучше бы переворачивать
                    bombIndex++;
                }
            }


        for (LogicCell logicCell : logicCells)
            logicCell.setNearlyCell(levelWidth, levelHight);


        addCondition();


    }


    //Установка состояния на все мины,вызывайтся на минах
    private void addCondition() {
        for (LogicCell bomb : bombs)
            for (int number : bomb.getNearlyCells())
                if (number != -10)
                    logicCells[number].addCondition();

    }


    private static boolean contains(int dig, int[] mass) {
        for (int number : mass)
            if (dig == number)
                return true;
        return false;

    }

    //Возвращает массив с номерами мин
    private int[] generateNumbersOfMines(int minesDigit) {
        int[] numbersOfMines = new int[minesDigit];
        int digit;//Промежуточная переменная для избежания повторения позиций мин
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(levelWidth * levelHight - 1);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(levelWidth * levelHight);

            numbersOfMines[i] = digit;
        }
        return numbersOfMines;
    }//Генерирование индексов мин без повторов


    /*Все что выше для конструктора
     *
     *
     *
     *
     *
     * Все что выше для конструктора*/


    //Общая часть reload-ов
    private void reloadCommon(int[] numbersOfMines) {
        for (int i = 0; i < bombs.length; i++)
            bombs[i] = null;


        int bombIndex = 0;
        for (int i = 0; i < levelHight; i++)
            for (int j = 0; j < levelWidth; j++) {
                if (contains(levelWidth * i + j, numbersOfMines)) {

                    logicCells[i * levelWidth + j].setConditon(9);
                    bombs[bombIndex] = logicCells[i * levelWidth + j];
                    bombIndex++;
                } else
                    logicCells[i * levelWidth + j].setConditon(0);

            }
        for (LogicCell logicCell : logicCells) {
            logicCell.setFlag(false);
            logicCell.setChecked(false);
        }
        findedBombs = 0;

        addCondition();

    }

    //Перезагрузка уровня
    void reload() {
        reloadCommon(generateNumbersOfMines(minesDigit));

    }

    //Перезагрузка уровня с заданными индексами мин
    void reload(int[] massMines) {
        reloadCommon(massMines);

    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        for (LogicCell logicCell : logicCells) {
            logicCell.setFlag(false);
            logicCell.setChecked(false);
        }
    }

    void checkCell(int numberOfCell) {
        if (!logicCells[numberOfCell].isFlag() && !logicCells[numberOfCell].isChecked()) {

            logicCells[numberOfCell].checkCell();

            if (logicCells[numberOfCell].getConditon() == 0)
                for (int index : logicCells[numberOfCell].getNearlyCells())
                    if (index >= 0 && !logicCells[index].isChecked())
                        checkCell(index);
        }


    }

    void changeFlag(int numberOfCell) {
        if (!logicCells[numberOfCell].isChecked()) {
            logicCells[numberOfCell].changeFlag();
            findedBombs = logicCells[numberOfCell].isFlag() ? findedBombs + 1 : findedBombs - 1;
        }

    }


    //Вывод условий в консоль
    void checkAll() {
        for (LogicCell logicCell : logicCells)
            logicCell.setChecked(true);
    }

    int checkGameCondition() {

        for (LogicCell bomb : bombs)
            if (bomb.isChecked()) {
                gameCondition = GAME_LOSSED;
                return gameCondition;
            }


        int checkedCell = 0;
        findedBombs = 0;

        for (LogicCell cell : logicCells) {
            if (cell.isFlag())
                findedBombs++;
            if (cell.isChecked())
                checkedCell++;
        }

        gameCondition = findedBombs + checkedCell == logicCells.length ? GAME_WIN : GAME_CONTINUES;

        return gameCondition;


    }

    //setter

    void setLogicCells(LogicCell[] logicCells) {
        this.logicCells = logicCells;
    }

    //getters-----------------------------
    int getMinesDigit() {
        return minesDigit;
    }

    int getLevelWidth() {
        return levelWidth;
    }

    int getLevelHight() {
        return levelHight;
    }

    LogicCell[] getLogicCells() {
        return logicCells;
    }

    LogicCell[] getBombs() {
        return bombs;
    }

    boolean isWin() {
        for (LogicCell bomb : bombs)
            if (bomb.isChecked())
                return false;


        int checkedCell = 0;
        int findedBombs = 0;
        for (LogicCell cell : logicCells) {
            if (cell.isFlag())
                findedBombs++;
            if (cell.isChecked())
                checkedCell++;
        }

        return findedBombs + checkedCell == logicCells.length;
    }

    int getFindedMinesDigit() {
        return findedBombs;
    }

}

