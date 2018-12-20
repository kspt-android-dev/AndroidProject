package com.example.danila.minerandroid;

class LogicCell {

    private int conditon;//Колличество мин вокруг клетки, 9 обозначается мина
    private boolean isChecked = false;//Проверена ли клетка
    final private int numberInArray;//Номер клетки в массиве cells
    private boolean flag = false;//true-есть флаг, false-нет флага
    private int[] nearlyCells;//Номера клеток, находящихся рядом
    double probabilities;//принимает разные значения в зависимости от isChecked
    //Если isChecked-то вероятность группы, если !isChecked-то вероятность рандома

    LogicCell(int conditon, int numberInArray) {
        this.numberInArray = numberInArray;
        this.conditon = conditon;

    }

    //Используется после того как задан весь массив cells, для определения массива номеров клеток, находящихся рядом
    void setNearlyCell(int levelWeight, int levelHight) {
        nearlyCells = new int[8];
        for (int i = 0; i < nearlyCells.length; i++)
            nearlyCells[i] = -10;


        if (numberInArray % levelWeight != 0)//Если не самая левая
            nearlyCells[0] = numberInArray - 1;


        if (numberInArray % levelWeight != levelWeight - 1) //Если не самая правая
            nearlyCells[4] = numberInArray + 1;


        if (numberInArray / levelWeight != 0) {//Если не в верхней строчке

            nearlyCells[2] = numberInArray - levelWeight;

            if (numberInArray % levelWeight != levelWeight - 1)
                nearlyCells[3] = numberInArray - levelWeight + 1;

            if (numberInArray % levelWeight != 0)
                nearlyCells[1] = numberInArray - levelWeight - 1;

        }
        if (numberInArray / levelWeight != levelHight - 1) {//Если не в нижней
            nearlyCells[6] = numberInArray + levelWeight;

            if (numberInArray % levelWeight != levelWeight - 1)
                nearlyCells[5] = numberInArray + levelWeight + 1;

            if (numberInArray % levelWeight != 0)
                nearlyCells[7] = numberInArray + levelWeight - 1;


        }


    }

    //Проверка клетки для бота, сделать для логической клетки
    LogicCell checkBot() {
        isChecked = true;
        return this;
    }

    //Установка флага(для бота)
    void changeFlag() {
        flag = !flag;


    }

    void setFlag(boolean flag) {
        this.flag = flag;
    }


    //setters
    void setConditon(int conditon) {
        this.conditon = conditon;
    }

    void setChecked(boolean checked) {
        isChecked = checked;
    }

    void addCondition() {
        if (conditon != 9)
            conditon++;
    }

    //getters
    int getConditon() {
        return conditon;
    }

    int[] getNearlyCells() {
        return nearlyCells;
    }

    int getNumberInArray() {
        return numberInArray;
    }

    boolean isFlag() {
        return flag;
    }

    double getProbabilities() {
        return probabilities;
    }

    //Открыли ли клетку(для автоматического открывания клеток вокруг нуля, боту не понадобится)
    boolean isChecked() {
        return isChecked;
    }

}
