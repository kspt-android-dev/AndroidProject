package com.example.danila.minerandroid;

import java.util.Random;
//Периодически выставляется меньше мин чем надо


class Logic {

    private LogicCell[] logicCells;
    private LogicCell[] bombs;
    private int minesDigit;
    private int levelWidth;
    private int levelHight;
    private int findedBombs = 0;


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


    //Метод для обеспечения отсутсвия повторов номеров мин
    //Необходимо избавиться/переделать
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
            logicCell.probabilities = 1;
            logicCell.setFlag(false);
            findedBombs--;
            logicCell.setChecked(false);
        }

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


    //Вывод условий в консоль
    void checkAll() {
        for (LogicCell bomb : bombs)
            System.out.print("" + bomb.getNumberInArray() + ",");

        System.out.println("Колличество бомб " + bombs.length);
    }



    //Setters


    //Getters-----------------------------

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

    boolean isGameOver() {
        for (LogicCell bomb : bombs)
            if (bomb.isChecked())
                return true;


        int checkedCell = 0;
        int findedBombs = 0;
        for (LogicCell cell : logicCells) {
            if (cell.isFlag())
                findedBombs++;
            if (cell.isChecked())
                checkedCell++;
        }

        this.findedBombs = findedBombs;

        return findedBombs + checkedCell == logicCells.length;


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

    public int getFindedMinesDigit() {
        return findedBombs;
    }
}

